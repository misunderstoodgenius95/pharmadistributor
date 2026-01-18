package pharma.DialogController.Report;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.List;

@ExtendWith(ApplicationExtension.class)
class VariazioneTest {
    @Start
    public void Start(Stage stage){
        VBox vBox=new VBox();
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }


    @Test
    public void Emptytest(FxRobot robot){
        Platform.runLater(()->{
            Variazione variazione=new Variazione("Visualizza Andamento", List.of());
          variazione.show();
        });
        robot.sleep(40000);




    }

}