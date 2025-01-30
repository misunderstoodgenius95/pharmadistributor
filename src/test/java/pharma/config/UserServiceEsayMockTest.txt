package pharma.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.json.JSONObject;

import org.junit.jupiter.api.*;
import pharma.oldest.UserService;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceEsayMockTest {
    private WireMockServer wireMockServer;
    private UserService userService;
    @BeforeEach
    public void setup(){
        wireMockServer=new WireMockServer(8080);
        wireMockServer.start();

        userService=new UserService(new HttpJsonClient(),new HashMap<>(),"http://localhost:8080/login");
        JSONObject response_json=new JSONObject();
        response_json.put("Status","Sucess").put("token","ed2ghghghjgjggy");


        //Stub success
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/login")).
                withRequestBody(WireMock.matchingJsonPath("$.username",WireMock.equalTo("user@example.com"))).
                withRequestBody(WireMock.matchingJsonPath("$.password",WireMock.equalTo("@5&17Vhm5QGp"))).
                willReturn(WireMock.aResponse().withStatus(200).withHeader("Content-Type","application/json").
                        withBody(response_json.toString())));

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/login"))
                .atPriority(10) // Lower priority for fallback
                .willReturn(WireMock.aResponse()
                        .withStatus(401)));


    }




    @Test
    public void autenticateWithCorrectValuesandPrecodid(){


        UserServiceResponse us_res=userService.authenticate("user@example.com","pallino");

        assertEquals(200, us_res.getStatus());
        assertEquals("ed2ghghghjgggy",new JSONObject(us_res.getBody()).get("token").toString());

    }
    @Test
    public  void authenticateWithUncorrectedValues(){

        UserServiceResponse us_res=userService.authenticate("user@example.com","@5&1-7Vh5QGp");
        assertEquals(401, us_res.getStatus());
    }



}
