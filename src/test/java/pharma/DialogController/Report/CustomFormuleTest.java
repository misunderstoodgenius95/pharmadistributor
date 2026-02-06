package pharma.DialogController.Report;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class CustomFormuleTest {
    @Start
    public void start(Stage stage){
        Scene scene=new Scene(new VBox(),900,900);
        stage.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }
    @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{
            CustomFormule customFormule=new CustomFormule("");
            customFormule.show();



        });
        robot.sleep(50000);


    }









}