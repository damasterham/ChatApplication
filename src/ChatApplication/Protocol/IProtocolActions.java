package ChatApplication.Protocol;//

//Created by DaMasterHam on 23-02-2017.
//
public interface IProtocolActions
{
    void join(String name, String serverIp, String serverPort);
    void joinOk();
    void joinError();
    void sendMessage(String name, String message);
}
