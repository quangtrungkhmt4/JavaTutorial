package sample.client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class FirstClient extends Application {

    private String serverAddress;
    private Scanner input;
    private PrintWriter output;

    public void init(){
        this.serverAddress = "localhost";
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
