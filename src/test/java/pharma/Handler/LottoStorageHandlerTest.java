package pharma.Handler;

import algo.LotDimensionModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Handler.Table.TableBase;
import pharma.Model.FieldData;
import pharma.config.Utility;
import pharma.dao.*;
import pharma.javafxlib.test.SimulateEvents;
import static org.assertj.core.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class LottoStorageHandlerTest {
    private static final Logger log = LoggerFactory.getLogger(LottoStorageHandlerTest.class);
    @Mock
    private LottiDao lottiDao;
    @Mock
    private SellerOrderDetails sellerOrderDetails;
    @Mock
    private SellerOrderDao orderDao;
    @Mock
    private FarmaciaDao farmaciaDao;
    @Mock
    private LotDimensionDao lotDimensionDao;
    @Mock
    private MagazzinoDao magazzinoDao;
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
        when(lottiDao.findByLottoCode(anyString())).thenReturn(List.of(fieldData));
        Platform.runLater(()-> {

            lottoStorageHandler = new LottoStorageHandler("Scegli Lotto", List.of(lottiDao, lotDimensionDao, sellerOrderDetails,
                    orderDao, farmaciaDao, magazzinoDao));
        });

    }
 /*   @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{

            lottoStorageHandler.show();

        });

        robot.sleep(500000);

    }
*/
    @Test
    public void ValidSelectedRadio(FxRobot robot) {
        Platform.runLater(() -> {

                    lottoStorageHandler.show();
                });
            WaitForAsyncUtils.waitForFxEvents();
            SimulateEvents.writeOn(lottoStorageHandler.getTextField_lot_code(), "amax");
            SimulateEvents.clickOn(lottoStorageHandler.getSelect_lot());
            WaitForAsyncUtils.waitForFxEvents();

        TableBase lt= lottoStorageHandler.getTableLot();
        ObservableList<FieldData> observableList=lt.getTableView().getItems();
        Platform.runLater(()-> {
            if (!observableList.isEmpty()) {
                SimulateEvents.setRadioBox(lt.get_radioButton(), observableList.getFirst());
            }
        });
        WaitForAsyncUtils.waitForFxEvents();

            if(lt.getRadio_value().get() instanceof FieldData fieldData){
                fieldData= (FieldData) lt.getRadio_value().get();
                assertThat(fieldData).isEqualTo("amax");

            }else{
                System.out.println("problems");
            }





      //  robot.sleep(5000);



    }
    @Test
    public void ValidTestNotOpenLotDimensionsDialog(FxRobot robot) {
        LotDimensionModel lotDimensionModel=new LotDimensionModel("ax23",1,10,20,10,20);
       when(lotDimensionDao.findByLots(anyString(),anyInt())).thenReturn(Optional.of(lotDimensionModel));
        Platform.runLater(() -> {

                    lottoStorageHandler.show();
                });
            WaitForAsyncUtils.waitForFxEvents();
            SimulateEvents.writeOn(lottoStorageHandler.getTextField_lot_code(), "amax");
            SimulateEvents.clickOn(lottoStorageHandler.getSelect_lot());
            WaitForAsyncUtils.waitForFxEvents();
         TableBase lt= lottoStorageHandler.getTableLot();
        ObservableList<FieldData> observableList=lt.getTableView().getItems();
        Platform.runLater(()-> {
            if (!observableList.isEmpty()) {
                SimulateEvents.setRadioBox(lt.get_radioButton(), observableList.getFirst());
            }
        });
        SimulateEvents.clickOn(lt.getButtonOK());
        WaitForAsyncUtils.waitForFxEvents();
        Label label=lottoStorageHandler.getVisibile_label();
        assertThat(label.isVisible()).isFalse();









    }
    @Test
    public void ValidTestOpenLotDimensionsDialog(FxRobot robot) {
        when(lotDimensionDao.findByLots(anyString(),anyInt())).thenReturn(Optional.empty());

        Platform.runLater(() -> {
                    lottoStorageHandler.show();
                });
        WaitForAsyncUtils.waitForFxEvents();
            SimulateEvents.writeOn(lottoStorageHandler.getTextField_lot_code(), "amax");
            SimulateEvents.clickOn(lottoStorageHandler.getSelect_lot());


            TableBase lt= lottoStorageHandler.getTableLot();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        Platform.runLater(()-> {
    ObservableList<FieldData> observableList=lt.getTableView().getItems();
    if(!observableList.isEmpty()) {
        SimulateEvents.setRadioBox(lt.get_radioButton(), observableList.getFirst());
    }
        });


            SimulateEvents.clickOn(lt.getButtonOK());
        WaitForAsyncUtils.waitForFxEvents();
            Label label=lottoStorageHandler.getVisibile_label();
        assertThat(label.isVisible()).isTrue();


   //   robot.sleep(50000);



    }


    @Test
    public void ValidTestLotDimensionInserted(FxRobot robot) {
        when(lotDimensionDao.findByLots(anyString(), anyInt())).thenReturn(Optional.empty());
        when(lotDimensionDao.insert(Mockito.any())).thenReturn(true);

        Platform.runLater(() -> {

            lottoStorageHandler.show();
        });
        WaitForAsyncUtils.waitForFxEvents();
        SimulateEvents.writeOn(lottoStorageHandler.getTextField_lot_code(), "amax");
        SimulateEvents.clickOn(lottoStorageHandler.getSelect_lot());

        WaitForAsyncUtils.waitForFxEvents();
        TableBase lt = lottoStorageHandler.getTableLot();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        Platform.runLater(() -> {
            ObservableList<FieldData> observableList = lt.getTableView().getItems();
            if (!observableList.isEmpty()) {
                SimulateEvents.setRadioBox(lt.get_radioButton(), observableList.getFirst());
            }
        });


        SimulateEvents.clickOn(lt.getButtonOK());

        SimulateEvents.clickOn(lottoStorageHandler.getBnt_lot_dimension());
        WaitForAsyncUtils.waitForFxEvents();
        CustomLotsDimension dimensionDialog = lottoStorageHandler.getLotDimensionDialog();
        List<Spinner> list = Utility.extract_value_from_list(dimensionDialog.getControlList(), Spinner.class);
        Spinner<Double> length = list.getFirst();
        SimulateEvents.setSpinnerDouble(length, 10.30);
        WaitForAsyncUtils.waitForFxEvents();
        SimulateEvents.clickOn(dimensionDialog.getButtonOK());
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(dimensionDialog.isCond()).isTrue();
    }
    @Test
    public void InValidTestLotDimensionInserted(FxRobot robot) {
        when(lotDimensionDao.findByLots(anyString(), anyInt())).thenReturn(Optional.empty());
        when(lotDimensionDao.insert(Mockito.any())).thenReturn(false);

        Platform.runLater(() -> {
            lottoStorageHandler.show();
        });
        WaitForAsyncUtils.waitForFxEvents();
        SimulateEvents.writeOn(lottoStorageHandler.getTextField_lot_code(), "amax");
        SimulateEvents.clickOn(lottoStorageHandler.getSelect_lot());


        TableBase lt = lottoStorageHandler.getTableLot();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        Platform.runLater(() -> {
            ObservableList<FieldData> observableList = lt.getTableView().getItems();
            if (!observableList.isEmpty()) {
                SimulateEvents.setRadioBox(lt.get_radioButton(), observableList.getFirst());
            }
        });


        SimulateEvents.clickOn(lt.getButtonOK());

        SimulateEvents.clickOn(lottoStorageHandler.getBnt_lot_dimension());
        WaitForAsyncUtils.waitForFxEvents();
        CustomLotsDimension dimensionDialog = lottoStorageHandler.getLotDimensionDialog();
        List<Spinner> list = Utility.extract_value_from_list(dimensionDialog.getControlList(), Spinner.class);
        Spinner<Double> length = list.getFirst();
        SimulateEvents.setSpinnerDouble(length, 10.30);
        WaitForAsyncUtils.waitForFxEvents();
        SimulateEvents.clickOn(dimensionDialog.getButtonOK());
        assertThat(dimensionDialog.isCond()).isFalse();
    }


    @Nested
    class  ChoiceWarehouse{
        @BeforeEach
        public void setUp(){
            when(lotDimensionDao.findByLots(anyString(), anyInt())).thenReturn(Optional.empty());
            when(lotDimensionDao.insert(Mockito.any())).thenReturn(true);


        }
        @Test
    public void Valid(FxRobot robot) {


        Platform.runLater(() -> {

            lottoStorageHandler.show();
        });
        WaitForAsyncUtils.waitForFxEvents();
        SimulateEvents.writeOn(lottoStorageHandler.getTextField_lot_code(), "amax");
        SimulateEvents.clickOn(lottoStorageHandler.getSelect_lot());

        WaitForAsyncUtils.waitForFxEvents();
        TableBase lt = lottoStorageHandler.getTableLot();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        Platform.runLater(() -> {
            ObservableList<FieldData> observableList = lt.getTableView().getItems();
            if (!observableList.isEmpty()) {
                SimulateEvents.setRadioBox(lt.get_radioButton(), observableList.getFirst());
            }
        });


        SimulateEvents.clickOn(lt.getButtonOK());

        SimulateEvents.clickOn(lottoStorageHandler.getBnt_lot_dimension());
        WaitForAsyncUtils.waitForFxEvents();
        CustomLotsDimension dimensionDialog = lottoStorageHandler.getLotDimensionDialog();
        List<Spinner> list = Utility.extract_value_from_list(dimensionDialog.getControlList(), Spinner.class);
        Spinner<Double> length = list.getFirst();
        SimulateEvents.setSpinnerDouble(length, 10.30);
        WaitForAsyncUtils.waitForFxEvents();
        SimulateEvents.clickOn(dimensionDialog.getButtonOK());

        robot.sleep(400000);
    }
    }






}