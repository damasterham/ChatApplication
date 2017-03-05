package ChatApplication.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CommunicationEndpoint
{
    private Socket host;
    private DataInputStream in;
    private DataOutputStream out;

    public CommunicationEndpoint(String hostname, int port) throws IOException
    {
        this(new Socket(hostname, port));
    }

    public CommunicationEndpoint(Socket host) throws IOException
    {
        // Creates a new ProtocolHandler with implemented ProtocolActions
        this.host = host;
        initializeStreams();
    }

    // Properties
    public String getLocalIp()
    {
        return host.getLocalAddress().getHostAddress();
    }

    public int getLocalPort()
    {
        return host.getLocalPort();
    }

    // Checks whether Socket is connected and whether the Socket is not shutdown
    public boolean isConnected()
    {
        return host.isConnected() && !host.isInputShutdown();
    }

    // Creates new Input and Output Streams from this CommunicationEndpoint / Socket
    private void initializeStreams() throws IOException
    {
        if (host != null)
        {
            in = new DataInputStream(this.host.getInputStream());
            out = new DataOutputStream(this.host.getOutputStream());
        }
    }

    // Send the messages from Client to the Server and flushes the OutputStream
    public void sendData(String message) throws IOException
    {
        out.writeUTF(message);
        out.flush();
    }

    // Reads messages from the input stream
    public String receiveData() throws IOException
    {
        return in.readUTF();
    }

    public void disconnect() throws IOException
    {
        if (!host.isClosed())
        {
            host.shutdownInput();
            host.close();
        }
    }

}
