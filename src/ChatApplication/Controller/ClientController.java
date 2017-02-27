package ChatApplication.Controller;

import ChatApplication.GUI.ChatClientGUI;
import ChatApplication.Network.ChatSocketUser;

import java.io.IOException;

/**
 * Created by DaMasterHam on 23-02-2017.
 */
public class ClientController
{
    private ChatClientGUI view;
    private ChatSocketUser client;

    private Thread listenerThread;


    public ClientController(ChatClientGUI view, ChatSocketUser client)
    {
        this.view = view;
        javafx.application.Application.launch(view.getClass(), "");
        javafx.application.Application.launch();
        this.client = client;

        view.getSubmit().setOnAction(e -> join());
    }

    public void join()
    {
        try
        {
            String name = view.getInput().getText();

            client.connect();

            client.tryJoin(name);
            //client.sendString(protocol.join(name, "WhateverIp", "WhateverPort"));
            //protocol.parse(client.receiveString());



        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void joinSuccess()
    {
        client.sendMessage(name + " joined the server");
    }

    public void sendMessage()
    {
        try
        {
            client.sendMessage(view.getInput().getText());
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    // Runs a new thread that listens and writes messages to message log
    public void listenToMessages()
    {
        listenerThread = new Thread(() ->
        {
            try
            {
                while (client.isConnected())
                {
                    client.receiveMessage();
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }

        });

        listenerThread.start();
    }

//    private class Connect implements EventHandler<ActionEvent>
//    {
//        @Override
//        public void handle(ActionEvent event)
//        {
//            join();
//        }
//    }
//
//    private class Message implements EventHandler<ActionEvent>
//    {
//        @Override
//        public void handle(ActionEvent event)
//        {
//            sendString();
//        }
//    }

}
