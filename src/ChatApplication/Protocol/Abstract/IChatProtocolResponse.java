package ChatApplication.Protocol.Abstract;//

//Created by DaMasterHam on 23-02-2017.
//
public interface IChatProtocolResponse
{
    // Method for handling receiving a join protocol
    void join(String name, String serverIp, String serverPort);
    // Method for handling receiving a joinOk protocol
    void joinOk();
    // Method for packing a JOIN Error protocol
    void joinError();
    // Method for packing a MSG protocol
    void message(String name, String message);

    void list(String... users);
}
