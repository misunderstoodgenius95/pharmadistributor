package pharma.DialogController;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.dao.PromotionDao;
import pharma.dao.SellerPriceDao;
import pharma.Service.Promotion.PromotionsSuggestService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class SuggestPromotionHandlerTest {
    @Mock
    private SellerPriceDao sellerPriceDao;
    @Mock
    private PromotionsSuggestService promotionsSuggestService;
    @Mock
    private PromotionDao promotionDao;

    @Start
    public  void start(Stage stage){
        Scene scene=new Scene(new VBox());
        stage.setScene(scene);
        stage.show();
        MockitoAnnotations.openMocks(this);



    }


    @Test
    public void Validtest(FxRobot robot) {
        when(promotionDao.insert(any(FieldData.class))).thenReturn(true);
        when(sellerPriceDao.findCurrentPricebyFarmaco(anyInt())).thenReturn(10.50);
        when(sellerPriceDao.findAll()).
                thenReturn(Arrays.asList(
                        FieldData.FieldDataBuilder.getbuilder().setNome("Tachipirina").setNome_tipologia("Supposta").setUnit_misure("100mg").setQuantity(100).setId(340).build() ));
        /*Platform.runLater(()->{
            when(promotionsSuggestService.get_promotion_discount(anyInt())).thenReturn(10);
            SimpleBooleanProperty s_boolean=new SimpleBooleanProperty();
         SuggestPromotionControllerBase promotionHandler=new SuggestPromotionControllerBase("Promozione",sellerPriceDao,promotionsSuggestService,promotionDao,s_boolean);
        promotionHandler.execute();
        });*/
        robot.sleep(40000);






    }
    /*@Test
    public void InValidtest(FxRobot robot) {
        when(promotionDao.insert(any(FieldData.class))).thenReturn(false);
        when(sellerPriceDao.findCurrentPricebyFarmaco(anyInt())).thenReturn(10.50);
        when(sellerPriceDao.findAll()).
                thenReturn(Arrays.asList(
                        FieldData.FieldDataBuilder.getbuilder().setNome("Tachipirina").setNome_tipologia("Supposta").setUnit_misure("100mg").setQuantity(100).setId(340).build() ));
        Platform.runLater(()->{
            when(promotionsSuggestService.get_promotion_discount(anyInt())).thenReturn(10);
            SimpleBooleanProperty s_boolean=new SimpleBooleanProperty();
            SuggestPromotionControllerBase promotionHandler=new SuggestPromotionControllerBase("Promozione",sellerPriceDao,promotionsSuggestService,promotionDao,s_boolean);
            promotionHandler.execute();
        });
        robot.sleep(40000);
*/





    }

