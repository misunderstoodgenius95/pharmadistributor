import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("security/login.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pharmadistributor");
        primaryStage.show();
    }


}
