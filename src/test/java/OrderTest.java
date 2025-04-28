import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableRowExpanderColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.config.database.Database;
import pharma.config.TableUtility;
import pharma.dao.FarmacoDao;
import pharma.dao.PurchaseOrderDao;
import pharma.dao.PurchaseOrderDetailDao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
public class OrderTest {
    private  TableView<FieldData> table_id;
    @Mock
    private Database database;
    private ObservableList<FieldData> obs_table;
    private PurchaseOrderDao purchaseOrderDao;
   private PurchaseOrderDetailDao p_detail;
   private  ObservableList<FieldData>obs_table_fd_details;
    @Mock
    private ResultSet resultSet_order;
    @Mock
    private ResultSet resultSet_details;
    private FarmacoDao farmacoDao;
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
         stage.setScene(scene);
         stage.show();
        obs_table_fd_details=FXCollections.observableArrayList();
        obs_table=FXCollections.observableArrayList();
        TableRowExpanderColumn<FieldData>expanderColumn=new TableRowExpanderColumn<>(this::createExpandendRow);
        TableColumn<FieldData,Double> col_subtotal=TableUtility.generate_column_double("Subtotale","subtotal");
        TableColumn<FieldData,Double> col_vat=TableUtility.generate_column_double("Iva","vat_amount");
        TableUtility.formatting_double(col_vat);
        TableColumn<FieldData,Double> col_total=TableUtility.generate_column_double("Totale","total");
        TableUtility.formatting_double(col_total);

        table_id.getColumns().addAll(expanderColumn,
                TableUtility.generate_column_string("Data Ordine","production_date"),
                TableUtility.generate_column_string("Id Ordine Fornitore","original_order_id"),
                TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),col_subtotal,col_total,col_vat);

        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }


    @BeforeEach
    public void setUp() throws SQLException {
        PreparedStatement preparedStatement=Mockito.mock(PreparedStatement.class);
        Mockito.when(resultSet_order.next()).thenReturn(true,false);
        Mockito.when(resultSet_order.getInt(1)).thenReturn(100);
        Mockito.when(resultSet_order.getDate(2)).thenReturn(Date.valueOf(LocalDate.of(2024,6,10)));
        Mockito.when(resultSet_order.getDouble(3)).thenReturn(10.1);
        Mockito.when(resultSet_order.getDouble(4)).thenReturn(1.0);
        Mockito.when(resultSet_order.getDouble(5)).thenReturn(11.1);
        Mockito.when(resultSet_order.getString(6)).thenReturn("1600");
        Mockito.when(resultSet_order.getString(7)).thenReturn("Angelini");
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet_order,resultSet_details);

        when(resultSet_details.next()).thenReturn(true,false);
        when(resultSet_details.getInt(1)).thenReturn(1);
        when(resultSet_details.getString(2)).thenReturn("aaa");
        when(resultSet_details.getInt("farmaco")).thenReturn(1);
        when(resultSet_details.getInt("purchase_order")).thenReturn(100);
        when(resultSet_details.getDouble("price")).thenReturn(10.1);
        when(resultSet_details.getInt("quantity")).thenReturn(10);
        when(resultSet_details.getInt("vat_percent")).thenReturn(4);
        when(resultSet_details.getString("nome")).thenReturn("Tachipirina");
        when(resultSet_details.getString("tipologia")).thenReturn("Compresse");
        when(resultSet_details.getString("misura")).thenReturn("100 mg");
        when(preparedStatement.executeQuery()).thenReturn(resultSet_details);
        Mockito.when(database.execute_prepared_query(anyString())).thenReturn(preparedStatement);


        obs_table.setAll(purchaseOrderDao.findAll());
        table_id.setItems(obs_table);


      //  table_id.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #636165;");



    }



@Test
    public void test(FxRobot robot){
    robot.sleep(40000);
    }

    private VBox createExpandendRow(TableRowExpanderColumn.TableRowDataFeatures<FieldData> param){
        TableView<FieldData> t_expanded=new TableView<>();
        FieldData fd_order=param.getValue();
        t_expanded.getColumns().addAll(
                TableUtility.generate_column_string("Lotto","lotto_id"),
                TableUtility.generate_column_string("Farmaco","nome_farmaco"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),
                TableUtility.generate_column_double("Pezzo","price"),
                TableUtility.generate_column_double("Quantità","quantity"),
                TableUtility.generate_column_int("Iva","vat_percent")
        );
        System.out.println(fd_order.getPurchase_order_id());
        obs_table_fd_details.setAll(p_detail.findDetailbyPurchaseOrderId(fd_order.getId()));
        t_expanded.setItems(obs_table_fd_details);
        t_expanded.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #636165;");
        t_expanded.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        return new VBox(t_expanded);
    }

}
