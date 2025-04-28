package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.config.database.Database;

import java.io.IOException;
import java.sql.SQLException;

@ExtendWith(ApplicationExtension.class) //
class PurchaseInvoiceTest {


    private FXMLLoader loader;
    @Mock
private Database databaseMock;



    @BeforeEach
    public void setUp() throws SQLException, IOException {





    }


    @Start
    public void start(Stage primaryStage) throws IOException, SQLException {



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/subpanel/purchase_invoice.fxml"));
        Parent root = loader.load();




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