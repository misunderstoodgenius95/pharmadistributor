package pharma.Handler;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Handler.Report.SpeseAcquisti;
import pharma.Model.Acquisto;
import pharma.javafxlib.test.SimulateEvents;

import java.sql.Date;
import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(ApplicationExtension.class)
class SpeseAcquistiTest {
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
            SpeseAcquisti speseAcquisti =new SpeseAcquisti("Visualizza Andamento", List.of());
            speseAcquisti.show();
        });
        robot.sleep(40000);




    }
    @Test
    public void testOnlyJan2025(FxRobot robot){
        Platform.runLater(()->{
            List<Acquisto> list=List.of(new Acquisto(1, "Tachipirina", 120, Date.valueOf("2025-01-05"), 8.50),
                    new Acquisto(2, "Aspirina", 85, Date.valueOf("2025-01-06"), 6.30),
                    new Acquisto(8, "Fluimucil", 95, Date.valueOf("2025-01-08"), 10.20),
                    new Acquisto(1, "Tachipirina", 150, Date.valueOf("2025-01-10"), 8.50));
            SpeseAcquisti speseAcquisti =new SpeseAcquisti("Visualizza Andamento", list);
                speseAcquisti.show();
            speseAcquisti.getYearMonthPicker().setValue(YearMonth.of(2025,1));
      SimulateEvents.clickOn(speseAcquisti.getButton());
            Assertions.assertEquals("7599.0",speseAcquisti.getLabel_result().getText());
//7599.0

        });
       // robot.sleep(40000);




    }

    @Test
    public void testOnlyJan20255(FxRobot robot) throws Exception {
        List<Acquisto> list = List.of(
                new Acquisto(1, "Tachipirina", 120, Date.valueOf("2025-01-05"), 8.50),
                new Acquisto(2, "Aspirina", 85, Date.valueOf("2025-01-06"), 6.30),
                new Acquisto(8, "Fluimucil", 95, Date.valueOf("2025-01-08"), 10.20),
                new Acquisto(1, "Tachipirina", 150, Date.valueOf("2025-01-10"), 8.50)
        );

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<SpeseAcquisti> speseRef = new AtomicReference<>();

        Platform.runLater(() -> {
            SpeseAcquisti speseAcquisti = new SpeseAcquisti("Visualizza Andamento", list);
            speseAcquisti.show();
            speseRef.set(speseAcquisti);
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        SpeseAcquisti speseAcquisti = speseRef.get();

        // Imposta YearMonth
        CountDownLatch latch2 = new CountDownLatch(1);
        Platform.runLater(() -> {
            speseAcquisti.getYearMonthPicker().setValue(YearMonth.of(2025, 1));
            latch2.countDown();
        });
        latch2.await(5, TimeUnit.SECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        // Click sul button
        CountDownLatch latch3 = new CountDownLatch(1);
        Platform.runLater(() -> {
            speseAcquisti.getButton().fire();
            latch3.countDown();
        });
        latch3.await(5, TimeUnit.SECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        // Aspetta un po' per l'aggiornamento UI
        Thread.sleep(500);

        // Leggi il risultato nel JavaFX thread
        AtomicReference<String> resultRef = new AtomicReference<>();
        CountDownLatch latch4 = new CountDownLatch(1);
        Platform.runLater(() -> {
            resultRef.set(speseAcquisti.getLabel_result().getText());
            latch4.countDown();
        });
        latch4.await(5, TimeUnit.SECONDS);

        System.out.println("Label text: '" + resultRef.get() + "'");
        Assertions.assertEquals("7599.0", resultRef.get());
    }






}