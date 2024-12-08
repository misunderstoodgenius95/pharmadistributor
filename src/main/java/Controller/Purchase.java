package Controller;


import Storage.StorageToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import security.TokenUtility;

public class Purchase{
    @FXML
    void add_casa_farmaceutica_btn(ActionEvent event) {
String token=StorageToken.get_token();
if(TokenUtility.check_permission(token,"pharma","write")){

    System.out.println("Accedi alla proceura");;
}else{

    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Permission Denied");
}



    }
}

