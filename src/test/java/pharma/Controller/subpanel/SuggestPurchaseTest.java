package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.dao.FarmacoDao;
import pharma.dao.LotAssigmentDao;
import pharma.dao.SellerOrderDao;
import pharma.formula.suggest.Model.Lots;
import pharma.formula.suggest.Model.SellerOrders;
import pharma.formula.suggest.Model.SuggestConfig;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class SuggestPurchaseTest {
    @Mock
    private FarmacoDao farmacoDao;
    SuggestPurchase suggestPurchase;
    private List<Lots> lotsList;
    private SuggestConfig suggestConfig;
    private List<SellerOrders>sellerOrders;
    @Mock
    private LotAssigmentDao assigmentDao;
    @Mock
    private SellerOrderDao sellerOrderDao;
    @Start
    public void start(Stage primaryStage) throws IOException {
        MockitoAnnotations.openMocks(this);
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/subpanel/suggest_purchase.fxml"));
        suggestPurchase=new SuggestPurchase(farmacoDao,assigmentDao,suggestConfig,sellerOrderDao);
        loader.setController(suggestPurchase);
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();

    }

    @Test
    public void test(FxRobot robot){
        when(farmacoDao.findByName(anyString())).thenReturn(List.of( FieldData.FieldDataBuilder.getbuilder().setNome("Tachipirina").setDescription("Farmaco per la febbre.").setCategoria(1).
                setNome_tipologia("Supposte").setUnit_misure("100mg").setNome_categoria("AntiInfiammatorio").setQuantity(30).setNome_casa_farmaceutica("Angelini").setNome_principio_attivo("Paracetamolo").build()));

        when(assigmentDao.findQuantitybyFarmacoId(anyInt())).thenReturn(

                List.of(FieldData.FieldDataBuilder.getbuilder().setcode("ax21").setFarmaco_id(10).
                setElapsed_date(Date.valueOf(LocalDate.of(2025, 10, 1))).setAvailability(40).build()),
                List.of(FieldData.FieldDataBuilder.getbuilder().setcode("ax22").setFarmaco_id(1).
                        setElapsed_date(Date.valueOf(LocalDate.of(2025, 10, 1))).setAvailability(40).build())


        );

        suggestConfig = new SuggestConfig(180, 500,200);
     /*   SellerOrders sellerOrders1=new SellerOrders(1,200, Date.valueOf(LocalDate.now()));
        SellerOrders sellerOrders2=new SellerOrders(1,200,Date.valueOf(LocalDate.now()));
        sellerOrders.addAll(List.of(sellerOrders1,sellerOrders2));*/
   /*     Lots lots1 = new Lots("ax21", 10, Date.valueOf(LocalDate.of(2025, 10, 1)), 40);
        Lots lots2 = new Lots("ax22", 11, Date.valueOf(LocalDate.of(2025, 10, 1)), 30);
        lotsList.addAll(List.of(lots1,lots2));*/
        Platform.runLater(()->{
        });
        robot.sleep(900000);


    }

}