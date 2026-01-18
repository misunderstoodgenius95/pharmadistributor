package pharma.Controller.subpanel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import pharma.DialogController.AdminCreateCredentialsControllerBase;
import pharma.DialogController.Table.AdminTable;
import pharma.Model.User;
import pharma.Storage.FileStorage;
import pharma.config.InputValidation;
import pharma.config.PathConfig;

import pharma.config.Utility;
import pharma.config.auth.UserGateway;
import pharma.security.Stytch.StytchClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AdminRole implements Initializable {
    @FXML
   private VBox vbox_filter_choice;
    @FXML
    private Button btn_send_filter_id;
    @FXML
    public ChoiceBox<String> choice_role_id;
    public TextField textfield_emai_id;

    @FXML
    private RadioButton radio_email;
    @FXML
    private RadioButton radio_role;
    @FXML
    private Button btn_add_id;
    private ObjectProperty<User> userObjectProperty;
    private UserGateway userGateway;
    private AdminTable adminTable;
    private BooleanProperty choice_booleanProperty;
    private ToggleGroup toggleGroup_radio;
    private BooleanProperty property_esit;
    private AdminCreateCredentialsControllerBase c_handler;
    public AdminRole() {
        HashMap<String, String> hashMap_json =null;
        try {
            hashMap_json = FileStorage.getProperties(List.of("project_id", "secret", "url"), new FileReader(PathConfig.STYTCH_CONF.getValue()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        userGateway = new UserGateway(new StytchClient(hashMap_json.get("project_id"), hashMap_json.get("secret"), hashMap_json.get("url")));
        adminTable=new AdminTable("Visualizza Risultati", userGateway);


    }
    // Only Test Only
    public AdminRole(UserGateway userGateway){
        this.userGateway = userGateway;
        adminTable = new AdminTable("Visualizza Risultati", userGateway);
    }

    public Button getBtn_add_id() {
        return btn_add_id;
    }
    @FXML
    public void btn_send_filter_action(ActionEvent actionEvent) {
        boolean email_condition = !textfield_emai_id.getText().isEmpty() && InputValidation.validate_email(textfield_emai_id.getText());
        boolean choice = choice_booleanProperty.get();
        // if are both true
       if(email_condition){
            User user= userGateway.searchUserByEmail(textfield_emai_id.getText());
            if(user.getResults().isEmpty()){
                Utility.create_alert(Alert.AlertType.INFORMATION,"","Utente Non trovato");

            }else {
                adminTable.setUser_property(user);
                adminTable.showAndWait();
            }


        }else if(choice){
            User user= userGateway.get_user_byRole(choice_role_id.getValue());
            if(user.getResults().isEmpty()){
                Utility.create_alert(Alert.AlertType.INFORMATION,"","Nessun utente Non trovato");
                property_esit.setValue(false);

            }else {
                adminTable.setUser_property(user);
                adminTable.showAndWait();
                property_esit.setValue(true);
            }

        }else{
            Utility.create_alert(Alert.AlertType.WARNING,"","Campo non corretto!");
           property_esit.setValue(false);
        }


    }

    public boolean isProperty_esit() {
        return property_esit.get();
    }

    public BooleanProperty property_esitProperty() {
        return property_esit;
    }

    public RadioButton getRadio_email() {
        return radio_email;
    }

    public RadioButton getRadio_role() {
        return radio_role;
    }

    public ChoiceBox<String> getChoice_role_id() {
        return choice_role_id;
    }

    public AdminTable getAdminTable() {
        return adminTable;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        property_esit=new SimpleBooleanProperty();
        choice_booleanProperty =new SimpleBooleanProperty(false);
        choice_role_id.setValue("Seleziona Ruolo");
        choice_role_id.getItems().addAll("purchase","seller","warehouse","pharmacist","admin");
        choice_role_id.setVisible(false);
        textfield_emai_id.setVisible(false);
       choice_role_id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                choice_booleanProperty.setValue(true);
               System.out.println("change");
           }
       });



       toggleGroup_radio =new ToggleGroup();
       toggleGroup_radio.getToggles().addAll(radio_email,radio_role);
       toggleGroup_radio.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
           @Override
           public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
               if(newValue instanceof  RadioButton radioButton){
                   if(radioButton.getId().equals("radio_role")){
                       choice_role_id.setVisible(true);
                       textfield_emai_id.setVisible(false);


                   }else{
                       choice_role_id.setVisible(false);
                       textfield_emai_id.setVisible(true);


                   }

               }
           }
       });

       Utility.add_iconButton(btn_send_filter_id, FontAwesomeSolid.PAPER_PLANE);
    }

    public Button getBtn_send_filter_id() {
        return btn_send_filter_id;
    }
    @FXML
    public void btn_add_action(ActionEvent actionEvent) {
        c_handler=new AdminCreateCredentialsControllerBase("Inserisci Utenti", userGateway);
        c_handler.executeStatus();


    }
}
