package OverlyComplex.Abstract.ProtocolResponse;//

import ChatApplication.Network.Server;
import OverlyComplex.Abstract.IChatProtocolResponse;

import java.io.IOException;

//Created by DaMasterHam on 27-02-2017.
//
public class ServerProtocolResponse implements IChatProtocolResponse
{
    private Server server;

    public ServerProtocolResponse(Server server)
    {
        this.server = server;
    }

    @Override
    public void join(String name, String serverIp, String serverPort)
    {
        new Thread(() ->
        {
            try
            {
                System.out.println("Client trying \""+name+"\"");
                // Check is client with username already exists
                if (isNameTaken(name))
                {
                    System.out.println("Name is taken");
                    client.sendString("Name already taken");
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
                                String msg = client.receiveString();
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


    @Override
    public void message(String name, String message)
    {
        server.sendMessageToAll(name, message);
    }

    @Override
    public void list(String... users)
    {

    }


    // Not used by server

    @Override
    public void joinOk() {

    }

    @Override
    public void joinError() {

    }
}
