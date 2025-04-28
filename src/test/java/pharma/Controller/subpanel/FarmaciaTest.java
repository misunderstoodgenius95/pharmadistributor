package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.jshell.execution.LoaderDelegate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Stages;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class FarmaciaTest {
    @Start
    public void start(Stage primaryStage) throws IOException {
        Stages stage = new Stages();
         FXMLLoader loader=stage.load("/subpanel/farmacia.fxml");

        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{



        });
        robot.sleep(400000);


    }
}