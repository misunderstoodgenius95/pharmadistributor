package pharma.Controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Stages;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class AdminTest {
    @Start
    public void start(Stage primaryStage) throws IOException {
        Stages stage = new Stages();
        Parent parent = stage.load_fxml("/admin.fxml");
         Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();


    }
    @Test
    public void test(FxRobot robot){
        robot.sleep(4000000);




    }

}