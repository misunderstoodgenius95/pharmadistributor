package pharma.DialogController.Report;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import pharma.Model.Acquisto;
import pharma.Service.Report.Andamento;

import java.lang.foreign.PaddingLayout;
import java.sql.Date;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class SpeseAcquistiTest {
    private List<Acquisto> acquistoList;
    public void Start(Stage stage){
        VBox vBox=new VBox();
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        acquistoList= List.of(
                new Acquisto(1, "Tachipirina", 120, Date.valueOf("2024-01-05"), 8.50),
                new Acquisto(2, "Aspirina", 85, Date.valueOf("2024-01-06"), 6.30),
                new Acquisto(8, "Fluimucil", 95, Date.valueOf("2024-01-08"), 10.20),
                new Acquisto(1, "Tachipirina", 150, Date.valueOf("2024-01-10"), 8.50),
                new Acquisto(3, "Brufen", 78, Date.valueOf("2024-01-12"), 9.80),
                new Acquisto(1, "Tachipirina", 250, Date.valueOf("2024-01-15"), 8.50),
                new Acquisto(9, "Bisolvon", 110, Date.valueOf("2024-01-18"), 11.30),
                new Acquisto(4, "Voltaren", 65, Date.valueOf("2024-01-20"), 12.50),
                new Acquisto(2, "Aspirina", 92, Date.valueOf("2024-01-22"), 6.30),
                new Acquisto(1, "Tachipirina", 180, Date.valueOf("2024-01-25"), 8.50),
                new Acquisto(1, "Tachipirina", 175, Date.valueOf("2024-02-03"), 8.50),
                new Acquisto(10, "Enterogermina", 45, Date.valueOf("2024-02-05"), 12.90),
                new Acquisto(3, "Brufen", 85, Date.valueOf("2024-02-08"), 9.80),
                new Acquisto(12, "Gaviscon", 55, Date.valueOf("2024-02-10"), 9.70),
                new Acquisto(2, "Aspirina", 70, Date.valueOf("2024-02-12"), 6.30),
                new Acquisto(8, "Fluimucil", 88, Date.valueOf("2024-02-14"), 10.20),
                new Acquisto(4, "Voltaren", 68, Date.valueOf("2024-02-18"), 12.50),
                new Acquisto(11, "Lactoflorene", 38, Date.valueOf("2024-02-20"), 18.50),
                new Acquisto(3, "Brufen", 92, Date.valueOf("2024-02-25"), 9.80),
                new Acquisto(13, "Maalox", 42, Date.valueOf("2024-02-28"), 8.90),
                new Acquisto(5, "Aerius", 95, Date.valueOf("2024-03-02"), 15.20),
                new Acquisto(6, "Reactine", 88, Date.valueOf("2024-03-05"), 13.80),
                new Acquisto(5, "Aerius", 150, Date.valueOf("2024-03-08"), 15.20),
                new Acquisto(7, "Zirtec", 105, Date.valueOf("2024-03-10"), 14.50),
                new Acquisto(5, "Aerius", 185, Date.valueOf("2024-03-12"), 15.20),
                new Acquisto(6, "Reactine", 120, Date.valueOf("2024-03-15"), 13.80),
                new Acquisto(1, "Tachipirina", 110, Date.valueOf("2024-03-18"), 8.50),
                new Acquisto(7, "Zirtec", 95, Date.valueOf("2024-03-20"), 14.50),
                new Acquisto(5, "Aerius", 165, Date.valueOf("2024-03-25"), 15.20),
                new Acquisto(6, "Reactine", 98, Date.valueOf("2024-03-28"), 13.80));
    }


/*
    @Test
    public void TrueTest(FxRobot robot){

        Platform.runLater(()-> {
            SpeseAcquisti speseAcquisti = new SpeseAcquisti("", acquistoList);
            speseAcquisti.show();
        });
        robot.sleep(60000);


    }
*/




}