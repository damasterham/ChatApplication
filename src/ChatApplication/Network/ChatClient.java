package ChatApplication.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by DaMasterHam on 22-02-2017.
 */
public abstract class ChatClient
{
    protected Socket host;
    protected DataInputStream in;
    protected DataOutputStream out;

    public ChatClient()
    {

    }

    public ChatClient(Socket host) throws IOException
    {
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

    public void sendMessage(String message) throws IOException
    {
        out.writeUTF(message);
        out.flush();
    }

    public String receiveMessage() throws IOException
    {
        return in.readUTF();
    }

}
