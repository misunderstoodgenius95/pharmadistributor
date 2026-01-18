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
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;
import pharma.javafxlib.test.SimulateEvents;

import java.sql.Date;
import java.time.LocalDate;

@ExtendWith(ApplicationExtension.class)
class TableBaseTest {
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
            LottoTableBase tableBase =new LottoTableBase("Scegli Lotto");
            tableBase.add_check_box_column();
            tableBase.getTableView().getItems().add(fieldData);
            SimulateEvents.setCheckBox(tableBase.get_checkbox_instance(), tableBase.getTableView().getItems().getFirst());
            tableBase.show();
            SimulateEvents.clickOn(tableBase.getButtonOK());
            WaitForAsyncUtils.waitForFxEvents();




        });

       robot.sleep(60000);


    }



}