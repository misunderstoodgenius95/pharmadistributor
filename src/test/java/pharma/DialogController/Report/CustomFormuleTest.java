package pharma.DialogController.Report;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.dao.CustomFormulaDao;
import pharma.dao.PurchaseOrderDao;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class CustomFormuleTest {
    @Mock
    private PurchaseOrderDao purchaseOrderDao;
    @Mock
    private CustomFormulaDao customFormulaDao;
    @Start
    public void start(Stage stage){
        MockitoAnnotations.openMocks(this);
        Scene scene=new Scene(new VBox(),900,900);
        stage.setScene(scene);
        stage.setScene(scene);
        stage.show();

    }
    @Test
    public void test(FxRobot robot){
        when(purchaseOrderDao.findBySumAggregate(Mockito.anyString())).thenReturn(10.22);
        when(purchaseOrderDao.findByValue(Mockito.anyString())).thenReturn(List.of(19.11,22.11));
        Platform.runLater(()->{
ViewFormule viewFormule =new ViewFormule("",customFormulaDao,purchaseOrderDao);
         viewFormule.show();



        });
        robot.sleep(50000000);


    }









}