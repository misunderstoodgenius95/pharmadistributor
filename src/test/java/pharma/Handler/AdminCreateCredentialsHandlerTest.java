package pharma.Handler;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.config.Utility;
import pharma.config.auth.UserService;
import pharma.javafxlib.test.SimulateEvents;
import pharma.security.Stytch.StytchClient;

import java.util.List;

@ExtendWith(ApplicationExtension.class)
class AdminCreateCredentialsHandlerTest {

    private UserService userService;
    @Start
    public void  start(Stage stage){
        VBox vbox=new VBox();
        Scene scene=new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        String project_id="project-test-ead7077c-e25f-4fe1-ba63-3e5972ab34ec";
        String secret="secret-test-GJ9p83rxjv8pT7WNQnoqLBaPgikz4ZV1UB8=";
        String endpoint="https://delicious-nose-9298.customers.stytch.dev";
        StytchClient stytchClient= new StytchClient(project_id,secret,endpoint);
        userService=new UserService(stytchClient);
    }

    @Test
    public void TestValidateCreate(FxRobot robot){
        Platform.runLater(()->{


            AdminCreateCredentialsHandler adminCreateCredentialsHandler =new AdminCreateCredentialsHandler("", userService);

            List<TextField> list_textField=Utility.extract_value_from_list(adminCreateCredentialsHandler.getControlList(), TextField.class);
            list_textField.get(0).setText("luigi.neri_azienda@proton.me");
            list_textField.get(1).setText("Luigi");
            list_textField.get(2).setText("Neri");
            SearchableComboBox<FieldData> choiceBox=Utility.extract_value_from_list(adminCreateCredentialsHandler.getControlList(), SearchableComboBox.class).getFirst();
            SimulateEvents.setFirstElementSearchableBox(choiceBox);
            adminCreateCredentialsHandler.executeStatus();



        });
           robot.sleep(10000000);



    }

}