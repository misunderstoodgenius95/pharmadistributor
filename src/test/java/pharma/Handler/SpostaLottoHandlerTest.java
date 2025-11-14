package pharma.Handler;

import javafx.application.Platform;
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
import pharma.dao.LotAssigmentDao;
import pharma.dao.LotAssigmentShelvesDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class SpostaLottoHandlerTest {
    @Mock
    private LotAssigmentDao lotAssigmentDao;
    @Mock
    private LotAssigmentShelvesDao assigmentShelvesDao;





    @Start
    public void start(Stage stage){
        MockitoAnnotations.openMocks(this);
    VBox vbox=new VBox();
    Scene scene=new Scene(vbox);
    stage.setScene(scene);
    stage.show();
    }

    @Test
    public void test(FxRobot robot){
        when(assigmentShelvesDao.findbyLotCode(anyString())).thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().setcode("ax22").setNome("Magazzino1").
                setShelf_code("s11").setShelves_code(1).setQuantity(100).build()));

        Platform.runLater(()->{
         /*   SpostaLottoHandler spostaLottoHandler=new SpostaLottoHandler("Sposta Lotto",lotAssigmentDao,assigmentShelvesDao);
                spostaLottoHandler.show();*/

        });
        robot.sleep(40000);

}



}