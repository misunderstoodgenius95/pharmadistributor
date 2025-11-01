package pharma.Handler;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
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
import pharma.dao.SellerPriceDao;
import pharma.formula.PriceSuggestion;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class SuggestPriceTest {
    @Mock
    private SellerPriceDao s_dao;
    @Mock
    private PriceSuggestion suggestion;
    @Start
    public  void start(Stage stage){
        Scene scene=new Scene(new VBox());
        stage.setScene(scene);
        stage.show();
        MockitoAnnotations.openMocks(this);



    }
    @Test
    public void test(FxRobot robot){
        SimpleBooleanProperty simpleBooleanProperty=new SimpleBooleanProperty();
       when(s_dao.findNotExistPrice()).
                thenReturn(Arrays.asList(
                        FieldData.FieldDataBuilder.getbuilder().setNome("Tachipirina").setNome_tipologia("Supposta").setUnit_misure("100mg").setQuantity(100).setId(340).build() ));
        Platform.runLater(()->{
          when(suggestion.suggest_price(anyInt())).thenReturn(10.33);
            SuggestPriceHandler suggestPriceHandler =new SuggestPriceHandler("Inserisci Prezzo",s_dao,suggestion,simpleBooleanProperty);
            suggestPriceHandler.execute();

        });
        robot.sleep(40000);



    }





}