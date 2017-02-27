package ChatApplication.Network.Abstract;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by DaMasterHam on 22-02-2017.
 */
public abstract class ChatSocket
{
    protected Socket host;
    protected DataInputStream in;
    protected DataOutputStream out;

    public ChatSocket()
    {
    }

    public ChatSocket(Socket host) throws IOException
    {
        // Creates a new ProtocolHandler with implemented ProtocolActions
        this.host = host;
        initializeStreams();
    }

    protected void initializeStreams() throws IOException
    {
        if (host != null)
        {
            in = new DataInputStream(this.host.getInputStream());
            out = new DataOutputStream(this.host.getOutputStream());
        }
    }

    public boolean isConnected()
    {
        return host.isConnected();
    }


    protected void sendString(String message) throws IOException
    {
        out.writeUTF(message);
        out.flush();
    }

    protected String receiveString() throws IOException
    {
        return in.readUTF();
    }

}
