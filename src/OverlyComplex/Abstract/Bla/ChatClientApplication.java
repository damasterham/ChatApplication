//package OverlyComplex.Abstract.Bla;
//
//import ChatApplication.Application.ClientApplication;
//import OverlyComplex.Abstract.ProtocolResponse.ClientProtocolResponse;
//import OverlyComplex.Abstract.Controller.ClientController;
//import ChatApplication.Network.ChatSocketUser;
//import ChatApplication.Protocol.ProtocolHandler;
//import OverlyComplex.Abstract.ChatProtocolResponseHandler;
//
///**
// * Created by DaMasterHam on 23-02-2017.
// */
//public class ChatClientApplication
//{
//    private static final String HOST = "localhost";
//
//    private ClientController controller;
//
//    public static void main(String[] args)
//    {
//        ProtocolHandler protocolParser = new ProtocolHandler();
//
//        ClientApplication view = new ClientApplication();
//        ChatSocketUser client;
//        ClientProtocolResponse response = new ClientProtocolResponse(view, client);
//        ChatProtocolResponseHandler clientResponseHandler = new ChatProtocolResponseHandler(response);
//
//
//        if (args.length > 0)
//        {
//            // Create with host from args
//            client = new ChatSocketUser(args[0], Global.PORT,  protocolParser, clientRepsonse);
//        }
//        else
//        {
//            // Create with default host
//            client = new ChatSocketUser(HOST, Global.PORT, protocolParser, clientRepsonse);
//        }
//
//
//        ClientController controller = new ClientController(view, client);
//    }
//}
