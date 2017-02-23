package ChatApplication.Protocol;//

//Created by DaMasterHam on 23-02-2017.
//
public interface IProtocolMessage
{
    void join();
    void joinOk();
    void joinError();
    void sendMessage();
}
