package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.config.database.Database;
import pharma.dao.LottiDao;
import pharma.dao.PurchaseOrderDao;
import pharma.dao.PurchaseOrderDetailDao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ExtendWith(ApplicationExtension.class) //
class OrdiniTest {


    private FXMLLoader loader;
    @Mock
private Database databaseMock;

     private PurchaseOrderDao purchaseOrderDao;
     private PurchaseOrderDetailDao p_detail;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
  private ResultSet resultSet;
    @Mock
    private LottiDao lottiDao;
  private Ordini ordini;


    @BeforeEach
    public void setUp() throws SQLException, IOException {
        // ordini=new Ordini(purchaseOrderDao,p_detail,lottiDao);




    }


    @Start
    public void start(Stage primaryStage) throws IOException, SQLException {



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/subpanel/ordini.fxml"));
        Parent root = loader.load();
        ordini = loader.getController();

        // Get controller

        // Inject dependencies BEFORE the UI initializes


        // Clear table before showing UI
//        ordini.table_id.getItems().clear();
//
//        FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().
//                setProduction_date(Date.valueOf(LocalDate.of(2025,10,01))).setSubtotal(10.2).setVat_amount(4.1).setTotal(12.1).build();
//ordini.table_id.getItems().add(fieldData);
//FieldData f_expand=FieldData.FieldDataBuilder.getbuilder().setLotto_id("aa").setCategoria(1).setPrice(10.0).setQuantity(100).setVat_percent(7).build();
        primaryStage.initStyle(StageStyle.DECORATED);
        // Show the stage
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }




    @Test
    public void test(FxRobot robot) {

        Platform.runLater(() -> {

        });

  robot.sleep(400000000);
    }
}