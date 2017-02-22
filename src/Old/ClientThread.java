package Old;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//Created by DaMasterHam on 20-02-2017.
//
public class ClientThread extends Thread
{
    private ChatServer cs;

    private Socket socket;
    private String username;

    private DataInputStream input;
    private DataOutputStream output;

    public ClientThread(Socket socket, ChatServer cs)
    {
        try
        {
            this.socket = socket;
            this.cs = cs;
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void run()
    {
        getUser();
    }

    private void getUser()
    {
        String user;

        try
        {
            user = input.readUTF();
            if (cs.getUsers().size() > 0)
            {
                for (ClientThread ct : cs.getUsers())
                {
                    if (ct.username.equals(user));
                        output.writeBytes("J_ERR");
                    return;
                }
            }
            else
            {
                username = user;
                cs.getUsers().add(this);
                output.writeBytes("J_OK");
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
