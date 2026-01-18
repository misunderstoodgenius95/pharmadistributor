package pharma.DialogController;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.Utility;
import pharma.config.database.Database;
import pharma.dao.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class PurchaseCreditNoteHandlerTest {
    private PurchaseOrderDao purchaseOrderDao;
    private PurchaseInvoiceDao purchaseInvoiceDao;
    private PurchaseOrderDetailDao p_detail;
    private PurchaseCreditNoteDao purchaseCreditNoteDao;
    @Mock
    private Database database;
    @Mock
    private ResultSet resultSet;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private PreparedStatement preparedStatement_details;
    @Mock
    private PreparedStatement preparedStatement_c_detail;
    @Mock
    private ResultSet resultSet_insert_c_n;
    @Mock
    private PreparedStatement p_statement_insert_c_n;
    private PurchaseCreditNoteDetailDao purchaseCreditNoteDetail;

    @Start
    public void start(Stage stage) {
        MockitoAnnotations.openMocks(this);
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        purchaseOrderDao = new PurchaseOrderDao(database);
        p_detail = new PurchaseOrderDetailDao(database);
        purchaseCreditNoteDao = new PurchaseCreditNoteDao(database);
        purchaseCreditNoteDetail = new PurchaseCreditNoteDetailDao(database);

    }

    @BeforeEach
    public void setUp() throws SQLException {
        ArgumentCaptor<Integer> paramCaptor_order = ArgumentCaptor.forClass(Integer.class);

        when(database.execute_prepared_query(anyString())).thenReturn(preparedStatement, p_statement_insert_c_n, preparedStatement_details);
        when(preparedStatement.executeQuery()).thenAnswer(invocation -> {
            int orderId = paramCaptor_order.getValue();

            // Ensure order ID exists before mocking result set
            if (orderId == 100) {
                when(resultSet.next()).thenReturn(true, true, false);
                when(resultSet.getInt(1)).thenReturn(1, 2); // Item ID
                when(resultSet.getString(2)).thenReturn("aaa", "bbb");
                when(resultSet.getInt("farmaco")).thenReturn(1, 2);
                when(resultSet.getInt("purchase_order")).thenReturn(100, 100);
                when(resultSet.getDouble("price")).thenReturn(10.1, 11.2);
                when(resultSet.getInt("quantity")).thenReturn(2, 2);
                when(resultSet.getInt("vat_percent")).thenReturn(4, 10);
                when(resultSet.getString("nome")).thenReturn("Tachipirina");
                when(resultSet.getString("tipologia")).thenReturn("Compresse");
                when(resultSet.getString("misura")).thenReturn("100 mg", "10mg");
            }

            if (orderId == 102) {

                when(resultSet.next()).thenReturn(true, true, false);
                when(resultSet.getInt(1)).thenReturn(1, 2); // Item ID
                when(resultSet.getString(2)).thenReturn("ccc", "dddd");
                when(resultSet.getInt("farmaco")).thenReturn(1, 2);
                when(resultSet.getInt("purchase_order")).thenReturn(102, 102);
                when(resultSet.getDouble("price")).thenReturn(10.1, 11.2);
                when(resultSet.getInt("quantity")).thenReturn(2, 11);
                when(resultSet.getInt("vat_percent")).thenReturn(4, 10);
                when(resultSet.getString("nome")).thenReturn("Tachipirina");
                when(resultSet.getString("tipologia")).thenReturn("Compresse");
                when(resultSet.getString("misura")).thenReturn("100 mg", "10mg");
            }
            return resultSet;
        });


        doNothing().when(preparedStatement).setInt(anyInt(), paramCaptor_order.capture());


    }


    @Test
    public void ValidationInsertSingle(FxRobot robot) throws InterruptedException {

        Platform.runLater(() -> {
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().
                    setInvoice_number("p1000").
                    setId(1).
                    setOrder_id(100).
                    setProduction_date(Date.valueOf(LocalDate.of(2025, 10, 1))).
                    setPayment_mode("BONIFICO_BANCARIO").
                    setSubtotal(42.60).
                    setVat_amount(3.05).
                    setTotal(45.65).setCasa_Farmaceutica(1).build();
                    PurchaseCreditNoteControllerBase purchaseCreditNoteHandler =
                    new PurchaseCreditNoteControllerBase("Aggiungi Nota di Credito", fieldData, Arrays.asList(p_detail, purchaseCreditNoteDao, purchaseCreditNoteDetail));

            DatePicker datePicker = Utility.extract_value_from_list(purchaseCreditNoteHandler.getControlList(), DatePicker.class).getFirst();
            List<TextField> textFields = Utility.extract_value_from_list(purchaseCreditNoteHandler.getControlList(), TextField.class);
            textFields.getFirst().setText("1000");
            textFields.get(1).setText("Decremento");
            datePicker.setValue(LocalDate.of(2025, 10, 1));


           // Platform.runLater(() -> SimulateEvents.clickOn(purchaseCreditNoteHandler.getButtonOK()));
            try {
                when(resultSet_insert_c_n.getInt(1)).thenReturn(200);
                when(resultSet_insert_c_n.next()).thenReturn(true, false);
                when(p_statement_insert_c_n.executeQuery()).thenReturn(resultSet_insert_c_n);
                when(preparedStatement_details.executeUpdate()).thenReturn(1, 1);



            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
/*

   statement.setInt(1,entity.getInvoice_id());
        statement.setInt(2,entity.getPurchase_order_id());
        statement.setInt(3,entity.getQuantity());
        statement.setDouble(4,entity.getPrice());
        statement.setInt(5,entity.getVat_percent());
 */

            purchaseCreditNoteHandler.execute();
            try {
                verify(p_statement_insert_c_n).setString(1, "1000");
                verify(p_statement_insert_c_n).setInt(2, 1);
                verify(p_statement_insert_c_n).setDate(3, Date.valueOf(LocalDate.of(2025, 10, 1)));
                verify(p_statement_insert_c_n).setString(4, "Decremento");
                verify(p_statement_insert_c_n).setInt(5, 1);
                verify(p_statement_insert_c_n).setDouble(6, 42.599999999999994d);
                verify(p_statement_insert_c_n).setDouble(7, 3.0479999999999996d);
                verify(p_statement_insert_c_n).setDouble(8, 45.647999999999996d);

                verify(preparedStatement_details,times(2)).setInt(1,200);
                verify(preparedStatement_details,times(2)).setInt(2,1);
                verify(preparedStatement_details).setInt(3,2);
                verify(preparedStatement_details).setDouble(4,10.1);
                verify(preparedStatement_details).setInt(5,4);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        });


        robot.sleep(100000);
        //  robot.sleep(3600000);

    }

    @AfterEach
    public void teardown() {
        purchaseCreditNoteDao = null;
        purchaseCreditNoteDetail = null;

    }

    @Test
    public void IntegrationTest(FxRobot robot) {
        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        purchaseCreditNoteDao = new PurchaseCreditNoteDao(Database.getInstance(properties));
        purchaseCreditNoteDetail = new PurchaseCreditNoteDetailDao(Database.getInstance(properties));

        Platform.runLater(() -> {
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().
                    setInvoice_number("p1000").
                    setId(7).
                    setOrder_id(100).
                    setProduction_date(Date.valueOf(LocalDate.of(2025, 10, 1))).
                    setPayment_mode("BONIFICO_BANCARIO").
                    setSubtotal(42.60).
                    setVat_amount(3.05).
                    setTotal(45.65).setCasa_Farmaceutica(1).build();
            PurchaseCreditNoteControllerBase purchaseCreditNoteHandler =
                    new PurchaseCreditNoteControllerBase("Aggiungi Nota di Credito", fieldData, Arrays.asList(p_detail, purchaseCreditNoteDao, purchaseCreditNoteDetail));

            DatePicker datePicker = Utility.extract_value_from_list(purchaseCreditNoteHandler.getControlList(), DatePicker.class).getFirst();
            List<TextField> textFields = Utility.extract_value_from_list(purchaseCreditNoteHandler.getControlList(), TextField.class);
            textFields.getFirst().setText("1000");
            textFields.get(1).setText("Decremento");
            datePicker.setValue(LocalDate.of(2025, 10, 1));
            purchaseCreditNoteHandler.execute();


        });
        robot.sleep(40000);



    }
}