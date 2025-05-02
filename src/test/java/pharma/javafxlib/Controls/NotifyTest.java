package pharma.javafxlib.Controls;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.NotificationPane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.Not;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class NotifyTest {
    private VBox vBox;
    private  NotificationPane notificationPane;
    @Start
    public void start(Stage stage) {


        vBox = new VBox();
        Scene scene = new Scene(vBox,400, 300);

        stage.setScene(scene);
        stage.show();
}

    

    @Test
    public void TestNotify(FxRobot robot) {
        System.out.println("pre:  " + Window.getWindows().size());
        Platform.runLater(() -> {
            Notify.create("Hi", "Hello Message!");
        });
        System.out.println("after: " + Window.getWindows().size());
        robot.sleep(4000);


    }
    @Test
    public void notifyPanel(FxRobot robot) {

            NotificationPane notificationPane = new NotificationPane(vBox);
            notificationPane.setShowFromTop(false);


        robot.interact(() -> {
            notificationPane.setText("This is a test notification!");
            notificationPane.show();
        });


        robot.sleep(3000); // Allow time for visual confirmation (optional in headless)
    }




}