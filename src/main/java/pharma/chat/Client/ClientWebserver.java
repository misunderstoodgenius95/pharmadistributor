package pharma.chat.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientWebserver extends WebSocketClient {
    private static final Logger log = LoggerFactory.getLogger(ClientWebserver.class);
    private String username;
    private BlockingQueue<JSONObject> outgoing;
    private ObservableSet<String> listener;
    private ConcurrentLinkedQueue<String> concurrentLinkedQueue;
    private ObservableList<String> outgoing_msg;
    private Set<String> pharmacists;
    public ClientWebserver(URI serverUri, String username, ConcurrentLinkedQueue<String> linkedBlockingQueue) {
        super(serverUri);
        this.username=username;
        outgoing=new LinkedBlockingQueue<>();
        listener= FXCollections.observableSet();

        pharmacists=new HashSet<>();
        this.concurrentLinkedQueue=linkedBlockingQueue;

    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
       log.info("connesso al server");
       log.info("status "+serverHandshake.getHttpStatusMessage());
        System.out.println("status"+serverHandshake.getHttpStatusMessage());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userName",username);
        jsonObject.put("userType","seller");
        jsonObject.put("messageType","Starting");
        this.send(jsonObject.toString());
        log.info("Send Message"+jsonObject);
        sendMessage();


    }
    public void sendMessage()  {
        if(isOpen()){
            System.out.println("send");
        }

    }


    @Override
    public void onMessage(String s) {
            log.info("On Message"+ s);
         JSONObject jsonObject=new JSONObject(s);
        concurrentLinkedQueue.offer(s);
         if(jsonObject.get("type").equals("Join")){
             String pharmacist=jsonObject.getString("Pharmacist");
             pharmacists.add(pharmacist);
             System.out.println("Added Pharmacist");

         }
    }


    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}

