import Storage.StorageToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;
import security.TokenUtility;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Login {

    public TextField login_field;
    public TextField password_field;
    @FXML
    private Button button_click;
    public HttpResponse<String> generate_token() {

        System.out.println("generate");

        final String CLIENT_ID = "p14pZzboKJeCcIjfmkb3nyTJoD14mf1r";
        final String CLIENT_SECRET = "p6t-4gyYZAarQo2-H3Jygpk00I0esL0UMO4V869WXGQ_4_HJXMYgP9xlUphK3wsa";
        final String GRANT_TYPE = "password";
        final String audience="https://distroapi.com";
       HttpClient client = HttpClient.newHttpClient();
     JSONObject json=new JSONObject();

        json.put("grant_type", GRANT_TYPE);
        json.put("client_id", CLIENT_ID);
        json.put("client_secret", CLIENT_SECRET);
        json.put("username",login_field.getText());
        json.put("password", password_field.getText());
        json.put("audience", audience);


        HttpRequest  request= HttpRequest.newBuilder().
                uri(URI.create("https://dev-md003sye8lbs8k7g.us.auth0.com/oauth/token")).
                header("content-type","application/json").
                POST(HttpRequest.BodyPublishers.ofString(json.toString())).build();

        try {
            HttpResponse<String> response= client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void buttonOnAction(ActionEvent event)  {
       HttpResponse<String> get_token = generate_token();


int status=get_token.statusCode();
        System.out.println(status);
        if(status==200){
            System.out.println("Login successful");
            JSONObject jsonObject=new JSONObject(get_token.body());
           String token=jsonObject.get("access_token").toString();
         StorageToken.store_token(token);


Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
RolesStage.change_stage(TokenUtility.extractRole(token),stage);

        }else if(status==401){
            System.out.println("Login failed");
        }






    }










    }