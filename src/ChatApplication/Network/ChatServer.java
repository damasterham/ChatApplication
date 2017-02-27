package ChatApplication.Network;//

import ChatApplication.Application.ProtocolResponse.ServerProtocolResponse;
import ChatApplication.Protocol.ChatProtocolResponseHandler;

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

    private List<ChatSocketServer> serverClientThreads;
    private ChatProtocolResponseHandler responseHandler;


    public ChatServer(int port)
    {
        serverClientThreads = new ArrayList<>();
        this.port = port;
        // Creates a ResponseHandler with contextual ServerResponse
        responseHandler = new ChatProtocolResponseHandler(new ServerProtocolResponse(this));
    }

    private boolean isNameTaken(String name)
    {
        for (ChatSocketServer client : serverClientThreads)
        {
            if (client.getName().toLowerCase().equals(name))
                return true;
        }
        return false;
    }

    public void sendMessageToAll(String sender, String message)
    {
        for (ChatSocketServer client : serverClientThreads)
        {
            //client.(message);
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
                    ChatSocketServer client = new ChatSocketServer(ss.accept());
                    System.out.println("Client received");
                    // Listens for data from client
                    new Thread(() ->
                    {
                        client.receiveString()
                    })


                }
            }
            catch (IOException ex)
            {

            }

        }
    }
}
