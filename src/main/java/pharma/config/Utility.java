package pharma.config;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class Utility {

    public static void create_alert(Alert.AlertType alert_type, String title_header,String body){

        Alert alert = new Alert(alert_type);
        alert.setTitle(title_header);
        alert.setHeaderText(body);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();

    }
}
