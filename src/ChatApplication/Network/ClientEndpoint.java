package ChatApplication.Network;

import ChatApplication.Application.ClientApplication;
import ChatApplication.Protocol.ProtocolHandler;
import javafx.application.Platform;

import java.io.IOException;

public class ClientEndpoint
{
    private String name;
    private String hostname;
    private int port;

    private ClientApplication clientApp;
    private CommunicationEndpoint comm;
    private Thread listener;

    public ClientEndpoint(String hostname, int port, ClientApplication clientApp) throws IOException
    {
        this.hostname = hostname;
        this.port = port;
        this.clientApp = clientApp;
    }

    private void connect() throws IOException
    {
        comm = new CommunicationEndpoint(hostname, port);
        listenToServer();
    }


    // Actions
    private void joinOk()
    {
        clientApp.appendToMessageLog("You've joined the server as " + name);

        // Changes the submits buttons action to send messages, instead of request to join
        // ... Here we call FX in client to set the action
        clientApp.getSubmit().setOnAction(e ->
        {
            try
            {
                sendMessage(clientApp.getInput().getText());
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        });
    }

    private void joinError(String errorMessage)
    {
        Platform.runLater(() ->
        {
            name = null;
            clientApp.appendToMessageLog("You failed to join the server due to: \n");
            clientApp.appendToMessageLog(errorMessage);
        });
    }

    private void sendMessage(String message) throws IOException
    {
        String msg = ProtocolHandler.packMessage(name, message);

        comm.sendData(msg);
    }

    private void printMessage(String name, String message)
    {
        clientApp.appendToMessageLog(name + ": " + message);
    }

    private void printUsers(String listOfUsers)
    {
        clientApp.appendToMessageLog("Users Online: " + listOfUsers);
    }

    // Receiving
    private void handleProtocol(String data) {
        String[] protocol = ProtocolHandler.unPack(data);

        switch (protocol[0]) {
            case ProtocolHandler.JOIN_OK:
                joinOk();
                break;
            case ProtocolHandler.JOIN_ERR:
            {
                if (protocol.length == 2 && protocol[1] == ProtocolHandler.ERR_NAME_TAKEN)
                    joinError(name + " is already taken.");
                else if (protocol.length == 3 && protocol[1] == ProtocolHandler.ERR_NAME_LENGTH)
                    joinError(name + " is too long, max nr of characters is " + protocol[2]);
            }
                break;
            case ProtocolHandler.MSG:
                if (protocol.length == 3)
                    printMessage(protocol[1], protocol[2]);
                break;
            case ProtocolHandler.LIST:
                if (protocol.length > 1)
                    printUsers(protocol[1]);
                    //printUsers(protocol);
                break;
            default:
                System.out.println("Client received message with incorrect protocol syntax:");
                System.out.println(data);
                break;
        }
    }

    private void listenToServer()
    {
            listener = new Thread(() ->
            {

                try
                {
                    while (comm.isConnected())
                    {
                        String data = comm.receiveData();

                        handleProtocol(data);
                    }
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            });

            listener.start();
    }


    // Sending
    public void joinServer(String name) throws IOException
    {
        if (name != "")
        {
            connect();

            if (comm.isConnected())
            {
                String joinProtocol = ProtocolHandler.packJoin(name, comm.getLocalIp(), "" + comm.getLocalPort());
                comm.sendData(joinProtocol);
                this.name = name;
            }
        }

    }

    // Sends a QUIT protocol to server and disconnects
    public void leaveServer() throws IOException
    {
        // Sends QUIt protocol
        comm.sendData(ProtocolHandler.packQuit());

        try
        {
            // Stops the listenerThread
            //stopListening();
            listener.interrupt();

            // Closes the socket connection
            comm.disconnect();

            //listener.join();
        }
        catch (IOException ex)
        {
            clientApp.appendToMessageLog("EXCEPTION: Sever already disconnected you");
        }
//        catch (InterruptedException ex)
//        {
//            clientApp.appendToMessageLog("EXCEPTION: Listener thread join interupted");
//        }
    }



//    private void printUsers(String[] listOfUsers)
//    {
//        Platform.runLater(() ->
//        {
//            String list = "Users Online: ";
//
//            for (int i = 1; i < list.length() -1; i++)
//            {
//                list += listOfUsers[i] + ", ";
//            }
//
//            list += listOfUsers[listOfUsers.length - 1];
//            clientApp.appendToMessageLog(list);
//        });
//    }
}


//     switch (protocolValues[0])
//    {
//        case ProtocolHandler.JOIN : if (protocolValues.length == 4)
//            response.join(protocolValues[1], protocolValues[2], protocolValues[3]);
//            break;
//        case ProtocolHandler.JOIN_OK : response.joinOk();
//            break;
//        case ProtocolHandler.JOIN_ERR : response.joinError();
//            break;
//        case ProtocolHandler.MSG : if (protocol.length == 3)
//            response.message(protocolValues[1], protocolValues[2]);
//            break;
//        case ProtocolHandler.LIST : if (protocolValues.length > 1)
//            response.list(protocolValues);
//    }

