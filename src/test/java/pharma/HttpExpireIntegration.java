package pharma;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharma.config.net.HttpExpireItem;
import pharma.config.net.ClientHttp;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class HttpExpireIntegration {
    private static WireMockServer wireMock;

    @BeforeEach
    public void setUp() {
        wireMock = new WireMockServer(3000);
        wireMock.start();
        configureFor("localhost", 3000);

        stubFor(post(urlEqualTo("/expire_item")).willReturn(aResponse().withStatus(201).withBody("Ciao")));
    }

    @AfterEach
    public void tearDown() {
        wireMock.stop();
    }


    @Test
    public void  ValidPost(){

   HttpExpireItem httpExpireItem =new HttpExpireItem(new ClientHttp());
        int status=httpExpireItem.post_expire("http://localhost:3000/expire_item",HttpExpireItemTest.get_list(),30);
        Assertions.assertEquals(201,status);
    }

}
