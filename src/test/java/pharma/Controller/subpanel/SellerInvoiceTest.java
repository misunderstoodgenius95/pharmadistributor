package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.dao.SellerInvoiceDao;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
class SellerInvoiceTest {
    @Mock
    private SellerInvoiceDao s_invoice_dao;

    @Start
    public void start (Stage stage) throws IOException {
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/subpanel/sellerInvoice.fxml"));
        SellerInvoice sellerInvoice=new SellerInvoice(s_invoice_dao);
        loader.setController(sellerInvoice);
        Scene scene=new Scene(loader.load());
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();



    }



   @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{

        });
        robot.sleep(4000000);
    }

}