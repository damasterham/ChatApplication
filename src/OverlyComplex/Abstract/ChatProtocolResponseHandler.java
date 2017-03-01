//package OverlyComplex.Abstract;//
//
//import ChatApplication.Protocol.ProtocolHandler;
//
////Created by DaMasterHam on 23-02-2017.
////
//public class ChatProtocolResponseHandler
//{
//
//    private IChatProtocolResponse response;
//
//    public ChatProtocolResponseHandler(IChatProtocolResponse response)
//    {
//        this.response = response;
//    }
//
//    public void parse(String message)
//    {
//        String[] protocol = ProtocolHandler.unPack(message);
//
//        switch (protocol[0])
//        {
//            case ProtocolHandler.JOIN : if (protocol.length == 4)
//                    response.join(protocol[1], protocol[2], protocol[3]);
//                break;
//            case ProtocolHandler.JOIN_OK : response.joinOk();
//                break;
//            case ProtocolHandler.JOIN_ERR : response.joinError();
//                break;
//            case ProtocolHandler.MSG : if (protocol.length == 3)
//                response.message(protocol[1], protocol[2]);
//                break;
//            case ProtocolHandler.LIST : if (protocol.length > 1)
//                response.list(protocol);
//        }
//    }
//
//    // Join protocol
//    // JOIN {user_name} {server_ip} {server_port}
///*    public String join(String name, String serverIp, String serverPort)
//    {
//        return String.format(JOIN_PROTOCOL, name, serverIp, serverPort);
//    }
//
//    public String joinOk()
//    {
//        return JOIN_OK;
//    }
//
//    public String joinError()
//    {
//        return JOIN_ERR;
//    }
//
//    public String sendData(String name, String message)
//    {
//        return String.format(MSG_PROTOCOL, name, message);
//}*/
//
//
//
////    public static void main(String[] args)
////    {
////        ChatProtocolResponseHandler protocol = new ChatProtocolResponseHandler(new ClientProtocolResponse());
////
////        protocol.parse("JOIN {Bob} {123 456} {Woop}");
////    }
//
//}

//
//
//private void veasdrifyClient(ServerEndpoint client, String wantedName)
//        {
//        new Thread(() ->
//        {
//        try
//        {
//        System.out.println("Client trying \""+name+"\"");
//        // Check is client with username already exists
//        if (isNameTaken(name))
//        {
//        System.out.println("Name is taken");
//        client.sendString("Name already taken");
//        }
//        else
//        {
//        System.out.println("Name is NOT taken");
//        client.setName(name);
//        serverClientThreads.add(client);
//        sendMessageToAll(name + " joined server");
//
//        // Client is good and new thread is started for them to write and receive messages
//        new Thread(() ->
//        {
//        try
//        {
//        System.out.println(client.getName() + " is listening");
//        while (client.isConnected())
//        {
//        String msg = client.receiveString();
//        sendMessageToAll(msg);
//        }
//        }
//        catch (IOException ex)
//        {
//
//        }
//        }).start();
//        }
//        }
//        catch (IOException ex)
//        {
//
//        }
//        }).start();
//        }

