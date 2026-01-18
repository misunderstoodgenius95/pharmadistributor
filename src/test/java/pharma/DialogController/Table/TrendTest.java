package pharma.DialogController.Table;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.DialogController.Report.Trend;
import pharma.Model.Acquisto;

import java.sql.Date;
import java.util.List;


@ExtendWith(ApplicationExtension.class)
class TrendTest {
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
          Trend trend=new Trend("",List.of());
          trend.show();
        });
        robot.sleep(40000);




    }
    @Test
    public void TrendMesi(FxRobot robot){

        List<Acquisto>list=List.of(
                new Acquisto(1, "Tachipirina", 120, Date.valueOf("2025-01-05"), 8.50),
                new Acquisto(2, "Aspirina", 85, Date.valueOf("2025-01-06"), 6.30),
                new Acquisto(8, "Fluimucil", 95, Date.valueOf("2025-03-08"), 10.20),
                new Acquisto(1, "Tachipirina", 150, Date.valueOf("2025-03-10"), 8.50));
        Platform.runLater(()->{
            Trend trend=new Trend("Visualizza Trend",list);
            trend.show();
        });
        robot.sleep(40000);




    }
}