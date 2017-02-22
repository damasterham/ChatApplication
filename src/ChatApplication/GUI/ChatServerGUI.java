package ChatApplication.GUI;//

import ChatApplication.Network.ChatServer;
import ChatApplication.Global;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

//Created by DaMasterHam on 21-02-2017.
//
public class ChatServerGUI extends Application
{
    // FX
    private Stage mainStage;
    private BorderPane wrapPane;
    private BorderPane inputBar;
    private Scene mainScene;

    private Button startButton;
    private Button stopButton;

    private Label log;

    // Server
    private ChatServer server;

    private void runServer()
    {
//        new Thread(() ->
//        {
            try
            {
                server = new ChatServer(Global.PORT);
                server.startServer();
                //appendToLog("Started Server");
                server.startReceivingAsync();
                //appendToLog("Ready to Receive clients");
                //appendToLog(server.getClient().getLocalAddress().getHostAddress());
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
//        }).start();


    }

    private void stopServer()
    {
        try
        {
            server.stopServer();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void appendToLog(String msg)
    {
        log.setText(log.getText() + "\n" + msg);
        mainStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        mainStage = primaryStage;

        wrapPane = new BorderPane();
        inputBar = new BorderPane();

        startButton = new Button("Start Server");
        startButton.setOnAction(e ->
        {
            runServer();
        });
        stopButton = new Button("Stop Server");
        stopButton.setOnAction(e ->
        {
            stopServer();
        });

        log = new Label();
        log.setText("LOG");
        log.setWrapText(true);

        inputBar.setLeft(startButton);
        inputBar.setRight(stopButton);

        wrapPane.setTop(inputBar);
        wrapPane.setCenter(log);

        mainScene = new Scene(wrapPane, 450, 200);

        mainStage.setScene(mainScene);
        mainStage.show();
    }
}
