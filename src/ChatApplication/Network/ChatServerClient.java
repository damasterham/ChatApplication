package ChatApplication.Network;

import Old.ClientThread;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by DaMasterHam on 22-02-2017.
 */
public class ChatServerClient extends ChatClient
{
    private String name;

    public ChatServerClient(Socket host) throws IOException
    {
        super(host);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
