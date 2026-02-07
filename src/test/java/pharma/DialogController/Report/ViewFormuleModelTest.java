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
import pharma.Service.Report.UserFormula;
import pharma.dao.CustomFormulaDao;
import pharma.dao.PurchaseOrderDao;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ViewFormuleModelTest {
    @Mock
    private CustomFormulaDao customFormulaDao;
    @Mock
    private PurchaseOrderDao orderDao;
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
        when(customFormulaDao.findAll()).thenReturn(List.of(
                new UserFormula("somma_totale","somma(totale_ordini,iva_ordini)"),
                new UserFormula("moltiplicazione_totale","moltiplicazione(somma(totale_ordini),2)")));
        when(orderDao.findBySumAggregate(Mockito.anyString())).thenReturn(10.22);
        when(orderDao.findByValue(Mockito.anyString())).thenReturn(List.of(19.11,22.11));
        Platform.runLater(()->{
        ViewFormuleModel viewFormuleModel=new ViewFormuleModel("",customFormulaDao,orderDao);
        viewFormuleModel.show();




        });
        robot.sleep(500000);



    }




}