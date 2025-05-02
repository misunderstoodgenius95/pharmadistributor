package pharma.javafxlib.Controls;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.HttpExpireItemTest;
import pharma.javafxlib.Controls.Notification.JsonNotify;

import java.util.List;

@ExtendWith(ApplicationExtension.class)
class JsonNotifyTest {
    private VBox vBox;
    @Start
    public void start(Stage stage){
        vBox=new VBox();
        Scene scene=new Scene(vBox);
       stage.setScene(scene);
        stage.show();


    }

    @Test
    public void ValidateSingleNotify(FxRobot robot){
        JSONObject json1 = new JSONObject();
        json1.put("lotto_id", "11a");
        json1.put("product_id", 60);
        json1.put("expiration_date", "10/10/2028");
        json1.put("time_of_day", 30);
 /*       Platform.runLater(()-> {
             aJsonNotify jsonNotify = new JsonNotify(json1.toString(), List.of("lotto_id"), "Scadenza Lotti", "Lotto Scaduto: ");
            jsonNotify.execute();
        });
        robot.sleep(4000);
*/


    }
    @Test
    public void ValidateMultiNotify(FxRobot robot){
        String value=HttpExpireItemTest.get_json_array().toString();
        Platform.runLater(()-> {
            JsonNotify jsonNotify = new JsonNotify(value, List.of("lotto_id"), "Scadenza Lotti", "Lotto Scaduto: ");
            jsonNotify.execute();
        });
        robot.sleep(40000000);



    }
    @Test
    public void ValidateMultiNotifyNotClosing(FxRobot robot){
        String value=HttpExpireItemTest.get_json_array().toString();
        Platform.runLater(()-> {
            JsonNotify jsonNotify = new JsonNotify(value, List.of("lotto_id"), "Scadenza Lotti", "Lotto Scaduto: ");
            jsonNotify.execute();
        });
        robot.sleep(40000000);



    }





}