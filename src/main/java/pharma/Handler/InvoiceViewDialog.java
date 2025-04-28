package pharma.Handler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import pharma.Model.FieldData;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.config.TableUtility;
import pharma.dao.*;

import java.util.List;

public class InvoiceViewDialog  extends CustomDialog<FieldData> {
    private  TableView<FieldData> table_id;
    private  FieldData fieldData_header;

    private ObservableList<FieldData> obs_table_innner;
    public InvoiceViewDialog(String content, FieldData fieldData_header, PurchaseOrderDetailDao p_detail) {
        super(content);
        getDialogPane().setPrefWidth(1200);
        getDialogPane().setPrefHeight(500);
        obs_table_innner=FXCollections.observableArrayList();
        this.fieldData_header=fieldData_header;
        add_header_invoice();
        table_id=add_table();
        add_column();
        obs_table_innner.setAll(p_detail.findDetailbyPurchaseOrderId(fieldData_header.getPurchase_order_id()));
        table_id.setItems(obs_table_innner);
    }
    public InvoiceViewDialog(String content, FieldData fieldData_header, PurchaseCreditNoteDao purchaseCreditNoteDao) {
        /*
        Credit Note View
         */
        super(content);
        getDialogPane().setPrefWidth(1200);
        getDialogPane().setPrefHeight(500);
        obs_table_innner=FXCollections.observableArrayList();
        this.fieldData_header=fieldData_header;
        List<FieldData> fieldData_details=purchaseCreditNoteDao.findDetailbyInvoiceId(fieldData_header.getId());
        add_header_credit_note(purchaseCreditNoteDao.findbyInvoiceid(fieldData_header.getId()));
        table_id=add_table();
        add_column();

        obs_table_innner.setAll(fieldData_details);
        table_id.setItems(obs_table_innner);
        System.out.println("table"+table_id.getItems().size());




    }


    private  void add_header_invoice(){



        HBox hBox_label=add_hbox(88);
        HBox hBox_textfiender=add_hbox(20.0);
        createLabeledTextField("Fattura ID",String.valueOf(fieldData_header.getId()),hBox_label,hBox_textfiender);


        createLabeledTextField("Casa Farmaceutica",String.valueOf(fieldData_header.getNome_casa_farmaceutica()),hBox_label,hBox_textfiender);
        createLabeledTextField("Data Emissione",String.valueOf(fieldData_header.getProduction_date()),hBox_label,hBox_textfiender);
        createLabeledTextField("Metodo Pagamento",String.valueOf(fieldData_header.getPayment_mode()),hBox_label,hBox_textfiender).setPrefWidth(250);
        createLabeledTextField("Imponibile",String.format("%.2f",fieldData_header.getSubtotal()),hBox_label,hBox_textfiender);
        createLabeledTextField("Totale",String.format("%.2f",fieldData_header.getTotal()),hBox_label,hBox_textfiender);
        createLabeledTextField("Iva",String.format("%.2f",fieldData_header.getVat_amount()),hBox_label,hBox_textfiender);
    }
    private  void add_header_credit_note(FieldData fieldData_credit_note){
        GridPane gridPane=add_gridpane(16);
        createLabeledTextField("Numero Nota di Credito",String.valueOf(fieldData_credit_note.getId()),gridPane,0,0,0,1).setPrefWidth(220);
        createLabeledTextField("Data Emissione",String.valueOf(fieldData_credit_note.getProduction_date()),gridPane,1,0,1,1);
        createLabeledTextField("Casa Farmaceutica",String.valueOf(fieldData_header.getNome_casa_farmaceutica()),gridPane,2,0,2,1);
        createLabeledTextField("Motivazione",fieldData_credit_note.getMotive(),gridPane,3,0,3,1);
        createLabeledTextField("Imponibile",String.format("%.2f",fieldData_credit_note.getSubtotal()),gridPane,4,0,4,1);
        createLabeledTextField("Iva",String.format("%.2f",fieldData_credit_note.getVat_amount()),gridPane,5,0,5,1);
        createLabeledTextField("Totale",String.format("%.2f",fieldData_credit_note.getTotal()),gridPane,6,0,6,1);
    }

    private  void add_column(){
        TableColumn<FieldData, Double> col_price=TableUtility.generate_column_double("Prezzo","price");
        TableColumn<FieldData, Integer> col_qty= TableUtility.generate_column_int("Quantità","quantity");
        TableColumn<FieldData, Integer> col_vat=TableUtility.generate_column_int("Iva","vat_percent");
        table_id.getColumns().addAll(
               TableUtility.generate_column_string("Lotto","lotto_id"),
                TableUtility.generate_column_string("Farmaco","nome_farmaco"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),col_price,col_qty,col_vat
        );
    }








}
