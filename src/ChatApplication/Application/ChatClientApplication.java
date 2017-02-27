package ChatApplication.Application;

import ChatApplication.Application.ProtocolResponse.ClientProtocolResponse;
import ChatApplication.Controller.ClientController;
import ChatApplication.GUI.ChatClientGUI;
import ChatApplication.Global;
import ChatApplication.Network.ChatSocketUser;
import ChatApplication.Protocol.Abstract.IChatProtocolResponse;
import ChatApplication.Protocol.ChatProtocolParser;
import ChatApplication.Protocol.ChatProtocolResponseHandler;

/**
 * Created by DaMasterHam on 23-02-2017.
 */
public class ChatClientApplication
{
    private static final String HOST = "localhost";

    private ClientController controller;

    public static void main(String[] args)
    {
        ChatProtocolParser protocolParser = new ChatProtocolParser();

        ChatClientGUI view = new ChatClientGUI();
        ChatSocketUser client;
        ClientProtocolResponse response = new ClientProtocolResponse(view, client);
        ChatProtocolResponseHandler clientResponseHandler = new ChatProtocolResponseHandler(response);


        if (args.length > 0)
        {
            // Create with host from args
            client = new ChatSocketUser(args[0], Global.PORT,  protocolParser, clientRepsonse);
        }
        else
        {
            // Create with default host
            client = new ChatSocketUser(HOST, Global.PORT, protocolParser, clientRepsonse);
        }


        ClientController controller = new ClientController(view, client);
    }
}
