package pharma.Handler;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import pharma.config.SimulateEvents;
import pharma.config.Utility;
import pharma.dao.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class PurchaseCreditNoteHandlerTest {
    private  PurchaseCreditNoteHandler purchaseCreditNoteHandler;
    @Mock
    private Database database;
    private ObservableList<FieldData> obs_table;
    private PurchaseOrderDao purchaseOrderDao;
    private PurchaseInvoiceDao purchaseInvoiceDao;
    private PurchaseOrderDetailDao p_detail;
    private  ObservableList<FieldData> obs_table_fd_order;
    private  ObservableList<FieldData>obs_table_fd_details;
    @Mock
    private ResultSet resultSet_invoice;
    @Mock
    private  ResultSet resultSet_order;
    @Mock
    private ResultSet resultSet_details;
    private FarmacoDao farmacoDao;
    private PurchaseCreditNoteDao purchaseCreditNoteDao;
    @Mock
    private  ResultSet resultset_invoicebyid;
    @Mock
    private  PreparedStatement p_invoicebyid;
    @Mock
    private  PreparedStatement ps_detail;
    @Mock
    private  ResultSet resultSet_insert_c_n;
    @Mock
    private  PreparedStatement p_statement_insert_c_n;





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


        when(database.execute_prepared_query(Mockito.anyString())).thenReturn(p_invoicebyid,ps_detail,ps_detail,p_statement_insert_c_n);






        // Order by  Invoicebyid
        ArgumentCaptor<Integer> paramCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> paramCaptor_order = ArgumentCaptor.forClass(Integer.class);


        when(p_invoicebyid.executeQuery()).thenAnswer(invocation -> {
            int value = paramCaptor.getValue();
            System.out.println("value"+value);
            if (value == 1) {
                when(resultset_invoicebyid.next()).thenReturn(true, true,false);
                when(resultset_invoicebyid.getInt(1)).thenReturn(100,102);
                when(resultset_invoicebyid.getDate(2)).thenReturn(
                        Date.valueOf(LocalDate.of(2024, 6, 10)),
                        Date.valueOf(LocalDate.of(2024, 6, 10))
                );
                when(resultset_invoicebyid.getDouble(3)).thenReturn(42.6,22.11);
                when(resultset_invoicebyid.getDouble(4)).thenReturn(3.05,1.1);
                when(resultset_invoicebyid.getDouble(5)).thenReturn(45.65,23.2);
                when(resultset_invoicebyid.getString(6)).thenReturn("1600","1700");
                when(resultset_invoicebyid.getString(7)).thenReturn("Angelini","Angelini");
                when(resultset_invoicebyid.getInt(8)).thenReturn(1,1);
            }

            return resultset_invoicebyid;
        });



// Details
doNothing().when(p_invoicebyid).setInt(eq(1), paramCaptor.capture());

        when(ps_detail.executeQuery()).thenAnswer(invocation -> {
            int  orderId=paramCaptor_order.getValue();

            // Ensure order ID exists before mocking result set
            if (orderId == 100) {
                when(resultSet_details.next()).thenReturn(true,true, false);
                when(resultSet_details.getInt(1)).thenReturn(1, 2); // Item ID
                when(resultSet_details.getString(2)).thenReturn("aaa", "bbb");
                when(resultSet_details.getInt("farmaco")).thenReturn(1, 2);
                when(resultSet_details.getInt("purchase_order")).thenReturn(100, 100);
                when(resultSet_details.getDouble("price")).thenReturn(10.1, 11.2);
                when(resultSet_details.getInt("quantity")).thenReturn(2, 2);
                when(resultSet_details.getInt("vat_percent")).thenReturn(4, 10);
                when(resultSet_details.getString("nome")).thenReturn("Tachipirina");
                when(resultSet_details.getString("tipologia")).thenReturn("Compresse");
                when(resultSet_details.getString("misura")).thenReturn("100 mg", "10mg");
            }

            if(orderId==102){

                when(resultSet_details.next()).thenReturn(true, true, false);
                when(resultSet_details.getInt(1)).thenReturn(1, 2); // Item ID
                when(resultSet_details.getString(2)).thenReturn("ccc", "dddd");
                when(resultSet_details.getInt("farmaco")).thenReturn(1, 2);
                when(resultSet_details.getInt("purchase_order")).thenReturn(102, 102);
                when(resultSet_details.getDouble("price")).thenReturn(10.1, 11.2);
                when(resultSet_details.getInt("quantity")).thenReturn(2, 11);
                when(resultSet_details.getInt("vat_percent")).thenReturn(4, 10);
                when(resultSet_details.getString("nome")).thenReturn("Tachipirina");
                when(resultSet_details.getString("tipologia")).thenReturn("Compresse");
                when(resultSet_details.getString("misura")).thenReturn("100 mg", "10mg");
            }
            return resultSet_details;
        });


        doNothing().when(ps_detail).setInt(anyInt(),paramCaptor_order.capture());





    }


    public void Validation(FxRobot robot) throws InterruptedException {

        Platform.runLater(() -> {
                    FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().
                            setInvoice_number("p1000").
                            setId(1).
                            setProduction_date(Date.valueOf(LocalDate.of(2025, 10, 1))).
                            setPayment_mode("BONIFICO_BANCARIO").
                            setSubtotal(10.1).
                            setVat_amount(4.1).
                            setTotal(14.2).setNome_casa_farmaceutica("Angelini").build();
                    purchaseCreditNoteHandler = new PurchaseCreditNoteHandler("Aggiungi Nota di Credito", fieldData, Arrays.asList(purchaseOrderDao, p_detail, purchaseCreditNoteDao));

                    DatePicker datePicker = Utility.extract_value_from_list(purchaseCreditNoteHandler.getControlList(), DatePicker.class).getFirst();
                    List<TextField> textFields = Utility.extract_value_from_list(purchaseCreditNoteHandler.getControlList(), TextField.class);
                    textFields.getFirst().setText("1000");
                    textFields.get(1).setText("Decremento");
                    datePicker.setValue(LocalDate.of(2024, 10, 1));

                       /* Platform.runLater(()-> SimulateEvents.clickOn(purchaseCreditNoteHandler.getButtonOK()));*/
                    purchaseCreditNoteHandler.showAndWait().ifPresent(fieldData_insert -> {
                        System.out.println("execute");


                        Assertions.assertEquals("Decremento", fieldData_insert.getMotive());
                    });
                });







          robot.sleep(3600000);

    }
    @Test
    public void ValidationInsert(FxRobot robot) throws InterruptedException {

        Platform.runLater(() -> {
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().
                    setInvoice_number("p1000").
                    setId(1).
                    setProduction_date(Date.valueOf(LocalDate.of(2025, 10, 1))).
                    setPayment_mode("BONIFICO_BANCARIO").
                    setSubtotal(10.1).
                    setVat_amount(4.1).
                    setTotal(14.2).setNome_casa_farmaceutica("Angelini").build();
            purchaseCreditNoteHandler = new PurchaseCreditNoteHandler("Aggiungi Nota di Credito", fieldData, Arrays.asList(purchaseOrderDao, p_detail, purchaseCreditNoteDao));

            DatePicker datePicker = Utility.extract_value_from_list(purchaseCreditNoteHandler.getControlList(), DatePicker.class).getFirst();
            List<TextField> textFields = Utility.extract_value_from_list(purchaseCreditNoteHandler.getControlList(), TextField.class);
            textFields.getFirst().setText("1000");
            textFields.get(1).setText("Decremento");
            datePicker.setValue(LocalDate.of(2024, 10, 1));


           //Platform.runLater(()-> SimulateEvents.clickOn(purchaseCreditNoteHandler.getButtonOK()));
            try {
                when(resultSet_insert_c_n.getInt(1)).thenReturn(100);
                when(resultSet_insert_c_n.next()).thenReturn(true,false);
                when(p_statement_insert_c_n.executeQuery()).thenReturn(resultSet_insert_c_n);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            purchaseCreditNoteHandler.execute();


        });





         robot.sleep(100000);
        //  robot.sleep(3600000);

    }


    @Test
    public void ValidationInsertSingle(FxRobot robot) throws InterruptedException {

        Platform.runLater(() -> {
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().
                    setInvoice_number("p1000").
                    setId(1).
                    setProduction_date(Date.valueOf(LocalDate.of(2025, 10, 1))).
                    setPayment_mode("BONIFICO_BANCARIO").
                    setSubtotal(10.1).
                    setVat_amount(4.1).
                    setTotal(14.2).setNome_casa_farmaceutica("Angelini").build();
            PurchaseCreditNoteHandlerSingle purchaseCreditNoteHandlerSingle = new PurchaseCreditNoteHandlerSingle("Aggiungi Nota di Credito", fieldData, Arrays.asList(purchaseOrderDao, p_detail, purchaseCreditNoteDao));

            DatePicker datePicker = Utility.extract_value_from_list(purchaseCreditNoteHandlerSingle.getControlList(), DatePicker.class).getFirst();
            List<TextField> textFields = Utility.extract_value_from_list(purchaseCreditNoteHandlerSingle.getControlList(), TextField.class);
            textFields.getFirst().setText("1000");
            textFields.get(1).setText("Decremento");
            datePicker.setValue(LocalDate.of(2024, 10, 1));


            //Platform.runLater(()-> SimulateEvents.clickOn(purchaseCreditNoteHandler.getButtonOK()));
            try {
                when(resultSet_insert_c_n.getInt(1)).thenReturn(100);
                when(resultSet_insert_c_n.next()).thenReturn(true,false);
                when(p_statement_insert_c_n.executeQuery()).thenReturn(resultSet_insert_c_n);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
       purchaseCreditNoteHandlerSingle.execute();


        });





        robot.sleep(100000);
        //  robot.sleep(3600000);

    }




}