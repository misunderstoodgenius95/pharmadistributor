package pharma.oldest;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pharma.config.HttpJsonClient;
import pharma.config.InputValidation;
import pharma.config.UserServiceResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class UserServiceTest {
     private UserService userService;
     private HttpJsonClient mock_jsonClient;
     private HttpResponse<String> mock_response;
    @BeforeEach
    void setup() {
        mock_jsonClient=Mockito.mock(HttpJsonClient.class);
        userService=new UserService(mock_jsonClient,new HashMap<>(),"http://example.com");
        mock_response=Mockito.mock(HttpResponse.class);
        when(mock_response.statusCode()).thenReturn(200);
        when(mock_jsonClient.post(anyString(),any(JSONObject.class))).thenReturn(mock_response);

    }

    //Valid
    @Test
    public void valid_authentication(){
    try(MockedStatic<InputValidation> input_validation_mock=Mockito.mockStatic(InputValidation.class)) {
        input_validation_mock.when(() -> InputValidation.validate_email(anyString())).thenReturn(true);
        input_validation_mock.when(() -> InputValidation.validate_password(anyString())).thenReturn(true);


        String email = "user@example.com";
        String password = "@5&17Vhm5QGp";
        UserService userService = new UserService(mock_jsonClient, new HashMap<>(), "http://example.com/api/auth");
        UserServiceResponse us = userService.authenticate(email, password);
        Assertions.assertEquals(200, us.getStatus());
    }}
    // Invalid when username and password are null
    // Invalid when username is null and password is not null
    // Incalid when




@Test
public void invalid_authentication() throws MalformedURLException {

        URL url=new URL("ciao");

}



    @Test
    public void get_api(){
        HttpJsonClient httpJsonClient=new HttpJsonClient();
        httpJsonClient.post("auth.apidistro.ct.ws",new JSONObject().
                put("authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkFuVmtvcFMyVWREVm01LXVEWkZwSCJ9.eyJpc3MiOiJodHRwczovL2Rldi1tZDAwM3N5ZThsYnM4azdnLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJwMTRwWnpib0tKZUNjSWpmbWtiM255VEpvRDE0bWYxckBjbGllbnRzIiwiYXVkIjoiYXV0aC5hcGlkaXN0cm8uY3Qud3MiLCJpYXQiOjE3MzY4NDc5OTgsImV4cCI6MTczNjkzNDM5OCwiZ3R5IjoiY2xpZW50LWNyZWRlbnRpYWxzIiwiYXpwIjoicDE0cFp6Ym9LSmVDY0lqZm1rYjNueVRKb0QxNG1mMXIifQ.RweTERz_unVTCzGP0B0nc_5WEb9oUaNJXBh3N1Nnt0iR1GJ3iwvg6cYDbcGqg91oveY-rQol1HruUHLX58IrLwzrNMGoaJc079bH_NaeF5VKb1AHDR2RlfkMF6nfjA0PhrOdIvZDmUpnUBZ-HbFbL7Q5fq7zH1eTodwjovI-TUhJCxcxnZEyCf7yOTMi0kdmrdeEKGGOOwIFjcKTTpHsQ6Zm6DKyo5naE3STpcS5WIZg84h-woIdvcFHZ827zC3eSYcymdgmVpmCa_1hjDI9dDFF1AmeO45vo3KvDOXyfD4isUPWejOeuaYHwRpi6zKSdsCYSfRK1r8vkOKoOSo3Kg"));
    }
/*
    @Test
    public void integration_test_auth() throws FileNotFoundException {
      HashMap<String,String> hashMap_json=
                FileStorage.getProperties(List.of("client_id","client_secret","grant_type","audience","url"),new FileReader("auth.properties"));
        UserService userService1=new UserService(new HttpJsonClient(),hashMap_json,"http://example.com");
        int res=userService1.authenticate("lucagcalio95@live.com","ULnWFkRh2w$QB&QQ7nrD").getStatus();
        assertEquals(200, res);
    }

    @Test void  auth(){
JSONObject jsonObject=new JSONObject();

        jsonObject.put("password","pallino");
        jsonObject.put("username","pinco");

when(mock_response.statusCode()).thenReturn(200);

        System.out.println(mock_response.statusCode());
        when(jsonClient.post(eq("http://example.com"),any(JSONObject.class))).thenReturn(mock_response);
        int actual=userService.authenticate("pinco","pallino").getStatus();
        assertEquals(200, actual);

       verify(jsonClient, times(1)).post(eq("http://example.com"),any(JSONObject.class));
    }


 */

}