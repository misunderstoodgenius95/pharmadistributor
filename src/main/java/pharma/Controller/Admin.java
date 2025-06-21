package pharma.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Admin  implements Initializable {
    public VBox vbox_filter_choice;
    public ChoiceBox<String> choice_user_id;
    public TextField field_id_search;
    public Button btn_send_filter_id;

    public Admin() {

    }

    public void btn_send_filter_action(ActionEvent actionEvent) {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choice_user_id.getItems().addAll("purchase","seller","warehouser","pharmacist");


    }
}
