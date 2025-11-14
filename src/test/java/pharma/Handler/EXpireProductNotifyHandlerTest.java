package pharma.Handler;

import algoWarehouse.LotAssigment;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;
import pharma.config.Utility;
import pharma.dao.FarmacoDao;
import pharma.dao.LotAssigmentDao;
import pharma.dao.LottiDao;
import pharma.javafxlib.test.SimulateEvents;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class EXpireProductNotifyHandlerTest {
    @Mock
    private LotAssigmentDao assigmentDao;

    @Start
    public void start(Stage stage){
        Scene scene=new Scene(new VBox(),600,700);
        stage.setScene(scene);
        stage.show();
        MockitoAnnotations.openMocks(this);

    }
    @Test
    public void test(FxRobot robot){
        when(assigmentDao.findByFarmacoAll()).thenReturn(List.of(
                FieldData.FieldDataBuilder.getbuilder().setFarmaco_id(100).setcode("ax11").setNome("Tachipirina").setNome_tipologia("Supposta").setUnit_misure("1000mg").setQuantity(100).setId(340).build(),
                FieldData.FieldDataBuilder.getbuilder().setFarmaco_id(50).setcode("ax22").setNome("Aspirina").setNome_tipologia("Compressa").setUnit_misure("100mg").setQuantity(100).setId(340).build()));
        FieldData fd_lots1=FieldData.FieldDataBuilder.getbuilder().setcode("ax26").setNome_farmaco("Tachiprina").
         setElapsed_date(Date.valueOf(LocalDate.of(2026,1,1))).build();
        FieldData fd_lots2=FieldData.FieldDataBuilder.getbuilder().setcode("ax24").setNome_farmaco("Debridat").setElapsed_date(Date.valueOf(LocalDate.of(2026,1,1))).
                build();

        Platform.runLater(()->{

           EXpireProductNotifyHandler expire_dao=new EXpireProductNotifyHandler("", assigmentDao);
            SimulateEvents.clickOn(expire_dao.getBtn_products());
            WaitForAsyncUtils.waitForFxEvents();



            expire_dao.execute();

        });
        robot.sleep(500000);


    }


}