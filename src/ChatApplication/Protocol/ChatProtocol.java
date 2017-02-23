package ChatApplication.Protocol;//

//Created by DaMasterHam on 23-02-2017.
//
public class ChatProtocol
{
    // Server -> Client
    private static final String JOIN_OK = "J_OK";
    private static final String JOIN_ERR = "J_ERR";
    private static final String LIST = "LIST";

    // Client -> Server
    private static final String JOIN = "JOIN";
    private static final String ALIVE = "ALIVE";
    private static final String QUIT = "QUIT";

    // Both
    private static final String MSG = "MSG";



    private static final String JOIN_PROTOCOL = JOIN + " {%s} {%s} {%s}";// JOIN {user_name} {server_ip} {server_port}
    private static final String MSG_PROTOCOL = MSG + " {%s} {%s}";// MSG {user_name} {message}
    private static final String LIST_PROTOCOL = LIST + " {%s}";


    private String delimiter = " ";

    private IProtocolActions protocolMessage;

    public ChatProtocol(IProtocolActions protocolMessage)
    {
        this.protocolMessage = protocolMessage;
    }

    public void parse(String message)
    {
        String[] protocol = ProtocolStringParser.getParameters(message);

        switch (protocol[0])
        {
            case JOIN : if (protocol.length == 4)
                    protocolMessage.join(protocol[1], protocol[2], protocol[3]);
                break;
            case JOIN_OK : protocolMessage.joinOk();
                break;
            case JOIN_ERR : protocolMessage.joinError();
                break;
            case MSG : if (protocol.length == 3)
                protocolMessage.sendMessage(protocol[1], protocol[2]);
                break;
        }
    }

    // Join protocol
    // JOIN {user_name} {server_ip} {server_port}
    public String join(String name, String serverIp, String serverPort)
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
    }



    public static void main(String[] args)
    {
        ChatProtocol protocol = new ChatProtocol(new ClientProtocolActions());

        protocol.parse("JOIN {Bob} {123 456} {Woop}");
    }

}
