package pharma.Controller;


import com.github.curiousoddman.rgxgen.iterators.IncrementalLengthIterator;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pharma.RolesStage;
import pharma.Stages;
import pharma.Storage.FileStorage;
import pharma.Storage.StorageToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import pharma.config.auth.AutorizationService;
import pharma.config.auth.UserService;
import pharma.javafxlib.DropDownMenu;
import pharma.security.Stytch.StytchClient;
import pharma.security.TokenUtility;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class Purchase  implements  Initializable{

    public Button report_id;
    private Stages stages;
    public VBox vbox_id;
    @FXML
    private AnchorPane anchor_id;
    private SimpleObjectProperty<Pane> simpleObjectProperty;
    private Button last_clicked;


    public Purchase() {
        stages = new Stages();
        simpleObjectProperty = new SimpleObjectProperty<>();
        last_clicked = null;
        HashMap<String,String> hashMap_json=null;
        try {
            hashMap_json = FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader("stytch.properties"));

        } catch (FileNotFoundException e) {
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
        dropDownMenu.createItem("Andamento Acquisti Per Mesi ");
        dropDownMenu.createItem("Variazione Acquisti Tra Mesi");
        dropDownMenu.createItem("Trend");
        dropDownMenu.createItem("Picchi");
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

