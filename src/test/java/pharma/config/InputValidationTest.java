package pharma.config;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class InputValidationTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/purchase.fxml")));
        stage.setScene(scene);
        stage.setTitle("Java Test");
        stage.show();
    }
@Test
    public  void InputValidationTest() {
    Platform.runLater(() -> {
        TextField field = new TextField();
        field.setText("IT123456778001");
        Assertions.assertTrue(InputValidation.validate_p_iva(field));

    });
    }















}
