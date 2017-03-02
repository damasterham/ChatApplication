package ChatApplication.Network;//

import ChatApplication.Application.ServerApplication;
import ChatApplication.Protocol.ProtocolHandler;
import javafx.application.Platform;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

//Created by DaMasterHam on 21-02-2017.
//
public class Server
{
    public static final int PORT = 42000;

    private int maxNameLength;

    private int port;
    private ServerSocket ss;

    private boolean receiveAlive;

    private List<ServerEndpoint> clients;
    private ServerApplication serverApp;


    public Server(int port, ServerApplication serverApp)
    {
        maxNameLength = ProtocolHandler.MAX_NAME_LENGTH;
        clients = new ArrayList<>();

        this.port = port;
        this.serverApp = serverApp;
    }



    // Name check
    private boolean isNameToLong(String name)
    {
        return name.length() > maxNameLength;
    }

    private boolean isNameTaken(String name)
    {
        for (ServerEndpoint client : clients)
        {
            if (client.getName().toLowerCase().equals(name.toLowerCase()))
                return true;
        }
        return false;
    }



    // Sending data
    private void sendDataToAll(String data) throws IOException
    {
        for (ServerEndpoint client : clients)
        {
            client.sendData(data);
        }
    }

    private void sendListToAll() throws IOException
    {
        String userNames = "";

        for (ServerEndpoint client : clients)
        {
            userNames += " " + client.getName();
        }

        String protocol = ProtocolHandler.packList(userNames);

        sendDataToAll(protocol);
    }

    private void sendMessageToAll(String sender, String message) throws IOException
    {
        String protocol = ProtocolHandler.packMessage(sender, message);

        sendDataToAll(protocol);
    }



    // Handling methods
    private void disconnectClient(ServerEndpoint client) throws IOException
    {
        // Removes client from list.
        // So now he cannot receive messages from other clients
        clients.remove(client);

        try
        {
            // Disconnects client, by closing socket connection
            // Should throw IOException on user side.
            client.disconnect();
        }
        catch (IOException ex)
        {
            serverApp.appendToLog("EXCEPTION: " + client.getName() + " already disconnected from client side");
        }

        // Sends an updated list to all clients
        sendListToAll();
        serverApp.appendToLog(client.getName() + " disconnected");
    }

    private void handleJoin(String[] protocol, ServerEndpoint client) throws IOException
    {
        String serverMsg;

        if (protocol.length == 4)
        {
            serverApp.appendToLog("Client trying to join server");

            String name = protocol[1];
            String ip = protocol[2];
            String port = protocol[3];

            if (!isNameToLong(name))
            {
                if (!isNameTaken(name))
                {
                    client.setName(name);
                    client.setIp(ip);
                    client.setPort(port);

                    // Sends a J_OK protocol
                    client.sendData(ProtocolHandler.packJoinOk());
                    // Adds client to list
                    clients.add(client);
                    // Sends an updated list to all clients
                    sendListToAll();
                    serverMsg = "Client " + name + " connected with valid name, and is added to client list";
                }
                else
                {
                    client.sendData(ProtocolHandler.packJoinErrorTaken());
                    serverMsg = "Client tried to connect with already existing name: " + name;
                }
            }
            else
            {
                client.sendData(ProtocolHandler.packJoinErrorLength());
                serverMsg = "Client tried to connect with to long of a name: " + name;
            }

            serverApp.appendToLog(serverMsg);
        }
    }

    private void handleProtocol(String data, ServerEndpoint client) throws IOException
    {
        // Parse the data protocol received from client
        String[] protocol = ProtocolHandler.unPack(data);

        // Handle protocol
        switch (protocol[0])
        {
            case ProtocolHandler.JOIN :
                handleJoin(protocol, client);
                break;
            case ProtocolHandler.MSG : if (protocol.length == 3)
                sendMessageToAll(client.getName(), protocol[2]);
                // You could technically also just send the original protocol forward
                break;
            case ProtocolHandler.QUIT :
                disconnectClient(client);
                break;
            default:
                    Platform.runLater(()->
                            {
                                serverApp.appendToLog("Server received message with incorrect protocol syntax:");
                                serverApp.appendToLog(data) ;
                            }
                    );
                break;
        }
    }

    // Starts a new thread that listens to a client and handles received protocols
    private void listenToClientAsync(ServerEndpoint client)
    {
        // Starts a new thread
        new Thread(() ->
        {
            try
            {
                // While a client is connected
                while (client.isConnected())
                {
                    // Listen for data from a client
                    String data = client.receiveData();

                    handleProtocol(data, client);
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            } // Does the thread close if it reaches the end?
        }).start();
    }

    // Start a new thread that listens for connections from clients
    private void receiveClientsAsync()
    {
        new Thread(() ->
        {
            try
            {
                // Open to receive
                while (receiveAlive)
                {
                    serverApp.appendToLog("Listening for client");
                    // Waits to receive a new client
                    ServerEndpoint client = new ServerEndpoint(ss.accept());
                    serverApp.appendToLog("Client received");

                    listenToClientAsync(client);
                }
            }
            catch (IOException ex)
            {

            }
        }).start();

    }



    // Server control
    public void startReceiving() throws IOException
    {
        receiveAlive = true;
        receiveClientsAsync();
        serverApp.appendToLog("Server started listening for connections from clients");
    }

    public void stopServer() throws IOException
    {
        receiveAlive = false;
        ss.close();
    }

    public void startServer() throws IOException
    {
        ss = new ServerSocket(port);
        serverApp.appendToLog("Server started");
    }
}
