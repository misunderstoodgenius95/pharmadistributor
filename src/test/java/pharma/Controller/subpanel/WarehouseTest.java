package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
class WarehouseTest {
    @Start
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/warehouse.fxml"));
    Scene scene=new Scene(fxmlLoader.load(),600,600);
  scene.getStylesheets().add(getClass().getResource("/warehouse.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{




        });
        robot.sleep(4000);


    }
}