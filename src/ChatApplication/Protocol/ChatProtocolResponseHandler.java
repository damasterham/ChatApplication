package ChatApplication.Protocol;//

import ChatApplication.Protocol.Abstract.IChatProtocolResponse;

//Created by DaMasterHam on 23-02-2017.
//
public class ChatProtocolResponseHandler
{

    private IChatProtocolResponse response;

    public ChatProtocolResponseHandler(IChatProtocolResponse response)
    {
        this.response = response;
    }

    public void parse(String message)
    {
        String[] protocol = ChatProtocolParser.unPack(message);

        switch (protocol[0])
        {
            case ChatProtocolParser.JOIN : if (protocol.length == 4)
                    response.join(protocol[1], protocol[2], protocol[3]);
                break;
            case ChatProtocolParser.JOIN_OK : response.joinOk();
                break;
            case ChatProtocolParser.JOIN_ERR : response.joinError();
                break;
            case ChatProtocolParser.MSG : if (protocol.length == 3)
                response.message(protocol[1], protocol[2]);
                break;
            case ChatProtocolParser.LIST : if (protocol.length > 1)
                response.list(protocol);
        }
    }

    // Join protocol
    // JOIN {user_name} {server_ip} {server_port}
/*    public String join(String name, String serverIp, String serverPort)
    {
        return String.format(JOIN_PROTOCOL, name, serverIp, serverPort);
    }

    public String joinOk()
    {
        return JOIN_OK;
    }

    public String joinError()
    {
        return JOIN_ERR;
    }

    public String sendMessage(String name, String message)
    {
        return String.format(MSG_PROTOCOL, name, message);
}*/



//    public static void main(String[] args)
//    {
//        ChatProtocolResponseHandler protocol = new ChatProtocolResponseHandler(new ClientProtocolResponse());
//
//        protocol.parse("JOIN {Bob} {123 456} {Woop}");
//    }

}
