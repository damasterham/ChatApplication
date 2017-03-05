package ChatApplication.Network;

import java.io.IOException;
import java.net.Socket;

public class ServerEndpoint
{
    private String name;
    private String ip;
    private String port;

    private Thread listener;
    private CommunicationEndpoint comm;

    public ServerEndpoint(Socket socket) throws IOException
    {
        comm = new CommunicationEndpoint(socket);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Thread getListener()
    {
        return listener;
    }

    public void setListener(Thread listener) {
        this.listener = listener;
    }

    // Sending
    public void joinOk()
    {

    }

    public void joinError()
    {

    }

    public void listUsers()
    {

    }

    public void sendData(String protocol) throws IOException
    {
        comm.sendData(protocol);
    }

    public String receiveData() throws IOException
    {
        return comm.receiveData();
    }

    public boolean isConnected()
    {
        return comm.isConnected();
    }

    public void disconnect() throws IOException
    {
        comm.disconnect();
    }

}
