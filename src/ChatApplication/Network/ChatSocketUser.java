package ChatApplication.Network;//

import java.io.IOException;
import java.net.Socket;

//Created by DaMasterHam on 21-02-2017.
//
public class ChatSocketUser extends ChatSocket
{
    private String hostUrl;
    private int port;
    private String name;

    public ChatSocketUser(String host, int port)
    {
        this.hostUrl = host;
        this.port = port;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void connect() throws IOException
    {
        connect(hostUrl, port);
    }

    public void connect(String hostUrl, int port) throws IOException
    {
        host = new Socket(hostUrl, port);
        initializeStreams();
    }



//    public void sendMessage(String message)throws IOException
//    {
//        output.writeUTF(message);
//        output.flush();
//    }
//    public String receiveMessage() throws IOException
//    {
//        return input.readUTF();
//    }
}
