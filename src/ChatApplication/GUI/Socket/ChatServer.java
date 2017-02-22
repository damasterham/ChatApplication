package ChatApplication.GUI.Socket;//

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//Created by DaMasterHam on 21-02-2017.
//
public class ChatServer
{
    private int port;
    private ServerSocket ss;
    private DataInputStream input;
    private DataOutputStream output;

    private Socket client;

    public ChatServer(int port)
    {
        this.port = port;
    }

    public Socket getClient()
    {
        return client;
    }

    public void startServer() throws IOException
    {
        ss = new ServerSocket(port);
    }

    public void stopServer() throws IOException
    {
        ss.close();
    }

    public void receiveClient() throws IOException
    {
        client = ss.accept();
    }
}
