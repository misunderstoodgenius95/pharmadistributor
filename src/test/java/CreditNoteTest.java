import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableRowExpanderColumn;
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
import pharma.Handler.PurchaseCreditNoteHandler;
import pharma.Model.FieldData;
import pharma.config.database.Database;
import pharma.javafxlib.DoubleClick_Menu;
import pharma.config.TableUtility;
import pharma.dao.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class CreditNoteTest {
    private  TableView<FieldData> table_id;
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
    @Mock
    private  PreparedStatement preparedStatement;
    @Mock
    private  ResultSet resultset_invoicebyid;
    @Mock
    private  PreparedStatement p_invoicebyid;
    @Mock
    private  PreparedStatement ps_detail;
    @Mock
    private  ResultSet resultSet_pharma_house;
    private PharmaDao pharmaDao;
    private PurchaseCreditNoteDetailDao purchaseCreditNoteDao;
    @Start
    public void start(Stage stage){
        MockitoAnnotations.openMocks(this);
        table_id=new TableView<>();
        table_id.setPrefWidth(800);
        table_id.setPrefHeight(800);
        VBox vBox=new VBox();
        vBox.getChildren().add(table_id);
        Scene scene=new Scene(vBox);
        purchaseOrderDao=new PurchaseOrderDao(database);
         p_detail=new PurchaseOrderDetailDao(database);
         farmacoDao=new FarmacoDao(database);
         purchaseInvoiceDao=new PurchaseInvoiceDao(database);
         pharmaDao=new PharmaDao(database);
         purchaseCreditNoteDao=new PurchaseCreditNoteDetailDao(database);
         stage.setScene(scene);
         stage.show();
        obs_table_fd_order =FXCollections.observableArrayList();
        obs_table=FXCollections.observableArrayList();
        obs_table_fd_details=FXCollections.observableArrayList();

        TableRowExpanderColumn<FieldData>expanderColumn=new TableRowExpanderColumn<>(this::createExpandendRow);
        TableColumn<FieldData,Double> col_subtotal=TableUtility.generate_column_double("Subtotale","subtotal");
        TableColumn<FieldData,Double> col_vat=TableUtility.generate_column_double("Iva","vat_amount");
        TableColumn<FieldData,Double> col_total=TableUtility.generate_column_double("Totale","total");
        TableUtility.formatting_double(col_subtotal);
        TableUtility.formatting_double(col_vat);
        TableUtility.formatting_double(col_total);
        TableColumn<FieldData, Timestamp> col_create_at = TableUtility.generate_column_timestamp("Creazione", "created_at");
        TableUtility.formatting_timestamp(col_create_at);
        table_id.getColumns().addAll(expanderColumn,
                TableUtility.generate_column_int("ID Fattura","id"),
                TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),
                TableUtility.generate_column_string("Numero Fattura Fornitore", "invoice_number"),
                TableUtility.generate_column_string("Data Emissione", "production_date"),
                TableUtility.generate_column_string("Metodo di Pagamento", "payment_mode"), col_subtotal, col_total, col_vat, col_create_at);

        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        DoubleClick_Menu<FieldData> doubleClickMenu=new DoubleClick_Menu<>(table_id);
        MenuItem menuItem=doubleClickMenu.create_menu_item("Crea Nota di credito");
        menuItem.setOnAction(event -> {

            FieldData fieldData=table_id.getSelectionModel().getSelectedItem();

            if(fieldData!=null) {
                PurchaseCreditNoteHandler purchaseCreditNoteHandler =
                        new PurchaseCreditNoteHandler("Inserisci Nota di Credito", fieldData,Arrays.asList(purchaseOrderDao,p_detail,purchaseCreditNoteDao));
                purchaseCreditNoteHandler.execute();
            }

        });
       obs_table_fd_details.addListener(new ListChangeListener<FieldData>() {
           @Override
           public void onChanged(Change<? extends FieldData> c) {
               System.out.println(c.getAddedSize());
           }
       });

    }


    @BeforeEach
    public void setUp() throws SQLException {
        //PreparedStatement preparedStatement=Mockito.mock(PreparedStatement.class);


        // Invoice
        when(resultSet_invoice.getInt(1)).thenReturn(1,2);
        when(resultSet_invoice.getString(2)).thenReturn("p100","p220");
        when(resultSet_invoice.getDate(3)).thenReturn(Date.valueOf(LocalDate.of(2024,6,10)),
                Date.valueOf(LocalDate.of(2021,6,10)));
        when(resultSet_invoice.getString(4)).thenReturn("BONIFICO_BANCARIO","BONIFICO_BANCARIO");
        when(resultSet_invoice.getDouble(5)).thenReturn(20.2,11.2);
        when(resultSet_invoice.getDouble(6)).thenReturn(0.8,2.1);
        when(resultSet_invoice.getDouble(7)).thenReturn(20.90,10.23);
        when(resultSet_invoice.getTimestamp(8)).thenReturn(new Timestamp(1742226308),new Timestamp(1742186708));
        when(resultSet_invoice.getInt(9)).thenReturn(1,2);
        when(resultSet_invoice.next()).thenReturn(true,false);

        when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet_invoice);

// Order
/*        when(resultSet_order.next()).thenReturn(true,true,false);
        when(resultSet_order.getInt(1)).thenReturn(100,200);
        when(resultSet_order.getDate(2)).thenReturn(Date.valueOf(LocalDate.of(2024,6,10)));
        when(resultSet_order.getDouble(3)).thenReturn(10.1,20.1);
        when(resultSet_order.getDouble(4)).thenReturn(1.0,1.2);
        when(resultSet_order.getDouble(5)).thenReturn(11.1,21.3);
        when(resultSet_order.getString(6)).thenReturn("1600","A1700");
        when(resultSet_order.getString(7)).thenReturn("Angelini","Angelini");
        when(resultSet_order.getInt(8)).thenReturn(100);
        when(resultSet_details.next()).thenReturn(true,false);*/




            //  Pharma House String
        when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement,p_invoicebyid,ps_detail);
        when(resultSet_pharma_house.getString(2)).thenReturn("Angelini");
        when(resultSet_pharma_house.next()).thenReturn(true,false);
        when(preparedStatement.executeQuery()).thenReturn(resultSet_pharma_house);


        // Order by  Invoicebyid
        ArgumentCaptor<Integer> paramCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> paramCaptor_order = ArgumentCaptor.forClass(Integer.class);


        when(p_invoicebyid.executeQuery()).thenAnswer(invocation -> {
            int value = paramCaptor.getAllValues().getFirst();
            System.out.println("value"+value);
            if (value == 1) {
                when(resultset_invoicebyid.next()).thenReturn(true, false,false);
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
                when(resultset_invoicebyid.getInt(9)).thenReturn(1,1);

            }

            return resultset_invoicebyid;
        });
        doNothing().when(p_invoicebyid).setInt(eq(1), paramCaptor.capture());


// Details


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
        //obs_table.setAll(purchaseInvoiceDao.findAll());
        obs_table.setAll(purchaseInvoiceDao.findAll().stream()
                .peek(fieldData -> fieldData.setNome_casa_farmaceutica(pharmaDao.findById(fieldData.getCasa_farmaceutica()).getNome_casa_farmaceutica()))
                .toList());
        table_id.setItems(obs_table);



      //  table_id.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #636165;");



    }



@Test
    public void test(FxRobot robot){




    robot.sleep(3600000);
    }

    private VBox createExpandendRow(TableRowExpanderColumn.TableRowDataFeatures<FieldData> param){
        TableView<FieldData> t_expanded=new TableView<>();
        FieldData fd_invoice=param.getValue();
      // fd_invoice.setNome_casa_farmaceutica();
        TableRowExpanderColumn<FieldData>expanderColumn=new TableRowExpanderColumn<>(this::createExpandendRowInner);
        TableColumn<FieldData,Double> col_subtotal=TableUtility.generate_column_double("Subtotale","subtotal");
        TableColumn<FieldData,Double> col_vat=TableUtility.generate_column_double("Iva","vat_amount");
        TableUtility.formatting_double(col_vat);
        TableColumn<FieldData,Double> col_total=TableUtility.generate_column_double("Totale","total");
        TableUtility.formatting_double(col_total);
        t_expanded.getColumns().addAll(expanderColumn,
                TableUtility.generate_column_string("Data Ordine","production_date"),
                TableUtility.generate_column_string("Id Ordine Fornitore","original_order_id"),
         col_subtotal,col_total,col_vat);

        //System.out.println(fd_order.getPurchase_order_id());
        obs_table_fd_order.setAll(purchaseOrderDao.findAll());
   /*     List<FieldData> list_detail=obs_table_fd_details.stream().filter(value->value.getPurchase_order_id()==fd_order.getId()).toList();*/
        t_expanded.setItems(obs_table_fd_order);
        t_expanded.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #636165;");
        t_expanded.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        return new VBox(t_expanded);
    }

    private VBox createExpandendRowInner(TableRowExpanderColumn.TableRowDataFeatures<FieldData> fieldDataTableRowDataFeatures) {
        TableView<FieldData> t_expanded_inner=new TableView<>();
        t_expanded_inner.getColumns().addAll(
                TableUtility.generate_column_string("Lotto","lotto_id"),
                TableUtility.generate_column_string("Farmaco","nome_farmaco"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),
                TableUtility.generate_column_double("Pezzo","price"),
                TableUtility.generate_column_double("Quantità","quantity"),
                TableUtility.generate_column_int("Iva","vat_percent")
        );
        obs_table_fd_details.setAll(p_detail.findAll());
        t_expanded_inner.setItems(obs_table_fd_details);
        t_expanded_inner.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #636165;");
        t_expanded_inner.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        return new VBox(t_expanded_inner);



    }

}
