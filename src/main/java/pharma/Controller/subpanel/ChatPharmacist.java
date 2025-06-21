package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pharma.Handler.ChatHandler;
import pharma.test2.ChatClientConnection;
import pharma.test2.ChatMsg;
import pharma.test2.Command;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class ChatPharmacist implements Initializable {
    @FXML
    public VBox aside_container_user;
    @FXML
    public VBox aside_user;

    @FXML
    public VBox wait_panel_id;

    @FXML
    public HBox hbox_header_text;
    @FXML
    public TextField text_field_id;
    @FXML
    public Button btn_id;
    @FXML
    public Button btn_disconnect_id;

    @FXML
    TextArea textarea_id;
    private  ChatHandler chatHandler;
    private ChatClientConnection chatClientConnection;
    private  ObjectProperty<ChatMsg> objectProperty;
    private ExecutorService  executorService;
    private String email_identify;
    private final String jwt;
    private String receiver_seller;
    private Logger logger=Logger.getLogger(ChatPharmacist.class.getName());
    public ChatPharmacist(ChatHandler chatHandler, String jwt) {
       this.chatHandler=chatHandler;
       this.objectProperty=new SimpleObjectProperty<>();
       objectProperty.addListener(message_receiver());
       this.jwt=jwt;


       this.chatClientConnection=new ChatClientConnection("localhost",3000,jwt,objectProperty);



       this.executorService= Executors.newSingleThreadExecutor();
       executorService.execute(()-> chatClientConnection.connect());


    }



    @FXML
    public void btn_action(ActionEvent actionEvent) {

        String msg= text_field_id.getText();
        if(!msg.isEmpty()){
            ChatMsg chatMsg=new ChatMsg(jwt,msg,Command.CHATTING,email_identify,"");
            chatClientConnection.sendMessage(chatMsg);
            text_field_id.clear();
            logger.info(msg);
            textarea_id.appendText(email_identify+":"+msg+"\n");
            logger.info("e_identify: "+email_identify);

        }


    }

    public ChangeListener<ChatMsg> message_receiver() {
        return ((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if(newValue!=null) {
                    switch (newValue.getCommand()) {
                        case Command.WAIT -> {
                            hbox_header_text.setVisible(false);
                            wait_panel_id.setVisible(true);
                            textarea_id.appendText("System:"+newValue.getPayload() + "\n");
                            email_identify=newValue.getReceiver();
                            logger.info("email_id: "+email_identify);
                        }
                        case JOIN_SUCCESS -> {
                            hbox_header_text.setVisible(true);
                            wait_panel_id.setVisible(false);
                            email_identify=newValue.getReceiver();
                            textarea_id.appendText("SYSTEM: "+newValue.getPayload()+"\n");


                        }
                        case Command.CHATTING -> {
                            textarea_id.appendText(newValue.getSender() + ":" + newValue.getPayload()+"\n");
                        }


                    }
                }
            });


        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aside_container_user.setVisible(false);
/*

        String role=chatHandler.get_role();
        aside_container_user.setVisible(role.equals("seller"));
*/




    }

    @FXML
    public void btn_disconnect_action(ActionEvent actionEvent) {
        if (chatClientConnection != null) {
            chatClientConnection.disconnect();
        }

    }
}

