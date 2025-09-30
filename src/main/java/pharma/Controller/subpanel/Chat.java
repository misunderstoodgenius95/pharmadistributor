package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.chat.Client.ClientWebserver;
import pharma.javafxlib.Controls.Notification.JsonNotify;
import pharma.security.crypto.Chacha20;

import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Chat implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(Chat.class);
    public TextField text_field_id;
    public TextArea textarea_id;
    ClientWebserver clientWebserver;

    public Chat() throws InterruptedException {



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConcurrentLinkedQueue<String> concurrentLinkedQueue=new ConcurrentLinkedQueue<>();
        clientWebserver=new ClientWebserver(URI.create("ws://localhost:8081"),"seller1@example.com",concurrentLinkedQueue);
        clientWebserver.connect();

        startQueueProcessor(concurrentLinkedQueue);

    }



    public static String extracted_message(String message){
        log.info("Extracted: "+message);
        JSONObject jsonObject=new JSONObject(message);
        String type=jsonObject.getString("type");
          if(type.equals("Chat")){
              String from=jsonObject.getString("from");
          JSONObject message_cipher=new JSONObject(jsonObject.getString("messages"));
          String nonce=message_cipher.getString("nonce");
          String cipherText=message_cipher.getString("cipherText");
          String decypted_message=Chacha20.decryptedString(Chacha20.decrypt(Chacha20.hexToByte(cipherText),Chacha20.hexToByte(nonce)).get());

            return from+" "+decypted_message;
        }else if(type.equals("Join")){
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
        textarea_id.appendText("Me: "+text_field_id.getText()+"\n");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("from","seller1@example.com");
        jsonObject.put("to","pharmacist@example.com");
        jsonObject.put("type","Chat");

        byte[] nonce=Chacha20.generateNonce();
        byte[] plaintext=text_field_id.getText().getBytes(StandardCharsets.UTF_8);
        byte[] cypherTex=Chacha20.encrypt(plaintext,nonce).get();
        String cipherText_hex=Chacha20.byteToHex(cypherTex);
        JSONObject jsonObject_message=new JSONObject();
        jsonObject_message.put("cipherText",cipherText_hex);
        jsonObject_message.put("nonce",Chacha20.byteToHex(nonce));
        jsonObject.put("Message",jsonObject_message);

        clientWebserver.send(jsonObject.toString());

    }

    public void btn_disconnect_action(ActionEvent event) {
    }
}
