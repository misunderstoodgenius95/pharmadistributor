package pharma.javafxlib.Controls;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class NotificationPanelLibTest {
    @Start
    public void start(Stage stage) {

       Label content = new Label("Main content here");

        NotificationPanelLib panel = new NotificationPanelLib(content);
        Scene scene = new Scene(panel.getPane(), 400, 200);

        stage.setScene(scene);
        stage.show();

        panel.show("Welcome to the app!");
    }
    @Test
    public  void test(FxRobot robot){

        robot.sleep(4000);


    }
}