package pharma.Controller.subpanel;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.controlsfx.control.table.TableRowExpanderColumn;
import pharma.Handler.InvoiceViewDialog;
import pharma.Handler.PurchaseCreditNoteHandler;
import pharma.Handler.PurchaseInvoiceHandler;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.config.TableUtility;
import pharma.dao.*;

import java.io.FileReader;
import java.io.IOException;

import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

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
    private PurchaseCreditNoteDao purchaseCreditNoteDao;
    private PurchaseCreditNoteDetailDao purchaseCreditNoteDetail;
    private PurchaseOrderDetailDao p_detail;
    public PurchaseInvoice() {

        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            purchaseInvoiceDao = new PurchaseInvoiceDao(Database.getInstance(properties));
            purchaseOrderDao = new PurchaseOrderDao(Database.getInstance(properties));
            pharmaDao = new PharmaDao(Database.getInstance(properties));
            purchaseCreditNoteDetail=new PurchaseCreditNoteDetailDao(Database.getInstance(properties));
            purchaseCreditNoteDao=new PurchaseCreditNoteDao(Database.getInstance(properties));
            p_detail=new PurchaseOrderDetailDao(Database.getInstance(properties));


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

        tableView_inner.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

            return  new VBox(tableView_inner);
}
public void double_click(){


    table_id.setRowFactory(tv -> {
        ContextMenu contextMenu = new ContextMenu();
        TableRow<FieldData> tableRow = new TableRow<>();
        MenuItem add_credit_note = new MenuItem("Aggiungi Nota di Credito.");
        MenuItem view_credit_note= new MenuItem("Visualizza Nota di Credito");
        MenuItem view_invoice=new MenuItem("Visualizza Fattura");
        contextMenu.getItems().add(view_invoice);
        add_credit_note.setOnAction(event -> {
            FieldData fieldData = tableRow.getItem();
            if (fieldData != null) {
                assert purchaseCreditNoteDao != null : "Null";
                PurchaseCreditNoteHandler purchaseCreditNoteHandler =
                        new PurchaseCreditNoteHandler("Aggiungi Nota di Credito", fieldData, Arrays.asList(p_detail, purchaseCreditNoteDetail, purchaseCreditNoteDao));
                purchaseCreditNoteHandler.execute();

            } else {
                System.out.println("fieldata item null");
            }

        });
        view_credit_note.setOnAction(event -> {
            FieldData fieldData = tableRow.getItem();
            if(fieldData!=null) {
                InvoiceViewDialog invoiceViewDialog = new InvoiceViewDialog("Visualizza Nota di Credito", fieldData, purchaseCreditNoteDao);
                invoiceViewDialog.show();
            }





        });
        view_invoice.setOnAction(event -> {
            FieldData fieldData = tableRow.getItem();

            InvoiceViewDialog invoiceViewDialog=new InvoiceViewDialog("Visualizza Dettagli fattura",fieldData,p_detail);
            invoiceViewDialog.show();

        });



        tableRow.setOnContextMenuRequested(event -> {
            System.out.println("menu request");
            FieldData fieldData = tableRow.getItem();

            if (fieldData != null) {

                boolean value = purchaseCreditNoteDao.exist_credit_note(fieldData.getId());
                if (!value) {
                    System.out.println("execute true");
                    if(!contextMenu.getItems().contains(add_credit_note)) {
                        contextMenu.getItems().add(add_credit_note);
                    }

                } else {
                    if(!contextMenu.getItems().contains(view_credit_note)) {
                        contextMenu.getItems().add(view_credit_note);
                    }


                }
                contextMenu.show(tableRow, event.getScreenX(), event.getScreenY());


            } else {
                System.out.println("fieldata null");
            }
            event.consume();


        });







        return tableRow;
    });
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
        table_id.getColumns().addAll(
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
                table_obs.setAll(purchaseInvoiceDao.findAll().stream()
                        .peek(fieldData -> fieldData.setNome_casa_farmaceutica(pharmaDao.findById(fieldData.getCasa_farmaceutica()).getNome_casa_farmaceutica()))
                        .toList());
                table_id.setItems(table_obs);
            }
        });

            double_click();



















  /*          FieldData fieldData=(FieldData) table_id.getSelectionModel().getSelectedItem();
            PurchaseCreditNoteHandler purchaseCreditNoteHandler =
                    new PurchaseCreditNoteHandler("Aggiungi Nota di Credito", fieldData, Arrays.asList(p_detail, purchaseCreditNoteDao, purchaseCreditNoteDetail));
            purchaseCreditNoteHandler.execute();
*/




    }
}


