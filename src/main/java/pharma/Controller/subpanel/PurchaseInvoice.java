package pharma.Controller.subpanel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.table.TableRowExpanderColumn;
import pharma.Handler.PurchaseInvoiceHandler;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.Database;
import pharma.config.TableUtility;
import pharma.dao.PharmaDao;
import pharma.dao.PurchaseInvoiceDao;
import pharma.dao.PurchaseOrderDao;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;

public class PurchaseInvoice implements Initializable {
    public TableView table_id;
    private PurchaseOrderDao purchaseOrderDao;
    private PurchaseInvoiceDao purchaseInvoiceDao;
    private PharmaDao pharmaDao;
    private Properties properties;
    private ObservableList<FieldData> table_obs;
    private SimpleBooleanProperty simpleBooleanProperty;
    private TableColumn<FieldData, Double> col_subtotal;
    private TableColumn<FieldData, Double> col_vat;
    private TableColumn<FieldData, Double> col_total;

    public PurchaseInvoice() {

        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            purchaseInvoiceDao = new PurchaseInvoiceDao(Database.getInstance(properties));
            purchaseOrderDao = new PurchaseOrderDao(Database.getInstance(properties));
            pharmaDao = new PharmaDao(Database.getInstance(properties));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btn_action_add(ActionEvent actionEvent) {
        PurchaseInvoiceHandler purchaseInvoiceHandler = new PurchaseInvoiceHandler(pharmaDao, purchaseInvoiceDao, purchaseOrderDao, simpleBooleanProperty);
        purchaseInvoiceHandler.execute();
    }
    private VBox createExpandendRow(TableRowExpanderColumn.TableRowDataFeatures<FieldData> fieldDataTableRowDataFeatures) {
        FieldData fieldData=fieldDataTableRowDataFeatures.getValue();
        TableView<FieldData> tableView_inner=new TableView<>();
        ObservableList<FieldData> obs_table_innner=FXCollections.observableArrayList();
        tableView_inner.setItems(obs_table_innner);
          TableColumn<FieldData,Double> col_subtotal_inner = TableUtility.generate_column_double("Subtotale", "subtotal");
        TableUtility.formatting_double(col_subtotal_inner);
        TableColumn<FieldData,Double>col_vat_inner = TableUtility.generate_column_double("Iva", "vat_amount");
        TableUtility.formatting_double(col_vat_inner);
        TableColumn<FieldData,Double> col_total_inner = TableUtility.generate_column_double("Totale", "total");
        TableUtility.formatting_double(col_total_inner);

        tableView_inner.getColumns().addAll(
                TableUtility.generate_column_string("Data Ordine","production_date"),
                TableUtility.generate_column_string("Id Ordine Fornitore","original_order_id"),
                TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),
                col_subtotal_inner,col_vat_inner,col_total_inner);

                obs_table_innner.setAll( purchaseOrderDao.findByInvoiceid(fieldData.getId()));
               tableView_inner.setItems(obs_table_innner);
        System.out.println(tableView_inner.getItems().size());

        tableView_inner.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

            return  new VBox(tableView_inner);
}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        simpleBooleanProperty = new SimpleBooleanProperty(false);
        table_obs = FXCollections.observableArrayList();
        TableRowExpanderColumn<FieldData> expanderColumn = new TableRowExpanderColumn<>(this::createExpandendRow);
        col_subtotal = TableUtility.generate_column_double("Subtotale a", "subtotal");
        TableUtility.formatting_double(col_subtotal);
         col_vat = TableUtility.generate_column_double("Iva a", "vat_amount");
        TableUtility.formatting_double(col_vat);
         col_total = TableUtility.generate_column_double("Totale a", "total");
        TableUtility.formatting_double(col_total);
        TableColumn<FieldData, Timestamp> col_create_at = TableUtility.generate_column_timestamp("Creazione", "created_at");
        TableUtility.formatting_timestamp(col_create_at);
        table_id.getColumns().addAll(expanderColumn,
                TableUtility.generate_column_int("ID Fattura", "id"),
                TableUtility.generate_column_string("Numero Fattura Fornitore", "invoice_number"),
                TableUtility.generate_column_string("Casa Farmaceutica", "nome_casa_farmaceutica"),
                TableUtility.generate_column_string("Data Emissione", "production_date"),
                TableUtility.generate_column_string("Metodo di Pagamento", "payment_mode"), col_subtotal, col_total, col_vat, col_create_at);
       table_obs.setAll(purchaseInvoiceDao.findAll().stream()
                .peek(fieldData -> fieldData.setNome_casa_farmaceutica(pharmaDao.findById(fieldData.getCasa_farmaceutica()).getNome_casa_farmaceutica()))
                .toList());
        table_id.setItems(table_obs);
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        simpleBooleanProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(true)) {
                table_obs.setAll(purchaseOrderDao.findAll());
                table_id.setItems(table_obs);
            }
        });


    }
}


