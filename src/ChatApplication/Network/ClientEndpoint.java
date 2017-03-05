package ChatApplication.Network;

import ChatApplication.Application.ClientApplication;
import ChatApplication.Protocol.ProtocolHandler;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.nio.channels.ClosedByInterruptException;

public class ClientEndpoint
{
    private String name;

    // Unnecessary since this info is already in Socket
    private String hostname;
    private int port;

    private ClientApplication clientApp;

    private CommunicationEndpoint comm;
    private Thread listener;

    // Constructor (parameters received from ClientApp "initializeClient" method)
    public ClientEndpoint(String hostname, int port, ClientApplication clientApp) throws IOException
    {
        this.hostname = hostname;
        this.port = port;
        this.clientApp = clientApp;
    }

    // Creates a new CommunicationEndpoint and stars listening
    private void connect() throws IOException
    {
        comm = new CommunicationEndpoint(hostname, port);
        listenToServer();
    }

    // Button and TextField method that handles Client messages to the Server after a "J_OK" is received
    // (Gets set when the "joinOK" method have been run)
    private void trySend()
    {
        try
        {
            sendMessage(clientApp.getInput().getText());
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            clientApp.getInput().clear(); // Clears the ClientApp TextField when message is sent
        }
    }

    // **** ACTIONS ****

    // Appends a succesfull "JOIN" message to ClientApp TextArea
    // and changes action of Button and TextField to the "trySend" method
    private void joinOk()
    {
        clientApp.appendToMessageLog("You've joined the server as " + name);

        // Changes the submits buttons action to send messages, instead of request to join
        // ... Here we call FX in client to set the action
        clientApp.getSubmit().setOnAction(e ->
        {
            trySend();

        });

        //
        clientApp.getInput().setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if (event.getCode() == KeyCode.ENTER)
                    trySend();
            }
        });
    }

    // Resets user name to null and output "Error" messages to ClientApp TextArea
    // (Does not change action of Button or TextField - still "tryJoin" method
    private void joinError(String errorMessage)
    {
            name = null;
            clientApp.appendToMessageLog("You failed to join the server due to: \n");
            clientApp.appendToMessageLog(errorMessage);
    }

    // Method that sends Client message to Server after the Client is logged in
    private void sendMessage(String message) throws IOException
    {
        String msg = ProtocolHandler.packMessage(name, message);

        comm.sendData(msg);
    }

    // Appends (DATA) message to ClientApp TextArea
    private void printMessage(String name, String message)
    {
        clientApp.appendToMessageLog(name + ": " + message);
    }

    // Appends list of users (LIST protocol message) to ClientApp TextArea
    private void printUsers(String listOfUsers)
    {
        clientApp.appendToMessageLog("Users Online: " + listOfUsers);
    }

    // Method to act upon the protocol message received from Server
    private void handleProtocol(String data)
    {
        String[] protocol = ProtocolHandler.unPack(data); // I (JONAS) don't get this method ... equals "split" method

        switch (protocol[0])
        {
            case ProtocolHandler.JOIN_OK:
                joinOk();
                break;
            case ProtocolHandler.JOIN_ERR:
            {
                joinError("Name \"" + name + "\" is already taken.");
//                if (protocol.length == 2 && protocol[1].equals(ProtocolHandler.ERR_NAME_TAKEN))
//                    joinError("Name \"" + name + "\" is already taken.");
//                else if (protocol.length == 3 && protocol[1].equals(ProtocolHandler.ERR_NAME_LENGTH))
//                    joinError("\"" + name + "\" is too long, max nr of characters is " + protocol[2]);
            }
                break;
            case ProtocolHandler.MSG:
                if (protocol.length == 3)
                {
                    printMessage(protocol[1], protocol[2]); // Prints name and message to ClientApp TextArea
                }
                break;
            case ProtocolHandler.LIST:
                if (protocol.length > 1)
                {
                    printUsers(protocol[1]);
                }
                    //printUsers(protocol);
                break;
            default:
                System.out.println("Client received message with incorrect protocol syntax:");
                System.out.println(data);
                break;
        }
    }

    // Creates a new Thread for listening for messages coming from Server while CommunicationEndpoint is connected
    private void listenToServer()
    {
        listener = new Thread(() ->
        {
            listener.setName("Endpoint Listener Thread");

            // While Socket is connected and not shutdown then read messages from Server
            while (comm.isConnected())
            {
                System.out.println("Still listening");

                try
                {
                    String data = comm.receiveData(); // Reads messages from InputStream

                    handleProtocol(data); // Handles the received protocol

                }
                // Closing the socket, throws and exceptipon wich is caught here
                // So from here it actually stops the inputstream from receiving data
                // And can exit the thread
                catch (IOException ex)
                {
                    //listener.interrupt();
                    System.out.println("Listener thread broke");
                    ex.printStackTrace();
                }

            }
        });
        listener.start();

    }

    public boolean isConnected()
    {
        if (comm != null)
            return comm.isConnected();

        return false;
    }

    // Creates new CommunicationEndpoint
    // and packs and sends "JOIN" messages while CommunicationEndpoint is connected (i.e. Socket not shutdown)
    public void joinServer(String name) throws IOException
    {
        if (name != "") // Unnecessary check ... (already done in CharValidation)
        {
            // Creates new CommunicationEndpoint and start to listen via new Thread
            connect();

            // Sends the "JOIN" messages
            if (comm.isConnected())
            {
                String joinProtocol = ProtocolHandler.packJoin(name, comm.getLocalIp(), "" + comm.getLocalPort());
                comm.sendData(joinProtocol);
                this.name = name;
            }
        }

    }

    // Sends a QUIT protocol message to server and closes down socket
    public void leaveServer() throws IOException
    {
        // Sends QUIt protocol
//        try
//        {
            //System.out.println(listener.getThreadGroup().toString());
//        }
//        catch (InterruptedException ex)
//        {
//            System.out.println("Listener thread interrupted");
//        }
        comm.sendData(ProtocolHandler.packQuit());

        try
        {
            // Stops the listenerThread
            //stopListening();
            //listener.interrupt();

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

