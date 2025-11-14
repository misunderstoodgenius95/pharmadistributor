package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.Storage.FileStorage;
import pharma.chat.Client.ClientWebserver;
import pharma.config.auth.UserService;
import pharma.javafxlib.Controls.Notification.JsonNotify;
import pharma.security.Stytch.StytchClient;
import pharma.security.TokenUtility;
import pharma.security.crypto.Chacha20;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Chat implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(Chat.class);
    public TextField text_field_id;
    public TextArea textarea_id;
    @FXML
    public Button btn_send_id;
    @FXML
    public Button btn_disconnect_id;
    public VBox aside_user;
    private ClientWebserver clientWebserver;
    private UserService userService;
    private List<String> pharmacists;
    private String email;
    public Chat() {
        pharmacists=new ArrayList<>();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HashMap<String,String> hashMap_json= null;
        String user_id="";
        try {
            hashMap_json = FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader("stytch.properties"));
            String jwt= FileStorage.getProperty("jwt",new FileReader("config.properties"));
            user_id=TokenUtility.extract_sub(jwt);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        userService=new UserService(new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url")));

        String body=userService.GettingUserInfoByUserId(user_id).getBody();
         email=TokenUtility.extractEmailByUserId(body);

        ConcurrentLinkedQueue<String> concurrentLinkedQueue=new ConcurrentLinkedQueue<>();
        clientWebserver=new ClientWebserver(URI.create("ws://localhost:8081"),email,concurrentLinkedQueue);
        clientWebserver.connect();

        startQueueProcessor(concurrentLinkedQueue);

    }



    public  String extracted_message(String message){
        System.out.println("Extracted: "+message);
        JSONObject jsonObject=new JSONObject(message);
        String type=jsonObject.getString("type");
          if(type.equals("Chat")){
                String from=jsonObject.getString("from");
                JSONObject message_cipher=new JSONObject(jsonObject.getString("messages"));
                String nonce=message_cipher.getString("nonce");
                String cipherText=message_cipher.getString("cipherText");
                String decryptedMessage =Chacha20.decryptedString(Chacha20.decrypt(Chacha20.hexToByte(cipherText),Chacha20.hexToByte(nonce)).get());
            return from+" "+decryptedMessage;
        }else if(type.equals("Join")){
              btn_send_id.setDisable(false);
              btn_disconnect_id.setDisable(false);
              String from=jsonObject.getString("from");
              if(!pharmacists.contains(from)) {
                  pharmacists.add(from);
                  aside_user.getChildren().add(new Button(from));
              }





              return  jsonObject.getString("message");




          }
          return "";
    }


    private void startQueueProcessor(ConcurrentLinkedQueue<String> queue) {
        Thread processorThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String message = queue.poll();

                    if (message != null) {
                        System.out.println("Received: " + message);

                        // Update UI on JavaFX thread
                        Platform.runLater(() -> {
                            String extracted=extracted_message(message);
                            System.out.println(extracted);
                            textarea_id.appendText(extracted+" \n ");


                            //textarea_id.appendText(message + "\n");
                        });
                    }

                    // Small delay to avoid CPU spinning
                    Thread.sleep(50);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    System.err.println("Error processing queue: " + e.getMessage());
                }
            }
        });

        processorThread.setDaemon(true);
        processorThread.setName("QueueProcessor");
        processorThread.start();
    }


    public void btn_action(ActionEvent event) {
        String messageText = text_field_id.getText();
        if (messageText == null || messageText.trim().isEmpty()) {
            return;
        }

        textarea_id.appendText("Me: " + messageText + "\n");

        // Encrypt message
        byte[] nonce = Chacha20.generateNonce();
        byte[] plaintext = messageText.getBytes(StandardCharsets.UTF_8);
        byte[] cipherText = Chacha20.encrypt(plaintext, nonce).get();
        String cipherTextHex = Chacha20.byteToHex(cipherText);

        // Create encrypted message object
        JSONObject encryptedMessage = new JSONObject();
        encryptedMessage.put("cipherText", cipherTextHex);
        encryptedMessage.put("nonce", Chacha20.byteToHex(nonce));

        // Create main message object
        JSONObject message = new JSONObject();
        message.put("from",email);  // Should use actual email from session
        message.put("to", pharmacists.getFirst());  // Should be dynamic based on selected user
        message.put("type", "Chat");
        message.put("message", encryptedMessage);  // Match what JavaScript expects

        // Send message
        if (clientWebserver.isOpen()) {
            clientWebserver.send(message.toString());
            System.out.println("Message sent: " + message.toString());
            text_field_id.clear();
        } else {
            System.err.println("WebSocket is not open!");
            textarea_id.appendText("ERROR: Not connected to server\n");
        }

    }

    public void btn_disconnect_action(ActionEvent event) {

        clientWebserver.close();

    }
}
