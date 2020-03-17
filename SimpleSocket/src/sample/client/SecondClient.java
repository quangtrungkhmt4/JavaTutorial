package sample.client;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SecondClient extends Application {

    private String serverAddress;
    private Scanner input;
    private PrintWriter output;

    public void init(){
        this.serverAddress = "localhost";
    }

    public void showDialogEnterName(){
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);

        TextField textName = new TextField();
        Button btnSendName = new Button("Enter");
        btnSendName.setOnAction(action -> {

        });
        VBox vbox = new VBox(textName, btnSendName);
        vbox.setAlignment(Pos.CENTER);
//        vbox.setPadding(new Insets(15));

        dialogStage.setTitle("Enter your name");
        dialogStage.setScene(new Scene(vbox, 200, 100));
        dialogStage.show();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        init();
//        showDialogEnterName();

        TextField textName = new TextField();
        VBox vbox = new VBox(textName);
        vbox.setAlignment(Pos.CENTER);

        TextArea listMessage = new TextArea();
        listMessage.setPrefHeight(550);
        TextField textEnter = new TextField();
        textEnter.setPrefWidth(456);
        Button btnSend = new Button("Send");
        btnSend.setDisable(true);
        btnSend.setOnAction(action -> {
            if (textEnter.getText().isEmpty()){
                return;
            }
            String content = textEnter.getText();
            output.println(content);
            textEnter.setText("");
        });
        HBox bottom = new HBox(textEnter, btnSend);
        VBox main = new VBox(vbox, listMessage, bottom);

        Scene scene = new Scene(main, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chat Client");
        primaryStage.show();


        try {
            Socket socket = new Socket(serverAddress, 1997);
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);

            while (input.hasNextLine()){
                String line = input.nextLine();
                if (line.startsWith("ENTER YOUR NAME")){
                    output.println(textName.getText());
                }else if (line.startsWith("NAME ACCEPTED")){
                    btnSend.setDisable(false);
                }else if (line.startsWith("Message_")){
                    listMessage.appendText(line);
                }


            }
        }finally {

        }
    }
}
