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
    private TextArea log;

    private BorderPane inputBar;
    private Button startButton;
    private Button stopButton;

    private Scene mainScene;

    // Creates a Server, starts it and starts listening
    private void runServer()
    {
//        new Thread(() ->
//        {
            try
            {
                server = new Server(Server.PORT, this);
                server.startServer();
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

        // Main Layout
        wrapPane = new BorderPane();

        log = new TextArea();
        log.setText("LOG");
        log.setWrapText(true);

        wrapPane.setCenter(log);

        // Top Main Layout
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

        inputBar.setLeft(startButton);
        inputBar.setRight(stopButton);

        wrapPane.setTop(inputBar);

        // Scene
        mainScene = new Scene(wrapPane, 450, 200);

        // Stage
        mainStage.setScene(mainScene);
        mainStage.show();
    }
}
