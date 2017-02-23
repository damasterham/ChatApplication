package ChatApplication.Protocol;//

import ChatApplication.GUI.ChatClientGUI;
import javafx.application.Platform;

//Created by DaMasterHam on 23-02-2017.
//
public class ClientProtocolActions implements IProtocolActions
{
    private ChatClientGUI gui;

    public ClientProtocolActions(ChatClientGUI gui)
    {

    }

    @Override
    public void join()
    {

    }

    @Override
    public void joinOk()
    {

        Platform.runLater(() -> gui.appendToMessageLog(msg));
        submit.setOnAction(new ChatClientGUI.Message());

        gui.listenToMessages();

    }

    @Override
    public void joinError() {

    }

    @Override
    public void sendMessage() {

    }
}
