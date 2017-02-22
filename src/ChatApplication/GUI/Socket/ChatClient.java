package ChatApplication.GUI.Socket;//

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//Created by DaMasterHam on 21-02-2017.
//
public class ChatClient
{
    private String host;
    private int port;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public ChatClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException
    {
        connect(host, port);
    }

    public void connect(String host, int port) throws IOException
    {
        socket = new Socket(host, port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
    }
}
