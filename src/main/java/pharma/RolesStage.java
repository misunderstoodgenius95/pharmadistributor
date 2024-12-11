package pharma;

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
                "purchase", "/purchase.fxml",
                "seller", "/seller.fxml",
                "warehouse", "/warehouse.fxml",
                "admin", "/admin.fxml"));
    }


public static void change_stage(String role, Stage stage){
     String role_extact= roles_stage.get(role);
Stages stages= new Stages();
stages.change_stage(role_extact, stage);

}

}
