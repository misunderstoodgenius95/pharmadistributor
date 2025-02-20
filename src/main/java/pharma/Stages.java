package pharma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Stages  {

    public  Parent  load_fxml(String fxmlPath) throws IOException {

FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(fxmlPath));

return fxmlLoader.load();
    }
    public  FXMLLoader  load(String fxmlPath) throws IOException {

       return new FXMLLoader(this.getClass().getResource(fxmlPath));


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

                Scene scene=new Scene(this.load_fxml(fxml+".fxml"));
                scene.getStylesheets().add(fxml+".css");
                stage.setScene(scene);

            }catch (NullPointerException e){
                e.printStackTrace();
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
