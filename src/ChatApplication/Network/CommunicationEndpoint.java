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

    public boolean isConnected()
    {
        return host.isConnected();
    }



    private void initializeStreams() throws IOException
    {
        if (host != null)
        {
            in = new DataInputStream(this.host.getInputStream());
            out = new DataOutputStream(this.host.getOutputStream());
        }
    }



    public void sendData(String message) throws IOException
    {
        out.writeUTF(message);
        out.flush();
    }

    public String receiveData() throws IOException
    {
        return in.readUTF();
    }

    public void disconnect() throws IOException
    {
        host.close();
    }

}
