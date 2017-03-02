package ChatApplication.Application;//

import ChatApplication.Network.ClientEndpoint;
import ChatApplication.Network.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

// Gui and Control of flow
public class ClientApplication extends Application
{
    // Network
    private ClientEndpoint client;

    // FX
    private Stage mainStage;
    private BorderPane wrapPane;
    private BorderPane inputBar;
    private Scene mainScene;

    private TextField input;
    private Button submit;
    private TextArea messageLog;



    public TextField getInput() {
        return input;
    }

    public Button getSubmit() {
        return submit;
    }

    public TextArea getMessageLog() {
        return messageLog;
    }

    public void initializeClient()
    {
        try
        {
            client = new ClientEndpoint("localhost", Server.PORT, this);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void appendToMessageLog(String msg)
    {
        Platform.runLater(() ->
                messageLog.setText(messageLog.getText() + "\n" + msg)
        );
    }


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Stage
        mainStage = primaryStage;

        // Layout
        wrapPane = new BorderPane();
        inputBar = new BorderPane();

        wrapPane.setTop(inputBar);

        // Inputs
        input = new TextField();
        input.setText("Msg");
        submit = new Button();
        submit.setText("Send");
        submit.setOnAction(new Connect());

        // Sets the button to fire the Connect event first, will later be set to message
        //submit.setOnAction(new Connect());

        inputBar.setLeft(input);
        inputBar.setRight(submit);

        // Output
        messageLog = new TextArea();
        messageLog.setWrapText(true);
        messageLog.setDisable(false);

        //messageLog.setStyle("-fx-text-fill: #3c9001;");

        wrapPane.setCenter(messageLog);

        //inputBar.getChildren().addAll(input, submit);

        mainScene = new Scene(wrapPane, 450, 200);
        mainStage.setScene(mainScene);
        mainStage.show();

        initializeClient();
    }

    @Override
    public void stop() throws Exception
    {
        try
        {
            client.leaveServer();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        super.stop();
    }

    // Here we call client in FX to set the action...
    private class Connect implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            try
            {
                client.joinServer(getInput().getText());
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }





}
