package pharma.chat.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientWebserverTest {

    @Test
    public void test() throws InterruptedException {
        ObservableList<String> senders= FXCollections.observableArrayList();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("from","seller1@example.com");
        jsonObject.put("to","pharmacist@example.com");
        jsonObject.put("type","Chat");
        jsonObject.put("Message","Ciao");

       /* ClientWebserver clientWebserver=new ClientWebserver(URI.create("ws://localhost:8081"),"seller1@example.com",senders );
        clientWebserver.connectBlocking();*/







    }


}