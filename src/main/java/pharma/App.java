package pharma;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
 Stages stages = new Stages();
 stages.init();


       /* FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("security/login.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pharma distributor");
        primaryStage.show();

        */
    }


}
