package pharma.Controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.controlsfx.control.NotificationPane;
import org.json.JSONObject;
import pharma.Storage.FileStorage;
import pharma.config.net.ClientHttp;
import pharma.config.net.PollingClient;
import pharma.javafxlib.Controls.NotificationPanelLib;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledFuture;

public class Seller implements Initializable {
    public Text text_notify;
    public AnchorPane anchor_id;
    public StackPane stack_id;
    private ClientHttp clientHttp;
        private  PollingClient pollingClient;
    public Seller() {
       clientHttp=new ClientHttp();
       pollingClient=new PollingClient(clientHttp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
/*        String uri="";
        try {
            uri= FileStorage.getProperty("expire_post",new FileReader("expire_item.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

       // ScheduledFuture<String>result_json=pollingClient.send(uri);*/
        NotificationPanelLib notificationPanelLib=new NotificationPanelLib(text_notify);
        stack_id.getChildren().add( notificationPanelLib.getPane());


        notificationPanelLib.show("LOg me!");



    }
}
