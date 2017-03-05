package ChatApplication.Application;//

import ChatApplication.Network.ClientEndpoint;
import ChatApplication.Network.Server;
import ChatApplication.Validation.ChatValidation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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

    // Method to try and join Server if chosen user name conforms to validation
    private void tryJoin()
    {
        try
        {
            // Gets user name from TextField
            String name = getInput().getText();

            // Checks if user name contains valid chars
            if (ChatValidation.usesValidChar(name))
            {
                // Checks if user has valid length
                if (ChatValidation.isNameLengthValid(name))
                {
                    client.joinServer(getInput().getText());
                }
                else
                {
                    appendToMessageLog("Name to long, max " + ChatValidation.MAX_NAME_LENGTH + " characters");
                }
            }
            else
                appendToMessageLog("Not using valid characters, only " + ChatValidation.VALID_CHARS  + " are allowed");
        }
        catch (IOException ex)
        {
            appendToMessageLog("Server is not running");
            ex.printStackTrace();
        }
        finally
        {
            input.clear();
        }
    }

    public TextField getInput() {
        return input;
    }

    // Get the submit button
    public Button getSubmit() {
        return submit;
    }

    public TextArea getMessageLog() {
        return messageLog;
    }

    // Initializes the Client Endpoint and passes a reference of the ChatApplication object
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

    // Appends received messages to TextArea
    public void appendToMessageLog(String msg)
    {
        Platform.runLater(() ->
                messageLog.setText(msg  + "\n" +  messageLog.getText())
        );
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        System.out.println(Thread.activeCount());

        // Stage
        mainStage = primaryStage;

        // Main Layout
        wrapPane = new BorderPane();

        messageLog = new TextArea();
        messageLog.setWrapText(true);
        messageLog.setDisable(false);

        wrapPane.setCenter(messageLog);

        // Top Main Layout
        inputBar = new BorderPane();

        input = new TextField();
        input.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if (event.getCode() == KeyCode.ENTER)
                    tryJoin();
            }
        });

        // Sets the button to fire the Connect event first, will later be set to message
        //submit.setOnAction(new Connect());

        submit = new Button();
        submit.setText("Send");
        submit.setOnAction(e ->
        {
            tryJoin();
        });

        inputBar.setLeft(input);
        inputBar.setRight(submit);

        wrapPane.setTop(inputBar);

        // Scene and Stage
        mainScene = new Scene(wrapPane, 450, 200);

        mainStage.setScene(mainScene);
        mainStage.show();

        // Sets the close window button event
        mainStage.setOnCloseRequest(new Shutdown());

        // Creates the ClientEndpoint
        initializeClient();

        appendToMessageLog("Write you desired chat name:");
    }

    // Closes and Shutdown Socket when ClientApp window is shutdown/closed
    private class Shutdown implements EventHandler<WindowEvent>
    {
        @Override
        public void handle(WindowEvent event)
        {
            try
            {
                if (client.isConnected())
                {
                    client.leaveServer();
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }


}
