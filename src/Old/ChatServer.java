package Old;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

//Created by DaMasterHam on 20-02-2017.
//
public class ChatServer
{

    private List<ClientThread> users;
    private ServerSocket ss;

    public ChatServer()
    {
        try
        {
            ss = new ServerSocket(4200);
            users = new ArrayList<>();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }    }

    public static void main(String[] args)
    {

        try
        {
            ChatServer cs = new ChatServer();

            while (true)
            {
                ClientThread ct = new ClientThread(cs.ss.accept(), cs);
                ct.start();
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }


    }

    public List<ClientThread> getUsers()
    {
        return users;
    }
}
