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
import pharma.Model.DistribuzioneModel;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class DistributionPercentuagesPharmaTest {
    @Start
    public void start(Stage stage){
        Scene scene=new Scene(new VBox());
        stage.setScene(scene);
        stage.show();
    }
    @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{
            List<DistribuzioneModel> list=List.of(new DistribuzioneModel("Pfizer",10),
            new DistribuzioneModel("Novartis",100),
                    new DistribuzioneModel("Bayer",300),
                    new DistribuzioneModel("Angelini",200),
            new DistribuzioneModel("Infecto Pharma",200));


/*
DistribuzionePharma distribuzionePharma=new DistribuzionePharma("");

distribuzionePharma.show();
*/






        });
        robot.sleep(30000);

    }


}