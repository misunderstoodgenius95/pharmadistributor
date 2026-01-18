package pharma.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pharma.RolesStage;
import pharma.Storage.FileStorage;
import pharma.config.*;

import pharma.config.auth.UserGateway;
import pharma.config.auth.UserGatewayResponse;
import pharma.security.Stytch.StytchClient;
import pharma.Utility.TokenUtility;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    public TextField user_field;
    @FXML
    public PasswordField password_field;

    @FXML
    public void buttonOnAction(ActionEvent event) throws FileNotFoundException {

        HashMap<String,String> hashMap_json=
                FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader(PathConfig.STYTCH_CONF.getValue()));
        UserGateway userGateway =new UserGateway(new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url")));
        UserGatewayResponse response=null;
        try {
            System.out.println(password_field.getText());
            response = userGateway.authenticate(user_field.getText(), password_field.getText());
            if(response.getStatus()==200){
                Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
                String json=response.getBody();
                String token=TokenUtility.extract_token(json);
                if(token.isEmpty()){
                    throw new IllegalArgumentException("token is null");
                }
                FileStorage.setProperty("jwt",token,new FileWriter("jwt.properties"));
                RolesStage.change_stage(TokenUtility.extractRole(json),stage);
            }else{
                Utility.network_status(response.getStatus());

            }
        } catch (IOException e) {
            Utility.create_alert(Alert.AlertType.WARNING,"Login Error","Credenziali Non valide!");
            throw new RuntimeException(e);
        }catch (IllegalArgumentException e){
            Utility.create_alert(Alert.AlertType.ERROR,"Login Error","Errore Software!");
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
