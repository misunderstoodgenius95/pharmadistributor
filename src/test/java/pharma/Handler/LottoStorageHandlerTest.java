package pharma.Handler;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.awaitility.constraint.WaitConstraint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Handler.Table.LotTableBase;
import pharma.Model.FieldData;
import pharma.dao.LotDimension;
import pharma.dao.LottiDao;
import pharma.javafxlib.test.SimulateEvents;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class LottoStorageHandlerTest {
    @Mock
    private LottiDao lottiDao;
    @Mock
    private LotDimension lotDimension;
    private  LottoStorageHandler lottoStorageHandler;
    @Start
    public void start(Stage stage){
        Scene scene=new Scene(new VBox(),500,600);
        stage.setScene(scene);
        stage.show();


    }
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setcode("amax").setFarmaco_id(10).setNome("Tachipirina").
                setNome_categoria("Capusle").setNome_tipologia("Antidepressivo").setUnit_misure("100mg").
                setNome_principio_attivo("Paracetamolo").setNome_casa_farmaceutica("Angelini").setQuantity(30).
                setProduction_date(Date.valueOf(LocalDate.of(2024, 10, 10))).
                setElapsed_date(Date.valueOf(LocalDate.of(2025, 10, 01))).build();
        when(lottiDao.findByLottoCode(Mockito.anyString())).thenReturn(List.of(fieldData));
        when(lotDimension.insert(any(FieldData.class))).thenReturn(true);
    }
    @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{
            lottoStorageHandler=new LottoStorageHandler("Scegli Lotto", List.of(lottiDao,lotDimension));
            lottoStorageHandler.show();

        });

        robot.sleep(500000);

    }
    @Test
    public void ValidTest(FxRobot robot){
        Platform.runLater(()->{
            lottoStorageHandler=new LottoStorageHandler("Scegli Lotto", List.of(lottiDao));
            lottoStorageHandler.show();
            SimulateEvents.writeOn(lottoStorageHandler.getTextField_lot_code(),"amax");
            SimulateEvents.clickOn(lottoStorageHandler.getSelect_lot());

            LotTableBase lt= lottoStorageHandler.getTableLot();
            Assertions.assertTrue(lt.getTableViewProductTable().getItems().isEmpty());
            lt.getTableViewProductTable().getItems().addListener(new ListChangeListener<FieldData>() {
                @Override
                public void onChanged(Change<? extends FieldData> c) {
                while(c.next()){
                    if(c.wasAdded()){
                        SimulateEvents.setCheckBox(lt.get_checkbox_instance(),lt.getTableViewProductTable().getItems().getFirst());

                        SimulateEvents.clickOn(lt.getButtonOK());
                        WaitForAsyncUtils.waitForFxEvents();


                        //Assertions.assertEquals("amax",lt.getCheckBoxValue().get().getCode());

                    }

                }
                }
            });
            lt.getCheckBoxValue().addListener(new ChangeListener<FieldData>() {
                @Override
                public void changed(ObservableValue<? extends FieldData> observable, FieldData oldValue, FieldData newValue) {
                    if(newValue!=null){
                        Assertions.assertEquals("amax",lt.getCheckBoxValue().get().getCode());
                    }
                }
            });
        //    Assertions.assertEquals("amax",lt.getCheckBoxValue().get().getCode());

         //  SimulateEvents.setCheckBox(lt.get_checkbox_instance(),lt.getTableViewProductTable().getItems().getFirst());

        });
    //    robot.sleep(50000);




    }


}