package ChatApplication.GUI;//

import ChatApplication.GUI.Socket.ChatClient;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.applet.Applet;
import java.io.IOException;

//Created by DaMasterHam on 21-02-2017.
//
public class ChatClientGUI extends Application
{
    private ChatClient client;

    // FX
    private Stage mainStage;
    private BorderPane wrapPane;
    private BorderPane inputBar;
    private Scene mainScene;

    private TextField input;
    private Button submit;
    private TextArea messageLog;

    private void initialize()
    {
        client = new ChatClient("localhost", 4200);
    }

    private void appendToMessageLog(String msg)
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

        submit.setOnAction(e ->
        {
            try
            {
                client.connect();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        });

        inputBar.setLeft(input);
        inputBar.setRight(submit);

        // Output
        messageLog = new TextArea();
        messageLog.setWrapText(true);
        messageLog.setDisable(true);

        messageLog.setStyle("-fx-text-fill: #3c9001;");

        wrapPane.setCenter(messageLog);

        //inputBar.getChildren().addAll(input, submit);

        mainScene = new Scene(wrapPane, 450, 200);
        mainStage.setScene(mainScene);
        mainStage.show();

        initialize();
    }


}
