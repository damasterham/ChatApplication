package Old;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

//Created by DaMasterHam on 20-02-2017.
//
public class ChatClient extends Application
{
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 4200;

    private static final String JOIN = "JOIN {%s} {%s}:{%s}"; // "JOIN {username} {server}:{port}"

    // FX
    private Stage mainStage;
    private BorderPane wrapPane;
    private BorderPane inputBar;
    private Scene mainScene;

    private TextArea messageLog;

    // ServerLogic
    private Socket socket;
    private DataOutputStream clientOutput;
    private DataInputStream clientInput;

    @Override
    public void start(Stage mainStage) throws Exception
    {
        this.mainStage = mainStage;

        wrapPane = new BorderPane();
        inputBar = new BorderPane();

        wrapPane.setTop(inputBar);

        TextField input = new TextField();
        input.setText("Msg");
        Button submit = new Button();
        submit.setText("Send");

        submit.setOnAction(e ->
        {

            joinServer(SERVER_HOST, SERVER_PORT, input.getText());

            //sendData("DATA", input.getText());
        });

        inputBar.setLeft(input);
        inputBar.setRight(submit);

        messageLog = new TextArea();
        messageLog.setWrapText(true);
        messageLog.setDisable(true);

        messageLog.setStyle("-fx-text-fill: #3c9001;");

        wrapPane.setCenter(messageLog);


        //inputBar.getChildren().addAll(input, submit);

        this.mainScene = new Scene(wrapPane, 450, 200);

        this.mainStage.setScene(mainScene);
        this.mainStage.show();

        requestUsername();

    }

    private void appendToMessageLog(String msg)
    {
        messageLog.setText(messageLog.getText() + "\n" + msg);
    }

    private void requestUsername()
    {
        appendToMessageLog("Input your username and send.");
    }

    private void joinServer(String serverHost, int serverPort, String username)
    {
        try
        {
            initilizeData(serverHost, serverPort);
            sendMessage(JOIN, username);

        }
        catch (IOException ex)
        {
            appendToMessageLog("Failed to connect to server");
        }

    }

    private void initilizeData(String serverHost, int serverPort) throws IOException
    {
        socket = new Socket(serverHost, serverPort);
        clientInput = new DataInputStream(socket.getInputStream());
        clientOutput = new DataOutputStream(socket.getOutputStream());

    }

    private void sendMessage(String protocol, String message) throws IOException
    {
        String[] proto = protocol.split(" ");
//        String compiledProtocol = "";

        switch (proto[0])
        {
            case "JOIN":clientOutput.writeBytes(String.format(protocol, message, SERVER_HOST, SERVER_PORT));
                break;
        }

//        clientOutput.writeBytes(message);
    }

    private void receiveMessage() throws IOException
    {
        String msg = clientInput.readUTF();
        switch (msg)
        {
            case "J_OK":appendToMessageLog("You are now logged in!");
                break;
            case "J_ERR":appendToMessageLog("Username already exists");
        }
    }
}
