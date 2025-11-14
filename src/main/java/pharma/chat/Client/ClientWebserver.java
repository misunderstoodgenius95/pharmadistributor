package pharma.chat.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientWebserver extends WebSocketClient {
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
        System.out.println("Connnected to chat server");
        System.out.println(serverHandshake.getHttpStatusMessage());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userName",username);
        jsonObject.put("userType","seller");
        jsonObject.put("messageType","Starting");

        this.send(jsonObject.toString());
        sendMessage();


    }
    public void sendMessage()  {
        if(isOpen()){
            System.out.println("send");
        }

    }


    @Override
    public void onMessage(String s) {
         System.out.println(s);
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

