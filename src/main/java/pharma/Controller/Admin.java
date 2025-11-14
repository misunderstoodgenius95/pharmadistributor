package pharma.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import pharma.Handler.Info_SocietyHandler;
import pharma.Stages;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.dao.InfoSocietyDao;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;

public class Admin implements Initializable {
    public AnchorPane anchor_id;
    private Info_SocietyHandler infoSocietyHandler;
    private InfoSocietyDao societyDao;
    public Admin() {
        Properties properties=null;
        try {
           properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        societyDao=new InfoSocietyDao(Database.getInstance(properties));
        infoSocietyHandler=new Info_SocietyHandler("Aggiungi o Modifica Info Società",societyDao);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void info_society_action(ActionEvent event){
        infoSocietyHandler.execute();

    }

    public void users(ActionEvent event) {
        Stages stages=new Stages();
        try {
            Parent parent = stages.load_fxml("/subpanel/admin_role.fxml");
            anchor_id.getChildren().add(parent);
            AnchorPane.setRightAnchor(parent,80.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
