import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RolesStage  {
    private static HashMap<String, String> roles_stage = new HashMap<>();

  static {

        roles_stage.putAll(Map.of(
                "purchase", "purchase.fxml",
                "seller", "seller.fxml",
                "warehouse", "warehouse.fxml",
                "admin", "admin.fxml"));
    }


public static void change_stage(String role, Stage stage){

      String fxml=null;
      try {
          roles_stage.get(role);
          FXMLLoader fxmlLoade=new FXMLLoader(RolesStage.class.getResource("purchase.fxml"));
        Scene scene=new Scene(fxmlLoade.load());
          stage.setScene(scene);
      }catch (NullPointerException | IOException e){
        e.printStackTrace();
      }
}

}
