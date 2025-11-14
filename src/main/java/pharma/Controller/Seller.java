package pharma.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.NotificationPane;
import org.jetbrains.annotations.TestOnly;
import org.json.JSONObject;
import pharma.Controller.subpanel.SellerInvoice;
import pharma.Handler.EXpireProductNotifyHandler;
import pharma.Handler.PharmacistHandlerCreate;
import pharma.Handler.SellerOrderHandler;
import pharma.Stages;
import pharma.Storage.FileStorage;
import pharma.config.auth.UserService;
import pharma.config.database.Database;
import pharma.config.net.ClientHttp;
import pharma.config.net.PollingClient;

import pharma.dao.*;
import pharma.formula.KMeans;
import pharma.formula.PriceSuggestion;
import pharma.javafxlib.Controls.Notification.JsonNotifyLottoDao;
import pharma.javafxlib.Controls.NotificationPanelLib;
import pharma.security.Stytch.StytchClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

public class Seller implements Initializable {
    public Text text_notify;
    public AnchorPane anchor_id;
    public StackPane stack_id;
    @FXML
    public Button pharmacist_id;
    public Button farmacia_id;
    public AnchorPane body_seller;
    private ClientHttp clientHttp;
    private  PollingClient pollingClient;
    private FarmaciaDao farmaciaDao;
    private FarmacoDao farmacoDao;
    private LottiDao lottiDao;
    private LotAssigmentDao assigmentDao;
    private UserService userService;
    private SellerOrderDao s_dao;
    private SellerOrderDetails s_detail;
    private SellerInvoiceDao s_invoice;
    private SellerCreditNoteDao s_credit;
    private SellerCreditNoteDetailDao s_credit_detail;
    private SellerInvoice sellerInvoice;
    private SellerInvoiceDao s_invoice_dao;
    Stages stages;
    private PharmacistHandlerCreate pharmacistHandlerCreate;
    private EXpireProductNotifyHandler notify;
    private SellerOrderHandler sellerOrderHandler;
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
         farmacoDao=new FarmacoDao(Database.getInstance(properties));
         lottiDao=new LottiDao(Database.getInstance(properties),"lotto");
         assigmentDao=new LotAssigmentDao(Database.getInstance(properties));
        s_dao = new SellerOrderDao(Database.getInstance(properties));
        s_detail = new SellerOrderDetails(Database.getInstance(properties));
        s_invoice = new SellerInvoiceDao(Database.getInstance(properties));
        s_credit_detail = new SellerCreditNoteDetailDao(Database.getInstance(properties));
         notify=new EXpireProductNotifyHandler("Imposta Notifica",assigmentDao);
        sellerOrderHandler=new SellerOrderHandler("Modifica Ordine", s_dao, s_detail, s_invoice, s_credit, s_credit_detail);
        s_invoice_dao=new SellerInvoiceDao(Database.getInstance(properties));
        sellerInvoice=new SellerInvoice(s_invoice_dao);



    }


    private Window getWindow() {
        return farmacia_id.getScene().getWindow();

    }


        @TestOnly
    public Seller(FarmaciaDao farmaciaDao, UserService userService) {
        this.farmaciaDao = farmaciaDao;
        this.userService = userService;
        pharmacistHandlerCreate=new PharmacistHandlerCreate(farmaciaDao,userService);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


                pollingClient = new PollingClient(clientHttp);
                ScheduledFuture<String> scheduledFuture = pollingClient.send("http://localhost:3001/notify");
                String response;
                try {
                    response = scheduledFuture.get();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
                if (!response.isEmpty()) {
                    System.out.println(response);

                    JsonNotifyLottoDao notifyLottoDao = new JsonNotifyLottoDao(response, List.of("lot_id", "expiration_date"), "Scadenza", "Avviso Scadenze ", lottiDao);
                    notifyLottoDao.execute();
                }
 /*       try {
            JsonNotifyLottoDao notifyLottoDao=new JsonNotifyLottoDao(response.get(),List.of("lot_id","expiration_date"),"Scadenza","Avviso Scadenze ",lottiDao);
            notifyLottoDao.execute();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }*/
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
        body_seller.getChildren().clear();
     body_seller.getChildren().add(parent);



    }

    public void farmacia_action(ActionEvent event) throws IOException {
        Parent parent = stages.load_fxml("/subpanel/farmacia.fxml");
        change_stages(parent,50.0);
    }

    public void btnPromotion(ActionEvent event) throws IOException {
        Parent parent = stages.load_fxml("/subpanel/promo.fxml");
        change_stages(parent,50.0);
    }
    @FXML
    public void expire_action(ActionEvent event) {
        notify.execute();
    }

    public void order_action(ActionEvent event) {
    sellerOrderHandler.execute();

    }

    public void invoice_action(ActionEvent event) throws IOException {
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/subpanel/sellerInvoice.fxml"));
        loader.setController(sellerInvoice);
        change_stages(loader.load(),50.0);

    }

    public void btn_chat_action(ActionEvent event) throws IOException {

        System.out.println("action");
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/subpanel/chat.fxml"));
        change_stages(loader.load(),50.0);

    }
}
