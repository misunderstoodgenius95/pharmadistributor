package pharma.Controller.subpanel;

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
import pharma.Controller.Admin;
import pharma.Model.FieldData;
import pharma.config.Utility;
import pharma.dao.DetailDao;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class SuggestPurchaseTest {
    @Mock
            private DetailDao detailDao;
    SuggestPurchase suggestPurchase;
    @Start
    public void start(Stage primaryStage) throws IOException {
        MockitoAnnotations.openMocks(this);
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/subpanel/suggest_purchase.fxml"));
            detailDao.setTable_name(Utility.Categoria);
        when(detailDao.findAll()).thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().setId(1).setNome("AntiDepressivo").build(),
                        FieldData.FieldDataBuilder.getbuilder().setId(2).setNome("Antibiotici").build()));
      suggestPurchase=new SuggestPurchase(detailDao);
        loader.setController(suggestPurchase);
        Scene scene = new Scene(loader.load());


        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();
    }

    @Test
    public void test(FxRobot robot){

        robot.sleep(900000);


    }

}