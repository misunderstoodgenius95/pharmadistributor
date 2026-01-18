package pharma.Controller.subpanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.table.TableRowExpanderColumn;
import pharma.DialogController.PurchaseCreditNoteControllerBase;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.PathConfig;
import pharma.config.database.Database;
import pharma.javafxlib.DoubleClick_Menu;
import pharma.config.TableUtility;
import pharma.dao.*;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;

public class PurchaseCreditNote implements Initializable {

    @FXML
    public TableView table_id;

    private ObservableList<FieldData>  obs_table_fd_order;
    private  ObservableList<FieldData> obs_table_fd_details;
    private  ObservableList<FieldData> obs_table;
    private PurchaseCreditNoteControllerBase creditNoteHandler;
        private  PurchaseCreditNoteDao purchaseCreditNoteDao;
        private  PurchaseCreditNoteDetailDao purchaseCreditNoteDetailDao;
        private PurchaseInvoiceDao purchaseInvoiceDao;
        private PurchaseOrderDetailDao p_detail;
        private PharmaDao pharmaDao;
    public PurchaseCreditNote() {

        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader(PathConfig.DATABASE_CONF.getValue()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pharmaDao=new PharmaDao(Database.getInstance(properties));
        purchaseInvoiceDao=new PurchaseInvoiceDao(Database.getInstance(properties));
        purchaseCreditNoteDao = new PurchaseCreditNoteDao(Database.getInstance(properties));
        purchaseCreditNoteDetailDao= new PurchaseCreditNoteDetailDao(Database.getInstance(properties));
        p_detail=new PurchaseOrderDetailDao(Database.getInstance(properties));
    }

    public void btn_action_add(ActionEvent actionEvent) {


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obs_table_fd_order = FXCollections.observableArrayList();
        obs_table = FXCollections.observableArrayList();
        obs_table_fd_details = FXCollections.observableArrayList();

        TableRowExpanderColumn<FieldData> expanderColumn = new TableRowExpanderColumn<>(this::createExpandendRowInner);
        TableColumn<FieldData, Double> col_subtotal = TableUtility.generate_column_double("Subtotale", "subtotal");
        TableColumn<FieldData, Double> col_vat = TableUtility.generate_column_double("Iva", "vat_amount");
        TableColumn<FieldData, Double> col_total = TableUtility.generate_column_double("Totale", "total");
        TableUtility.formatting_double(col_subtotal);
        TableUtility.formatting_double(col_vat);
        TableUtility.formatting_double(col_total);
        TableColumn<FieldData, Timestamp> col_create_at = TableUtility.generate_column_timestamp("Creazione", "created_at");
        TableUtility.formatting_timestamp(col_create_at);
        table_id.getColumns().addAll(expanderColumn,
                TableUtility.generate_column_int("ID Fattura", "id"),
                TableUtility.generate_column_string("Casa Farmaceutica", "nome_casa_farmaceutica"),
                TableUtility.generate_column_string("Numero Fattura Fornitore", "invoice_number"),
                TableUtility.generate_column_string("Data Emissione", "production_date"),
                TableUtility.generate_column_string("Metodo di Pagamento", "payment_mode"), col_subtotal, col_total, col_vat, col_create_at);

        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        DoubleClick_Menu<FieldData> doubleClickMenu = new DoubleClick_Menu<>(table_id);
        MenuItem menuItem = doubleClickMenu.create_menu_item("Crea Nota di credito");
        menuItem.setOnAction(event -> {

            FieldData fieldData = (FieldData) table_id.getSelectionModel().getSelectedItem();

            if (fieldData != null) {
                PurchaseCreditNoteControllerBase purchaseCreditNoteHandler =
                        new PurchaseCreditNoteControllerBase("Inserisci Nota di Credito", fieldData, Arrays.asList(p_detail, purchaseCreditNoteDao, purchaseCreditNoteDetailDao));
                purchaseCreditNoteHandler.execute();
            }

        });


        obs_table.setAll(purchaseInvoiceDao.findAll().stream()
                .peek(fieldData -> fieldData.setNome_casa_farmaceutica(pharmaDao.findById(fieldData.getCasa_farmaceutica()).getNome_casa_farmaceutica()))
                .toList());
        table_id.setItems(obs_table);

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
