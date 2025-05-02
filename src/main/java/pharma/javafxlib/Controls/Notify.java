package pharma.javafxlib.Controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

import javax.management.Notification;

public class Notify {
    private  static  boolean success;

  public static void create(String title, String body){
        Notifications.create().title(title).text(body).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).showWarning();




    }
    public static void createWithButtonClick(String title, String body){

        Notifications notification=Notifications.create().title(title).text(body)
                .position(Pos.BOTTOM_RIGHT).
                hideAfter(Duration.INDEFINITE);
        notification.show();






    }








}
