package pharma.Handler;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.test2.ActiveClient;
import pharma.test2.ThreadServerManager;

@ExtendWith(ApplicationExtension.class)

class ChatHandlerTest {
    @Mock
    private ThreadServerManager ts;
    private VBox vBox;
    private ChatHandler chatHandler;
    private TextArea textArea;
    private StringProperty current_pharmacist;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        ActiveClient.setPharmacist_map("farmacist1@example.com", ts);
        ActiveClient.setPharmacist_map("farmacist2@examample.com", ts);


    }

    @Start
    public void start(Stage stage){
        BorderPane borderPane=new BorderPane();
        textArea=new TextArea();
        borderPane.setPrefHeight(800);
        borderPane.setPrefWidth(900);
        stage.initStyle(StageStyle.DECORATED);
         vBox=new VBox(20);
        vBox.setPrefWidth(300);
        vBox.setPrefHeight(200);
        borderPane.setRight(vBox);
        HBox inputBox = new HBox(10);
        inputBox.setPadding(new Insets(10, 0, 0, 0));
         TextField messageField = new TextField();
         messageField.setPrefWidth(300);
current_pharmacist =new SimpleStringProperty(null);
        messageField.setPromptText("Enter message...");
         Button sendButton = new Button("Send");
         sendButton.setPrefWidth(100);
        inputBox.getChildren().addAll(messageField, sendButton);
        borderPane.setBottom(inputBox);
        vBox.setStyle("-fx-border-color: #9ea8a2; -fx-border-width:2px");
        chatHandler = new ChatHandler(vBox,textArea);
        borderPane.setCenter(textArea);
        chatHandler.getProperty_btn_active().addListener(((observable, oldValue, newValue) -> {
            current_pharmacist.setValue(oldValue.getText());

        }));

        Scene scene=new Scene(borderPane,1000,1000);
        stage.setScene(scene);
        stage.show();


    }



        @Test
        public void ValidAddPharmacist(FxRobot robot) {
            Platform.runLater(() -> {

                Assertions.assertTrue(chatHandler.getCurrent_chat().containsKey("farmacist1@example.com"));


            });
        }

            @Test
            public void ValidAddButton(FxRobot robot){

            Platform.runLater(()->{
                chatHandler.add_chat_msg("farmacist1@example.com", "Ciao");
                Assertions.assertEquals(1,vBox.getChildren().size());
            });
            robot.sleep(1000000);



            }
    @Test
    public void ValidateTwoButton(FxRobot robot) {

        Platform.runLater(() -> {
            chatHandler.add_chat_msg("farmacist1@example.com", "Ciao");
            chatHandler.add_chat_msg("farmacist2@examample.com", "Hi!");
            Assertions.assertEquals(2, vBox.getChildren().size());

        });
    }
        @Test
        public void SimulateClicFirstElement(FxRobot robot){
        Platform.runLater(()->{

            chatHandler.add_chat_msg("farmacist1@example.com", "Ciao\n");
            chatHandler.add_chat_msg("farmacist1@example.com", "Come Stai?\n");
            chatHandler.add_chat_msg("farmacist2@examample.com", "Hi!");






        });
        robot.sleep(1000000);




        }















    }
