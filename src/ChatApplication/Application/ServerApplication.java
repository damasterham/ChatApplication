package ChatApplication.Application;//

import ChatApplication.Network.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

// Gui and Control of flow
public class ServerApplication extends Application
{
    // Network
    private Server server;

    // FX
    private Stage mainStage;
    private BorderPane wrapPane;
    private BorderPane inputBar;
    private Scene mainScene;

    private Button startButton;
    private Button stopButton;

    private TextArea log;

    private void runServer()
    {
//        new Thread(() ->
//        {
            try
            {
                server = new Server(Server.PORT, this);
                server.startServer();
                //appendToLog("Started Server");
                server.startReceiving();
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

    public void appendToLog(String msg)
    {
        Platform.runLater(() ->
        {
            log.setText(log.getText() + "\n" + msg);
            mainStage.show();
        });

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

        log = new TextArea();
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
