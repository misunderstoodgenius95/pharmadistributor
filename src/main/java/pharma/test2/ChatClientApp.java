package pharma.test2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ChatClientApp extends Application {

    private TextArea chatArea;
    private TextField messageField;
    private Button sendButton;
    private ChatClientConnection connection;
    private  String jwt;
    private String email_identify;
    private String title;
    private  BorderPane root;
    private  ProgressIndicator pindicator;
    private VBox vbox_wait;
    private  HBox inputBox;
    public ChatClientApp(String jwt,String title) {
        this.jwt=jwt;
        this.title=title;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(title);
        String wait="";
        if(title.equals("seller")){
            wait="pharmacist";
        }else{
            wait="seller";

        }

         root = new BorderPane();
        pindicator=new ProgressIndicator();
      //root.setLeft(pindicator);

      vbox_wait=new VBox(1);
      vbox_wait.getChildren().addAll(new Text("Waiting for a"+wait),pindicator);




        // ChatPharmacist display area
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        chatArea.setPrefHeight(600);
        chatArea.setPrefWidth(500);

        root.setCenter(chatArea);
        ObjectProperty<ChatMsg> chatMsgObjectProperty=new SimpleObjectProperty<>(null);
        chatMsgObjectProperty.addListener(message_receiver());

        // Message input area
        HBox inputBox = new HBox(10);
        inputBox.setPadding(new Insets(10, 0, 0, 0));
        messageField = new TextField();
        messageField.setPromptText("Enter message...");
        sendButton = new Button("Send");
        inputBox.getChildren().addAll(messageField, sendButton);
      //  gridPane.add(inputBox,0,90);
        root.setBottom(inputBox);

        // Event handlers
        sendButton.setOnAction(e -> sendMessage());
        //messageField.setOnAction(e -> sendMessage());

        // Start the network connection
     //   connection = new ChatClientConnection("localhost", 3000,jwt,this::onMessageReceived);
        connection=new ChatClientConnection("localhost",3000,jwt,chatMsgObjectProperty);
        new Thread(connection::connect).start();
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public ChangeListener<ChatMsg> message_receiver() {
        return ((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if(newValue!=null) {
                    switch (newValue.getCommand()) {
                        case Command.WAIT -> {
                            root.setBottom(vbox_wait);
                            System.out.println("wait");
                            pindicator.setVisible(true);
                            System.out.println(newValue.getPayload());
                            chatArea.appendText(newValue.getPayload());


                        }


                        case JOIN_SUCCESS -> {
                            email_identify = newValue.getReceiver();

                        }
                        case Command.CHATTING -> {
                            chatArea.appendText(newValue.getSender() + ":" + newValue.getPayload());
                        }


                    }
                }
            });


        });
    }




    private void onMessageReceived(ChatMsg msg) {
        // ALL GUI updates must happen on the JavaFX Application Thread.
        // Platform.runLater ensures this.
        Platform.runLater(() -> {
            switch (msg.getCommand()){
                case Command.WAIT -> {
                    root.setBottom(vbox_wait);
                    System.out.println("wait");
                    pindicator.setVisible(true);


                }

            }


            if(msg.getCommand().equals(Command.JOIN_SUCCESS)){
                    email_identify=msg.getReceiver();

            }
            chatArea.appendText(msg.getSender()+":"+msg.getPayload());


        });
    }

    private void sendMessage() {
        String text = messageField.getText().trim();
        if (!text.isEmpty()) {
            ChatMsg msg = new ChatMsg(jwt, text, Command.CHATTING,email_identify,"SYSTEM");
            connection.sendMessage(msg);

            // Add our own message to the chat area immediately
            chatArea.appendText(email_identify+":"+msg.getPayload());

            messageField.clear();
        }
    }

    @Override
    public void stop() {
        // This is called when the application is closed.
        // Ensure we disconnect cleanly.
        if (connection != null) {
            connection.disconnect();
        }
    }
}
