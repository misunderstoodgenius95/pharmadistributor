import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.util.Objects;

import static org.mockito.Mockito.mock;

class LoginTest extends ApplicationTest {





    @Override
    public void start(Stage stage) {

        FXMLLoader fxmlLoader= null;
        try {
            fxmlLoader = FXMLLoader.load((getClass().getResource("pharma/security/login.fxml")));
            Scene scene=new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test_button(){
       // clickOn("#user_field").write("pinco");
       // clickOn("#password_field").write("pallino");
      //  clickOn("#button_click");

    }

}