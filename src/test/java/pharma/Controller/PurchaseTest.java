package pharma.Controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.config.auth.UserGateway;
import pharma.dao.FarmaciaDao;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
class PurchaseTest {

     private UserGateway userGateway;
    private FarmaciaDao farmaciaDao;
     private  Seller seller;
    private Stage stage;


    @Start
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/purchase.fxml"));
        Scene scene = new Scene(loader.load());


        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        stage=primaryStage;

    }
    @Test
    public  void TestMainScreenNotLong1sec(FxRobot robot){

        long startTime = System.nanoTime();
        Platform.runLater(()-> {

            stage.show();
            });
        WaitForAsyncUtils.waitForFxEvents();
            long endTime=System.nanoTime();
            double durationMs = (endTime - startTime) / 1_000_000.0;
            System.out.println(durationMs);
            Assertions.assertThat(durationMs).isLessThan(1000);





    }





}