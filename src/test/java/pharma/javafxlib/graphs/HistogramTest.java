package pharma.javafxlib.graphs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class HistogramTest {

    @Start
    public void start(Stage stage){
        Histogram histogram=new Histogram("Anno/Mese","Spese","Andamento Spese");
        histogram.addData("10/2024",1000);
        histogram.addData("11/2024",2000);
        histogram.addSeries();


        Scene scene=new Scene(histogram.getBarChart());
        stage.setScene(scene);
        stage.setTitle("BarChart Example");
        stage.initStyle(StageStyle.DECORATED);
        stage.show();

    }


    @Test
    public void test(FxRobot robot){
        robot.sleep(40000);




    }





}