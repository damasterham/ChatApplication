package ChatApplication.Network;//

import ChatApplication.Network.Abstract.ChatSocket;
import ChatApplication.Protocol.Abstract.IChatProtocolResponse;
import ChatApplication.Protocol.ChatProtocolParser;
import ChatApplication.Protocol.ChatProtocolResponseHandler;

import java.io.IOException;
import java.net.Socket;

//Created by DaMasterHam on 21-02-2017.
//
public class ChatSocketUser extends ChatSocket
{
    private String hostUrl;
    private int port;
    private String name;

    private ChatProtocolParser protocolParser;
    private ChatProtocolResponseHandler protocolResponseHandler;

    public ChatSocketUser(String host, int port, ChatProtocolParser protocolParser, ChatProtocolResponseHandler protocolResponseHandler)
    {
        this.hostUrl = host;
        this.port = port;

        this.protocolParser = protocolParser;
        this.protocolResponseHandler = protocolResponseHandler;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void connect() throws IOException
    {
        connect(hostUrl, port);
    }

    public void connect(String hostUrl, int port) throws IOException
    {
        host = new Socket(hostUrl, port);
        initializeStreams();
    }

    public void tryJoin(String name) throws IOException
    {
        sendString(protocolParser.packJoin(name, "N/A", "N/A"));
    }

    public void sendMessage(String msg) throws IOException
    {
        sendString(protocolParser.packMessage(name, msg));
    }

    public void receiveMessage() throws IOException
    {
        protocolResponseHandler.parse(receiveString());
    }



//    public void sendString(String message)throws IOException
//    {
//        output.writeUTF(message);
//        output.flush();
//    }
//    public String receiveString() throws IOException
//    {
//        return input.readUTF();
//    }
}
