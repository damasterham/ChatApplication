package ChatApplication.Controller;

import ChatApplication.GUI.ChatClientGUI;
import ChatApplication.Network.ChatSocketUser;
import ChatApplication.Protocol.ChatProtocol;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

/**
 * Created by DaMasterHam on 23-02-2017.
 */
public class ClientController
{
    private ChatClientGUI view;
    private ChatSocketUser client;
    private ChatProtocol protocol;

    public ClientController(ChatClientGUI view, ChatSocketUser client, ChatProtocol protocol)
    {
        this.view = view;
        javafx.application.Application.launch(view.getClass(), "");
        javafx.application.Application.launch();
        this.client = client;
        this.protocol = protocol;

        view.getSubmit().setOnAction(new Connect());
    }

    private class Connect implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            try
            {
                    String name = view.getInput().getText();

                    client.connect();
                    client.sendMessage(protocol.join(name, "WhateverIp", "WhateverPort"));
                    protocol.parse(client.receiveMessage());

            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private class Message implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
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
    }

}
