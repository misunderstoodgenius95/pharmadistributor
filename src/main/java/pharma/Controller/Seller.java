package pharma.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import org.jetbrains.annotations.TestOnly;
import org.json.JSONObject;
import pharma.Handler.PharmacistHandlerCreate;
import pharma.Stages;
import pharma.Storage.FileStorage;
import pharma.config.auth.UserService;
import pharma.config.database.Database;
import pharma.config.net.ClientHttp;
import pharma.config.net.PollingClient;
import pharma.dao.FarmaciaDao;
import pharma.formula.KMeans;
import pharma.formula.PriceSuggestion;
import pharma.javafxlib.Controls.NotificationPanelLib;
import pharma.security.Stytch.StytchClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

public class Seller implements Initializable {
    public Text text_notify;
    public AnchorPane anchor_id;
    public StackPane stack_id;
    @FXML
    public Button pharmacist_id;
    public Button farmacia_id;
    private ClientHttp clientHttp;
    private  PollingClient pollingClient;
    private FarmaciaDao farmaciaDao;
    private UserService userService;
    Stages stages;
    private PharmacistHandlerCreate pharmacistHandlerCreate;

    public Seller() {
        stages=new Stages();
       clientHttp=new ClientHttp();
       Properties properties;
        try {
           properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, String> hashMap_json =null;
        try {
            hashMap_json = FileStorage.getProperties(List.of("project_id", "secret", "url"), new FileReader("stytch.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        pollingClient=new PollingClient(clientHttp);
       farmaciaDao=new FarmaciaDao(Database.getInstance(properties));
        userService = new UserService(new StytchClient(hashMap_json.get("project_id"), hashMap_json.get("secret"), hashMap_json.get("url")));
         pharmacistHandlerCreate=new PharmacistHandlerCreate(farmaciaDao,userService);


    }





        @TestOnly
    public Seller(FarmaciaDao farmaciaDao, UserService userService) {
        this.farmaciaDao = farmaciaDao;
        this.userService = userService;
        pharmacistHandlerCreate=new PharmacistHandlerCreate(farmaciaDao,userService);
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


        notificationPanelLib.show("Log me!");



    }


    @FXML
    public void pharmacist_action(ActionEvent actionEvent) {
        pharmacistHandlerCreate.executeStatus();

    }

    public void btn_action_price(ActionEvent event) throws IOException {
        Parent parent = stages.load_fxml("/subpanel/price.fxml");
        change_stages(parent,50.0);
    }









    private void change_stages(Parent parent, double value) throws IOException {
        anchor_id.getChildren().removeIf(node -> node.getStyleClass().contains("subpanel"));
        AnchorPane.setRightAnchor(parent, value);
        anchor_id.getChildren().add(parent);

    }

    public void farmacia_action(ActionEvent event) throws IOException {
        Parent parent = stages.load_fxml("/subpanel/farmacia.fxml");
        change_stages(parent,50.0);
    }
}
