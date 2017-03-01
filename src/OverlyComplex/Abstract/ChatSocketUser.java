//package ChatApplication.Network;//
//
//import ChatApplication.Network.CommunicationEndpoint;
//import ChatApplication.Protocol.ProtocolHandler;
//import OverlyComplex.Abstract.ChatProtocolResponseHandler;
//
//import java.io.IOException;
//import java.net.Socket;
//
////Created by DaMasterHam on 21-02-2017.
////
//public class ChatSocketUser extends CommunicationEndpoint
//{
//    private String hostUrl;
//    private int port;
//    private String name;
//
//    private ProtocolHandler protocolParser;
//    private ChatProtocolResponseHandler protocolResponseHandler;
//
//    public ChatSocketUser(String host, int port, ProtocolHandler protocolParser, ChatProtocolResponseHandler protocolResponseHandler)
//    {
//        this.hostUrl = host;
//        this.port = port;
//
//        this.protocolParser = protocolParser;
//        this.protocolResponseHandler = protocolResponseHandler;
//    }
//
//    public String getName()
//    {
//        return name;
//    }
//
//    public void setName(String name)
//    {
//        this.name = name;
//    }
//
//    public void connect() throws IOException
//    {
//        connect(hostUrl, port);
//    }
//
//    public void connect(String hostUrl, int port) throws IOException
//    {
//        host = new Socket(hostUrl, port);
//        initializeStreams();
//    }
//
//    public void tryJoin(String name) throws IOException
//    {
//        sendData(protocolParser.packJoin(name, "N/A", "N/A"));
//    }
//
//    public void sendData(String msg) throws IOException
//    {
//        sendData(protocolParser.packMessage(name, msg));
//    }
//
//    public void receiveData() throws IOException
//    {
//        protocolResponseHandler.parse(receiveData());
//    }
//
//
//
////    public void sendData(String message)throws IOException
////    {
////        output.writeUTF(message);
////        output.flush();
////    }
////    public String receiveData() throws IOException
////    {
////        return input.readUTF();
////    }
//}
