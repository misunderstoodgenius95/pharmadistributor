package pharma.config;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox=new VBox();
        vBox.getChildren().add(new Button("Hi!"));
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void create_alert() {
        Utility.create_alert(Alert.AlertType.WARNING,"aa","bbb");

    }
}