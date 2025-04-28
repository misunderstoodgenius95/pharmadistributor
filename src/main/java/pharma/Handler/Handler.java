package pharma.Handler;

import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.stage.Modality;
import pharma.Model.FieldData;
import pharma.config.Utility;
import pharma.dao.GenericJDBCDao;

import java.util.List;

public class Handler {



    public static    void  showAlert(boolean success,String error_message) {
        Alert.AlertType alertType = success ? Alert.AlertType.CONFIRMATION : Alert.AlertType.ERROR;
        String message = success ? "Inserimento effettuato" : error_message;
        Handler.create_alert(alertType, "", message);

    }
  private  static void create_alert(Alert.AlertType alert_type, String title_header, String body) {

        Alert alert = new Alert(alert_type);
        alert.setTitle(title_header);
        alert.setHeaderText(body);
        alert.getDialogPane().setId("alert");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();

    }






}
