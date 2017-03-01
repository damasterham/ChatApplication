package OverlyComplex.Abstract.ProtocolResponse;//

import ChatApplication.Application.ClientApplication;
import ChatApplication.Network.ChatSocketUser;
import OverlyComplex.Abstract.IChatProtocolResponse;
import javafx.application.Platform;

import java.io.IOException;

//Created by DaMasterHam on 23-02-2017.
//
public class ClientProtocolResponse implements IChatProtocolResponse
{
    private static final String LOGIN_FAIL_MSG = "Your name is already taken, please try another";

    private ClientApplication view;
    private ChatSocketUser client;

    public ClientProtocolResponse(ClientApplication gui, ChatSocketUser client)
    {
        this.view = view;
        this.client = client;
    }

    // If client name is accepted set submit button to send messages
    @Override
    public void joinOk()
    {
        view.getSubmit().setOnAction(e ->
        {
            try
            {
                client.sendMessage(view.getInput().getText());
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        });
    }

    // If client name is denied, print failed message
    @Override
    public void joinError()
    {
        Platform.runLater(() -> view.appendToMessageLog(LOGIN_FAIL_MSG));
    }

    @Override
    public void message(String name, String message)
    {
        Platform.runLater(() -> view.appendToMessageLog(name + ": " + message));
    }

    // No used by client
    @Override
    public void list(String... users) {

    }

    @Override
    public void join(String name, String serverIp, String serverPort)
    {

    }
}
