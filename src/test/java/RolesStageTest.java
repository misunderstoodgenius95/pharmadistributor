import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RolesStageTest extends Application {

    @Test
    void getStage() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("purchase.fxml"));
        Scene root= null;
        try {
            root = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("purchase");
        stage.setScene(root);

    }
@Test
    @Override
    public void start(Stage primaryStage) throws Exception {

getStage();
    }
}