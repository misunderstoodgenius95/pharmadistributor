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
import pharma.dao.DetailDao;
import pharma.dao.FarmacoDao;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class SuggestPriceTest {
    @Mock
    private DetailDao detailDao;
    @Mock
    private FarmacoDao farmacoDao;
 @Start
 public void start(Stage stage) throws IOException {
     MockitoAnnotations.openMocks(this);
     FXMLLoader loader = new  FXMLLoader(getClass().getResource("/subpanel/suggest_price.fxml"));
        SuggestPrice suggestPrice=new SuggestPrice(farmacoDao,detailDao);
        loader.setController(suggestPrice);
     Scene scene=new Scene(loader.load());
     stage.setScene(scene);
     stage.initStyle(StageStyle.DECORATED);
     stage.show();
 }
 @Test
    public void test(FxRobot robot){
     when(detailDao.findAll()).thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().setNome("Antibiotico").build()));
     when(farmacoDao.findByName(anyString())).thenReturn(List.of( FieldData.FieldDataBuilder.getbuilder().setNome("Tachipirina").setDescription("Farmaco per la febbre.").setCategoria(1).
             setNome_tipologia("Supposte").setUnit_misure("100mg").setNome_categoria("AntiInfiammatorio").setQuantity(30).setNome_casa_farmaceutica("Angelini").setNome_principio_attivo("Paracetamolo").build()));
     Platform.runLater(()->{




     });

     robot.sleep(4000000);
 }




}