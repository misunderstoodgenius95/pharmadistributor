package pharma.Controller;


import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pharma.DialogController.Report.PiccoDialog;
import pharma.DialogController.Report.SpeseAcquisti;
import pharma.DialogController.Report.Trend;
import pharma.DialogController.Report.Variazione;
import pharma.Model.Acquisto;
import pharma.Model.Ordini;
import pharma.Stages;
import pharma.Storage.FileStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pharma.config.PathConfig;
import pharma.config.database.Database;
import pharma.dao.FarmacoDao;
import pharma.dao.PurchaseOrderDao;
import pharma.dao.PurchaseOrderDetailDao;
import pharma.javafxlib.DropDownMenu;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Purchase  implements  Initializable{

    public Button report_id;
    private Stages stages;
    public VBox vbox_id;
    @FXML
    private AnchorPane anchor_id;
    private SimpleObjectProperty<Pane> simpleObjectProperty;
    private Button last_clicked;
    private PurchaseOrderDetailDao p_detail_dao;
    private FarmacoDao farmacoDao;
    private PurchaseOrderDao purchaseOrderDao;
    public Purchase() {
        stages = new Stages();
        simpleObjectProperty = new SimpleObjectProperty<>();
        last_clicked = null;
        Properties properties=null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader(PathConfig.DATABASE_CONF.getValue()));

            p_detail_dao=new PurchaseOrderDetailDao(Database.getInstance(properties));
            farmacoDao=new FarmacoDao(Database.getInstance(properties));
            purchaseOrderDao=new PurchaseOrderDao(Database.getInstance(properties));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void change_stages(Parent parent, double value) throws IOException {
        anchor_id.getChildren().removeIf(node -> node.getStyleClass().contains("subpanel"));
        AnchorPane.setRightAnchor(parent, value);
        anchor_id.getChildren().add(parent);
    }

    @FXML
    void dettagli_action(ActionEvent event) throws IOException {
        Parent parent = stages.load_fxml("/subpanel/dettagli.fxml");
        parent.getStyleClass().add("subpanel");
        change_stages(parent, 200.00);
        Button button = (Button) event.getSource();
        handleButton(button);

    }

    @FXML
    void farmaci_action(ActionEvent event) throws IOException {
        Parent parent = stages.load_fxml("/subpanel/farmaco.fxml");
        parent.getStyleClass().add("subpanel");
        change_stages(parent, 50.00);
        Button button = (Button) event.getSource();
        handleButton(button);
    }

    @FXML
    void fattura_acquisto_action(ActionEvent event) throws IOException {
        Parent parent = stages.load_fxml("/subpanel/purchase_invoice.fxml");
        parent.getStyleClass().add("subpanel");
        change_stages(parent, -20.00);
        Button button = (Button) event.getSource();
        handleButton(button);
    }

    @FXML
    void lotti_action(ActionEvent event) throws IOException {
        Parent parent = stages.load_fxml("/subpanel/lotti.fxml");
        parent.getStyleClass().add("subpanel");
        change_stages(parent, -20.00);
        Button button = (Button) event.getSource();
        handleButton(button);

    }

    @FXML
    void ordini_action(ActionEvent event) throws IOException {
        Parent parent = stages.load_fxml("/subpanel/ordini.fxml");
        parent.getStyleClass().add("subpanel");
        change_stages(parent, -20.00);
        Button button = (Button) event.getSource();
        handleButton(button);
    }

    @FXML
    void pharma_action(ActionEvent event) throws IOException {

        Parent parent = stages.load_fxml("/subpanel/pharma.fxml");
        parent.getStyleClass().add("subpanel");
        change_stages(parent, -20.00);
        Button button = (Button) event.getSource();
        handleButton(button);

    }

    @FXML
    void raccomandazioni_action(ActionEvent event) throws IOException {
        Parent parent = stages.load_fxml("/subpanel/suggest_purchase.fxml");
        parent.getStyleClass().add("subpanel");
        change_stages(parent, -20.00);
    }

    public void handleButton(Button button) {

        if (last_clicked != null) {
            last_clicked.getStyleClass().remove("clicked");
        }

        last_clicked = button;
        last_clicked.getStyleClass().add("clicked");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DropDownMenu dropDownMenu=new DropDownMenu(report_id);

       List<Acquisto> acquistos=p_detail_dao.findAllReportData().stream().map(item->new Acquisto(item.getId(),item.getQuantity(),item.getElapsed_date(),item.getPrice())).toList();
        List<Ordini> ordiniList=purchaseOrderDao.findAll().stream().map(item->new Ordini(item.getOrder_id(),item.getTotal(),item.getProduction_date())).toList();
        SpeseAcquisti acquisti=new SpeseAcquisti(ordiniList);
        Variazione variazione=new Variazione("Visualizza variazione",ordiniList);
        PiccoDialog piccoDialog =new PiccoDialog("Visualizza Picco",acquistos,farmacoDao);
        Trend trend=new Trend("Visualizza Trend",acquistos);
        dropDownMenu.createItem("Andamento Acquisti Per Mesi ").setOnAction(event -> {
        acquisti.show();
        });
dropDownMenu.createItem("Variazione Acquisti Tra Mesi").setOnAction(event -> {
    variazione.show();
});
       dropDownMenu.createItem("Trend").setOnAction(event -> {
            trend.show();
        });
      dropDownMenu.createItem("Picchi").setOnAction(event -> piccoDialog.show());
    }

    public void report_action(ActionEvent event) {
    }

/*
    @FXML
    void add_casa_farmaceutica_btn(ActionEvent event) {
        String token = StorageToken.get_token();
        boolean token_u = TokenUtility.check_permission(token, "write", "pharma");

        if (token_u) {
            System.out.println("Accedi alla proceura");
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Permission Denied");
            alert.show();
        }

 *


    }

 */




}

