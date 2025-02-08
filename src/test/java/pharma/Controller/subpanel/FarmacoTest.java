package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Stages;
import pharma.config.SimulateEvents;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class FarmacoTest {
    private Scene scene;

    @Start
    public void start(Stage primaryStage) throws IOException {
        Stages stage = new Stages();
        Parent parent = stage.load_fxml("/subpanel/farmaco.fxml");

        scene = new Scene(parent);

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