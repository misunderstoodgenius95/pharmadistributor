package pharma.Handler;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.config.Database;
import pharma.config.Utility;
import pharma.dao.PurchaseCreditNoteDao;
import pharma.dao.PurchaseInvoiceDao;
import pharma.dao.PurchaseOrderDao;
import pharma.dao.PurchaseOrderDetailDao;

import javax.xml.crypto.Data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class PurchaseCreditNoteHandlerSingleTest {
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
    @Start
    public void start(Stage stage){
        MockitoAnnotations.openMocks(this);
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        purchaseOrderDao=new PurchaseOrderDao(database);
        p_detail=new PurchaseOrderDetailDao(database);
        purchaseCreditNoteDao=new PurchaseCreditNoteDao(database);

    }
    @BeforeEach
    public void setUp() throws SQLException {
        ArgumentCaptor<Integer> paramCaptor_order = ArgumentCaptor.forClass(Integer.class);

            when(database.execute_prepared_query(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenAnswer(invocation -> {
            int  orderId=paramCaptor_order.getValue();

            // Ensure order ID exists before mocking result set
            if (orderId == 100) {
                when(resultSet.next()).thenReturn(true,true, false);
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

            if(orderId==102){

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


        doNothing().when(preparedStatement).setInt(anyInt(),paramCaptor_order.capture());



    }




    @Test
    public void ValidationInsertSingle(FxRobot robot) throws InterruptedException {

        Platform.runLater(() -> {
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().
                    setInvoice_number("p1000").
                    setId(1).
                    setPurchase_order_id(100).
                    setProduction_date(Date.valueOf(LocalDate.of(2025, 10, 1))).
                    setPayment_mode("BONIFICO_BANCARIO").
                    setSubtotal(42.60).
                    setVat_amount(3.05).
                    setTotal(45.65).setNome_casa_farmaceutica("Angelini").build();
            PurchaseCreditNoteHandlerSingle purchaseCreditNoteHandlerSingle = new PurchaseCreditNoteHandlerSingle("Aggiungi Nota di Credito", fieldData, Arrays.asList( p_detail, purchaseCreditNoteDao));

            DatePicker datePicker = Utility.extract_value_from_list(purchaseCreditNoteHandlerSingle.getControlList(), DatePicker.class).getFirst();
            List<TextField> textFields = Utility.extract_value_from_list(purchaseCreditNoteHandlerSingle.getControlList(), TextField.class);
            textFields.getFirst().setText("1000");
            textFields.get(1).setText("Decremento");
            datePicker.setValue(LocalDate.of(2024, 10, 1));


            //Platform.runLater(()-> SimulateEvents.clickOn(purchaseCreditNoteHandler.getButtonOK()));
           /* try {
                when(resultSet_insert_c_n.getInt(1)).thenReturn(100);
                when(resultSet_insert_c_n.next()).thenReturn(true,false);
                when(p_statement_insert_c_n.executeQuery()).thenReturn(resultSet_insert_c_n);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }*/
            purchaseCreditNoteHandlerSingle.execute();


        });





        robot.sleep(100000);
        //  robot.sleep(3600000);

    }




}