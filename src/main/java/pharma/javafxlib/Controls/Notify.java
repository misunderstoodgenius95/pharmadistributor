package pharma.javafxlib.Controls;

import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.stage.Window;
import javafx.util.Duration;

import org.controlsfx.control.Notifications;



public class Notify {


    public static void create(String title, String body) {
        Notifications.create().title(title).text(body).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).showWarning();


    }

    public static void createWithButtonClick(String title, String body,Window window) {

        Notifications notification = Notifications.create()
                .title(title)
                .text(body)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.INDEFINITE).owner(window);
        // Add this line
        notification.show();
    }









}
