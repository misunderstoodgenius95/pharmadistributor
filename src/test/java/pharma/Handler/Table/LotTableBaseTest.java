package pharma.Handler.Table;

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
import pharma.Model.FieldData;
import pharma.javafxlib.test.SimulateEvents;

import javax.xml.crypto.dsig.SignatureMethod;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class LotTableBaseTest {
    @Start
    public void start(Stage stage){
        Scene scene=new Scene(new VBox(),500,600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void ValidTest(FxRobot robot) {

        Platform.runLater(()->{
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setcode("amax").setFarmaco_id(10).setNome("Tachipirina").
                    setNome_categoria("Capusle").setNome_tipologia("Antidepressivo").setUnit_misure("100mg").
                    setNome_principio_attivo("Paracetamolo").setNome_casa_farmaceutica("Angelini").setQuantity(30).
                    setProduction_date(Date.valueOf(LocalDate.of(2024, 10, 10))).
                    setElapsed_date(Date.valueOf(LocalDate.of(2025, 10, 01))).build();
            LotTableBase lotTableBase=new LotTableBase("Scegli Lotto");
            lotTableBase.add_check_box_column();
            lotTableBase.getTableViewProductTable().getItems().add(fieldData);
            SimulateEvents.setCheckBox(lotTableBase.get_checkbox_instance(),lotTableBase.getTableViewProductTable().getItems().getFirst());
            lotTableBase.show();
            SimulateEvents.clickOn(lotTableBase.getButtonOK());
            WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertEquals("amax",lotTableBase.getCheckBoxValue().get().getCode());



        });

       // robot.sleep(60000);


    }



}