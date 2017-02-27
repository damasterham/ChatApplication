package ChatApplication.GUI;//

import ChatApplication.Network.ChatSocketUser;
import ChatApplication.Protocol.ChatProtocolResponseHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

//Created by DaMasterHam on 21-02-2017.
//
public class ChatClientGUI extends Application
{
    // Move Fx to ChatSocketUser
    private ChatSocketUser client;
    private ChatProtocolResponseHandler protocol;

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

    public void launchFx()
    {
        launch();
    }

//    public void initializeClient()
//    {
//        client = new ChatSocketUser("localhost", Global.PORT);
//        protocol = new ChatProtocolResponseHandler(new ClientProtocolResponse());
//    }



    public void chat() throws IOException
    {

    }



    public void appendToMessageLog(String msg)
    {
        messageLog.setText(messageLog.getText() + "\n" + msg);
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

        //initializeClient();
    }
/*

    private class Connect implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            try
            {
                joinServer();
                //Change to Message
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
*/



}
