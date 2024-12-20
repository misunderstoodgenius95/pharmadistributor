package pharma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Stages  {

    public  Parent  load_fxml(String fxmlPath) throws IOException {

FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(fxmlPath));

return fxmlLoader.load();
    }
public void init() throws IOException {

Stage primaryStage = new Stage();
    FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("security/login.fxml"));
    Scene scene=new Scene(fxmlLoader.load());
    primaryStage.setScene(scene);
    primaryStage.setTitle("Pharma distributor");
    primaryStage.show();

}

    public  void change_stage(String fxml, Stage stage)  {
        try {
Stages stages = new Stages();
                Scene scene=new Scene(this.load_fxml(fxml));
                stage.setScene(scene);

            }catch (NullPointerException e){
                e.printStackTrace();
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
