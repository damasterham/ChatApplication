package ChatApplication.Protocol;//

//Created by DaMasterHam on 23-02-2017.
//
public class Protocol
{
    private static final String JOIN = "JOIN";
    private static final String JOIN_OK = "J_OK";
    private static final String JOIN_ERR = "J_ERR";
    private static final String MSG = "MSG";

    private String delimiter = " ";

    private IProtocolMessage protocolMessage;

    public Protocol(IProtocolMessage protocolMessage)
    {
        this.protocolMessage = protocolMessage;


    }

    public void parse(String message)
    {
        String[] protocol = message.split(delimiter);

        switch (protocol[0])
        {
            case JOIN : protocolMessage.join();
                break;
            case JOIN_OK : protocolMessage.joinOk();
                break;
            case JOIN_ERR : protocolMessage.joinError();
                break;
            case MSG : protocolMessage.sendMessage();
                break;
        }
    }

}
