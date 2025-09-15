package pharma.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Stages;
import pharma.Storage.FileStorage;
import pharma.config.auth.UserService;
import pharma.config.database.Database;
import pharma.dao.FarmaciaDao;
import pharma.security.Stytch.StytchClient;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class SellerTest {

     private UserService userService;
    private FarmaciaDao farmaciaDao;
     private  Seller seller;



    @Start
    public void start(Stage primaryStage) throws IOException {
        HashMap<String, String> hashMap_json =null;
        try {
            hashMap_json = FileStorage.getProperties(List.of("project_id", "secret", "url"), new FileReader("stytch.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Properties properties;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userService = new UserService(new StytchClient(hashMap_json.get("project_id"), hashMap_json.get("secret"), hashMap_json.get("url")));

        farmaciaDao=new FarmaciaDao(Database.getInstance(properties));
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/seller.fxml"));
        seller= new Seller(farmaciaDao,userService);
        loader.setController(seller);
        Scene scene = new Scene(loader.load());


        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();
    }
    @Test
    public  void test(FxRobot robot){
        robot.sleep(40000000);

    }
    @Test
    public  void CreatePharmacistTest(FxRobot robot){
        robot.sleep(40000000);

    }

}