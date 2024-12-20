package pharma.Controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pharma.RolesStage;
import pharma.Stages;
import pharma.Storage.StorageToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import pharma.security.TokenUtility;

import java.io.IOException;

public class Purchase{


    public VBox vbox_id;
    @FXML
    private AnchorPane anchor_id;

    @FXML
    void dettagli_action(ActionEvent event) {

    }

    @FXML
    void farmaci_action(ActionEvent event) {

    }

    @FXML
    void fattura_acquisto_action(ActionEvent event) {

    }

    @FXML
    void lotti_action(ActionEvent event) {

    }

    @FXML
    void ordini_action(ActionEvent event) {

    }

    @FXML
    void pharma_action(ActionEvent event) throws IOException {
vbox_id.setVisible(false);
 Stages stage = new Stages();
        Parent parent=stage.load_fxml("/subpanel/pharma.fxml");
        System.out.println(parent.getLayoutX());
     AnchorPane.setTopAnchor(parent, 0.0);
        anchor_id.getChildren().add(parent);
    }

    @FXML
    void raccomandazioni_action(ActionEvent event) {

    }
    @FXML
    void add_casa_farmaceutica_btn(ActionEvent event) {
String token=StorageToken.get_token();
        boolean token_u=TokenUtility.check_permission(token,"write","pharma");

if(token_u){
    System.out.println("Accedi alla proceura");;
}else{

    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Permission Denied");
    alert.show();
}





    }


}

