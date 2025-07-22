package pharma.Handler;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
import pharma.config.database.Database;
import pharma.javafxlib.Controls.TextFieldComboBox;
import pharma.config.Utility;
import pharma.dao.PharmaDao;
import pharma.dao.PurchaseInvoiceDao;
import pharma.dao.PurchaseOrderDao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class PurchaseInvoiceHandlerTest{
    @Mock
    private Database database;
    private PharmaDao pharmaDao;
    @Mock
    private ResultSet resultSet_order;
    @Mock
    private  PreparedStatement p_update;
    @Mock
    private  PreparedStatement p_query;
    @Mock
    private PreparedStatement p_insert;
    @Mock
    private  ResultSet resultSet_pharma;
    private PurchaseInvoiceDao inv_dao;
    private  PurchaseInvoiceHandler purchaseInvoiceHandler;
    private PurchaseOrderDao purchaseOrderDao;
    @Mock
    private  ResultSet resultSet_insert;
    @Start
    public  void start(Stage stage){
        VBox vBox=new VBox();
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }
    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        purchaseOrderDao=new PurchaseOrderDao(database);
        pharmaDao=new PharmaDao(database);
        inv_dao =new PurchaseInvoiceDao(database);


// Mock on Order



    }

    @Test
    public void test(FxRobot robot) throws SQLException {
        when(resultSet_pharma.next()).thenReturn(true,true,true,false);
        when(resultSet_pharma.getInt(1)).thenReturn(1,2,3);
        when(resultSet_pharma.getString(2)).thenReturn("Angelini","Melarini","Bayer");
        when(database.executeQuery(anyString())).thenReturn(resultSet_pharma);
        ArgumentCaptor<Integer> argumentCaptor=ArgumentCaptor.forClass(Integer.class);
        when(database.execute_prepared_query(anyString())).thenReturn(p_query);
        when(p_query.executeQuery()).thenAnswer(invocation -> {
            if (argumentCaptor.getValue()== 1) {


                when(resultSet_order.next()).thenReturn(true, true, false);
                when(resultSet_order.getInt(1)).thenReturn(100, 300);
                when(resultSet_order.getDate(2)).thenReturn(Date.valueOf(LocalDate.of(2024, 6, 10)),
                        Date.valueOf(LocalDate.of(2021, 6, 10)));
                when(resultSet_order.getDouble(3)).thenReturn(10.1, 11.2);
                when(resultSet_order.getDouble(4)).thenReturn(1.0, 2.1);
                when(resultSet_order.getDouble(5)).thenReturn(11.1, 10.23);
                when(resultSet_order.getString(6)).thenReturn("1600", "1700");
                when(resultSet_order.getInt(7)).thenReturn(1, 1);
                when(resultSet_order.getString(8)).thenReturn("Angelini", "Angelini");

            }else if(argumentCaptor.getValue()==2){

                when(resultSet_order.next()).thenReturn( true, false);
                when(resultSet_order.getInt(1)).thenReturn( 200);
                when(resultSet_order.getDate(2)).thenReturn(
                        Date.valueOf(LocalDate.of(2021, 6, 10)));
                when(resultSet_order.getDouble(3)).thenReturn( 11.2);
                when(resultSet_order.getDouble(4)).thenReturn( 2.1);
                when(resultSet_order.getDouble(5)).thenReturn(10.23);
                when(resultSet_order.getString(6)).thenReturn( "1700");
                when(resultSet_order.getInt(7)).thenReturn(2);
                when(resultSet_order.getString(8)).thenReturn("Bayer");
            }


            return  resultSet_order;
        });
        doNothing().when(p_query).setInt(anyInt(),argumentCaptor.capture());


        //Order
/*        when(resultSet_order.next()).thenReturn(true,true,true,false);
        when(resultSet_order.getInt(1)).thenReturn(1000,2000,3000);
        when(resultSet_order.getDate(2)).thenReturn(
                Date.valueOf(LocalDate.of(2025,10,1)),
                Date.valueOf(LocalDate.of(2024,04,04)),
                Date.valueOf(LocalDate.of(2023,1,11)));
        when(resultSet_order.getDouble(3)).thenReturn(10.2,15.5,11.5);
        when(resultSet_order.getDouble(4)).thenReturn(1.2,2.1,3.0);
        when(resultSet_order.getDouble(5)).thenReturn(11.4,17.6,14.5);
        when(resultSet_order.getString(6)).thenReturn("p100","p3330","ak4");
        when(resultSet_order.getInt(7)).thenReturn(1,2,3);
        when(resultSet_order.getString(8)).thenReturn("Angelini","Angelini,","Bayer");
        when(database.executeQuery(anyString())).thenReturn(resultSet_order,resultSet_pharma);*/
        SimpleBooleanProperty simpleBooleanProperty=new SimpleBooleanProperty(false);
// Mock on pharma


        Platform.runLater(()-> {
            purchaseInvoiceHandler = new PurchaseInvoiceHandler(pharmaDao, inv_dao,purchaseOrderDao,simpleBooleanProperty);

                    DatePicker datePicker = Utility.extract_value_from_list(purchaseInvoiceHandler.getControlList(), DatePicker.class).getFirst();
                    datePicker.setValue(LocalDate.of(2025, 10, 1));
                    TextField textField = Utility.extract_value_from_list(purchaseInvoiceHandler.getControlList(), TextField.class).getFirst();
                    textField.setText("a1000");
                    TextFieldComboBox<String> textFieldComboBox = Utility.extract_value_from_list(purchaseInvoiceHandler.getControlList(), TextFieldComboBox.class).getFirst();
                    textFieldComboBox.getChoiceBox().setValue(textFieldComboBox.getChoiceBox().getItems().getFirst());
                    purchaseInvoiceHandler.showAndWait().ifPresent(fieldData -> {
                        Assertions.assertEquals(Date.valueOf(LocalDate.of(2025, 10, 1)), fieldData.getProduction_date());
                        Assertions.assertEquals("a1000", fieldData.getInvoice_number());
                        Assertions.assertEquals("Bonifico_Bancario", fieldData.getPayment_mode());
                        System.out.println("valori");
                        System.out.println(fieldData.getSubtotal());
                        System.out.println(fieldData.getVat_amount());
                        System.out.println(fieldData.getTotal());
                        System.out.println(fieldData.getFieldDataList().getFirst().getId());


                    });
                });




        robot.sleep(40000000);
}

    @Test
    public void ValidTransaction() throws Exception {

        Platform.runLater(()-> {
            try {
    when(resultSet_insert.getInt(1)).thenReturn(100);
    when(resultSet_insert.next()).thenReturn(true);
    when(p_insert.executeQuery()).thenReturn(resultSet_insert);
    when(database.execute_prepared_query(anyString())).thenReturn(p_insert,p_update);


    Mockito.when(p_update.executeUpdate()).thenReturn(1, 2);




    FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().
            setInvoice_number("p1000").
            setProduction_date(Date.valueOf(LocalDate.of(2025, 10, 1))).
            setPayment_mode("BONIFICO_BANCARIO").
            setSubtotal(10.1).
            setVat_amount(4.1).
            setTotal(14.2).setFieldDataListAll(Arrays.asList(FieldData.FieldDataBuilder.getbuilder().setOrder_id(10).build(),
                    FieldData.FieldDataBuilder.getbuilder().setOrder_id(11).build(),
                    FieldData.FieldDataBuilder.getbuilder().setOrder_id(12).build())).build();

    purchaseInvoiceHandler = new PurchaseInvoiceHandler(inv_dao,purchaseOrderDao);

                boolean value=purchaseInvoiceHandler.condition_event(fieldData);
                Assertions.assertTrue(value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    @Test
    public void InValidTransactionError() throws Exception {

        Platform.runLater(()-> {
            try {
                when(resultSet_insert.getInt(1)).thenReturn(-1);
                when(resultSet_insert.next()).thenReturn(true);
                when(p_insert.executeQuery()).thenReturn(resultSet_insert);
                when(database.execute_prepared_query(anyString())).thenReturn(p_insert,p_update);


                Mockito.when(p_update.executeUpdate()).thenReturn(1, 2);




                FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().
                        setInvoice_number("p1000").
                        setProduction_date(Date.valueOf(LocalDate.of(2025, 10, 1))).
                        setPayment_mode("BONIFICO_BANCARIO").
                        setSubtotal(10.1).
                        setVat_amount(4.1).
                        setTotal(14.2).setFieldDataListAll(Arrays.asList(FieldData.FieldDataBuilder.getbuilder().setOrder_id(10).build(),
                                FieldData.FieldDataBuilder.getbuilder().setOrder_id(11).build(),
                                FieldData.FieldDataBuilder.getbuilder().setOrder_id(12).build())).build();

                purchaseInvoiceHandler = new PurchaseInvoiceHandler(inv_dao,purchaseOrderDao);

                boolean value=purchaseInvoiceHandler.condition_event(fieldData);
                Assertions.assertTrue(value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}