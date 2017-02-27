package ChatApplication.Network;

import ChatApplication.Network.Abstract.ChatSocket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by DaMasterHam on 22-02-2017.
 */
public class ChatSocketServer extends ChatSocket
{
    private String name;

    public ChatSocketServer(Socket host) throws IOException
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
