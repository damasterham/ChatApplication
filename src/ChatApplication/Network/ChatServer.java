package ChatApplication.Network;//

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

//Created by DaMasterHam on 21-02-2017.
//
public class ChatServer
{
    private int port;
    private ServerSocket ss;

    private boolean receiveAlive;
    private Thread receiverThread;

    private List<ChatServerClient> serverClientThreads;


    public ChatServer(int port)
    {
        serverClientThreads = new ArrayList<>();
        this.port = port;
    }

    private boolean isNameTaken(String name)
    {
        for (ChatServerClient client : serverClientThreads)
        {
            if (client.getName().toLowerCase().equals(name))
                return true;
        }
        return false;
    }

    private void sendMessageToAll(String message) throws IOException
    {
        for (ChatServerClient client : serverClientThreads)
        {
            client.sendMessage(message);
        }
    }

    public void startServer() throws IOException
    {
        ss = new ServerSocket(port);
    }

    public void stopServer() throws IOException
    {
        receiveAlive = false;
        ss.close();
    }

    public void startReceivingAsync() throws IOException
    {
        receiveAlive = true;
        receiverThread = new ReceiverThread();
        receiverThread.start();
    }




    private class ReceiverThread extends Thread
    {

        @Override
        public void run()
        {
            System.out.println("Receiving");
            try
            {
                // Open to receive
                while (receiveAlive)
                {
                    System.out.println("Alive");
                    // Listens to clients
                    ChatServerClient client = new ChatServerClient(ss.accept());
                    System.out.println("Client received");
                    // Starts a new thread for intial connection
                    new Thread(() ->
                    {
                        try
                        {
                            String name = client.receiveMessage();
                            System.out.println("Client trying \""+name+"\"");
                            // Check is client with username already exists
                            if (isNameTaken(name))
                            {
                                System.out.println("Name is taken");
                                client.sendMessage("Name already taken");
                            }
                            else
                            {
                                System.out.println("Name is NOT taken");
                                client.setName(name);
                                serverClientThreads.add(client);
                                sendMessageToAll(name + " joined server");

                                // Client is good and new thread is started for them to write and receive messages
                                new Thread(() ->
                                {
                                    try
                                    {
                                        System.out.println(client.getName() + " is listening");
                                        while (client.isConnected())
                                        {
                                            String msg = client.receiveMessage();
                                            sendMessageToAll(msg);
                                        }
                                    }
                                    catch (IOException ex)
                                    {

                                    }
                                }).start();
                            }
                        }
                        catch (IOException ex)
                        {

                        }
                    }).start();
                }
            }
            catch (IOException ex)
            {

            }

        }
    }
}
