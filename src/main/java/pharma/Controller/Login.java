package pharma.Controller;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;
import pharma.RolesStage;
import pharma.Storage.FileStorage;
import pharma.config.*;

import pharma.security.TokenUtility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

public class Login {
    @FXML
    public TextField user_field;
    @FXML
    public TextField password_field;

    @FXML
    public void buttonOnAction(ActionEvent event) throws FileNotFoundException {

        HashMap<String,String> hashMap_json=
                FileStorage.getProperties(List.of("client_id","client_secret","grant_type","audience","url"),new FileReader("auth.properties"));
        UserService userService=new UserService(AuthAPI.newBuilder(hashMap_json.get("url"),hashMap_json.get("client_id"),hashMap_json.get("client_secret")).build());
        Response<TokenHolder> response= null;
        try {
            response = userService.authenticate(user_field.getText(), password_field.getText(),hashMap_json.get("audience"));
            if(response.getStatusCode()==200){
                Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
                String token=response.getBody().getAccessToken();
                RolesStage.change_stage(TokenUtility.extractRole(token),stage);


            }
        } catch (Auth0Exception e) {
            System.out.println(e.getMessage());
            Utility.create_alert(Alert.AlertType.WARNING,"Login Error","Credenziali Non valide!");
        }



    }



}
