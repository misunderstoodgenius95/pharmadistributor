package pharma.Handler;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import pharma.Model.FieldData;
import pharma.config.ListCalculate;
import pharma.config.PopulateChoice;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.config.spinner.SpinnerTableCellOrder;
import pharma.dao.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class PurchaseCreditNoteHandler extends  DialogHandler<FieldData> {
    private TextField textField_credit_note_number;
    private DatePicker date_credit_note;
    private TextField  textField_motivo;
    private  int invoice_id;
    private  TableView<FieldData> table_id;
    private ObservableList<FieldData> obs_table;
    private ObservableMap<Integer,ObservableList<FieldData>> obs_details;
    private SimpleIntegerProperty s_update;
    private  int pharma_id;
    private PurchaseCreditNoteDao purchaseCreditNoteDao;
    private PurchaseCreditNoteDetailDao p_credit_dao;
    private Label label_subtotale;
    private  Label label_iva;
    private  Label label_totale;
    public PurchaseCreditNoteHandler(String content, FieldData fieldData_header, List<GenericJDBCDao> list) {
      super(content,list,fieldData_header);
        getDialogPane().setPrefWidth(1200);
        getDialogPane().setPrefHeight(900);
        System.out.println(list.size());

        purchaseCreditNoteDao=(PurchaseCreditNoteDao) list.stream().filter(genericJDBCDao -> PurchaseCreditNoteDao.class.isAssignableFrom(genericJDBCDao.getClass())).findFirst().
                orElseThrow(()->new IllegalArgumentException("Not found PurchaseCreditNoteDao"));
        p_credit_dao=(PurchaseCreditNoteDetailDao) list.stream().filter(genericJDBCDao ->  PurchaseCreditNoteDetailDao.class.isAssignableFrom(genericJDBCDao.getClass())).findFirst().
                orElseThrow(()->new IllegalArgumentException("Not found PurchaseCreditNoteDetailDao"));

    }
    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {
       obs_table= FXCollections.observableArrayList();
       obs_details=FXCollections.observableHashMap();
       s_update=new SimpleIntegerProperty(-1);
        if(optionalfieldData.isPresent() && optionalgenericJDBCDao.isPresent()) {
            List<GenericJDBCDao> genericJDBCDaoList=optionalgenericJDBCDao.get();
           PurchaseOrderDetailDao p_detail= (PurchaseOrderDetailDao) genericJDBCDaoList.stream().filter(genericJDBCDao -> genericJDBCDao instanceof PurchaseOrderDetailDao).findFirst().
                    orElseThrow(()->new IllegalArgumentException("Not found"));
             FieldData fieldData_header=optionalfieldData.get();
            textField_credit_note_number = add_text_field("Inserisci Numero Nota di Credito");
            date_credit_note = add_calendar();
            textField_motivo = this.add_text_field("Inserisci Motivazione");
            add_header(fieldData_header);
            obs_table.setAll(p_detail.findDetailbyPurchaseOrderId(fieldData_header.getOrder_id()));
            table_id = add_table();
            add_column();
            table_id.setItems(obs_table);
            calculus_attribute();
            label_subtotale = add_label("SubTotale");
            label_iva = add_label("IVA");
            label_totale = add_label("Totale");
            s_update.set(1);


        }
    }




    private  void add_column(){
        TableColumn<FieldData, Double> col_price=TableUtility.generate_column_double("Prezzo","price");
        col_price.setCellFactory(col-> new SpinnerTableCellOrder<>(Double.class,s_update,"setPrice"));
        TableColumn<FieldData, Integer> col_qty= TableUtility.generate_column_int("Quantità","quantity");
        col_qty.setCellFactory(col->new SpinnerTableCellOrder<>(Integer.class,s_update,"setQuantity"));
        TableColumn<FieldData, Integer> col_vat=TableUtility.generate_column_int("Iva","vat_percent");
        col_vat.setCellFactory(col->new SpinnerTableCellOrder<>(Integer.class,s_update,"setPercent"));
        table_id.getColumns().addAll(
                TableUtility.generate_column_string("Lotto","lotto_id"),
                TableUtility.generate_column_string("Farmaco","nome_farmaco"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),col_price,col_qty,col_vat
        );
    }




    private void add_header(FieldData fieldData_header){

        HBox hBox_label=add_hbox(88);
        HBox hBox_textfiender=add_hbox(20.0);
        createLabeledTextField("Fattura ID",String.valueOf(fieldData_header.getId()),hBox_label,hBox_textfiender);

        /*
        Casa Farmaceutica lo eredito dal view
         */
        createLabeledTextField("Casa Farmaceutica",fieldData_header.getNome_casa_farmaceutica(),hBox_label,hBox_textfiender);
        createLabeledTextField("Data Emissione",String.valueOf(fieldData_header.getProduction_date()),hBox_label,hBox_textfiender);
       createLabeledTextField("Metodo Pagamento",fieldData_header.getPayment_mode(),hBox_label,hBox_textfiender).setPrefWidth(250);
        createLabeledTextField("Imponibile",String.format("%.2f",fieldData_header.getSubtotal()),hBox_label,hBox_textfiender);
        createLabeledTextField("Totale",String.format("%.2f",fieldData_header.getTotal()),hBox_label,hBox_textfiender);
        createLabeledTextField("Iva",String.format("%.2f",fieldData_header.getVat_amount()),hBox_label,hBox_textfiender);
        invoice_id =fieldData_header.getId();
        pharma_id=fieldData_header.getCasa_farmaceutica();



    }



    private  void calculus_attribute() {

        s_update.addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(-1)){

                Utility.resetLabelText(label_iva,label_subtotale,label_totale);

                double subtotale=ListCalculate.amount_subtotal(table_id.getItems());
                double totalVatAmount=ListCalculate.amount_vat(table_id.getItems());
                double totale=ListCalculate.total(totalVatAmount,subtotale);

                label_subtotale.setUserData(subtotale);
                label_iva.setUserData(totalVatAmount);
                label_totale.setUserData(totale);
                label_iva.setText(label_iva.getText()+":  "+String.format("%.2f", totalVatAmount));
                label_totale.setText(label_totale.getText()+": "+String.format("%.2f",totale));
                label_subtotale.setText(label_subtotale.getText()+": "+String.format("%.2f",subtotale));
            }
            s_update.set(-1);

        });

    }



    @Override
    protected FieldData get_return_data() {
        System.out.println("credit"+textField_credit_note_number.getText());
        return FieldData.FieldDataBuilder.getbuilder().
                setCredit_note_number(textField_credit_note_number.getText()).
                setInvoice_id(invoice_id).
                setProduction_date(Date.valueOf(date_credit_note.getValue())).
                setMotive(textField_motivo.getText()).
                setCasa_Farmaceutica(pharma_id).
                setSubtotal((Double)label_subtotale.getUserData()).
                setVat_amount((Double)label_iva.getUserData()).
                setTotal((Double)label_totale.getUserData()).
                setFieldDataListAll(obs_table).
                build();
    }

    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {

        purchaseCreditNoteDao.setTransaction(true);
    int index_credit_note=purchaseCreditNoteDao.insertAndReturnID(fieldData);
    if((index_credit_note!=-1) && (index_credit_note!=0)){

        boolean result= fieldData.getFieldDataList().stream().allMatch(fieldata_detail -> {
                    FieldData fieldData_insert_details= FieldData.FieldDataBuilder.getbuilder()
                            .setInvoice_id(index_credit_note).
                            setOrder_id(fieldata_detail.getId()).
                            setQuantity(fieldata_detail.getQuantity()).
                            setVat_percent(fieldata_detail.getVat_percent()).
                            setPrice(fieldata_detail.getPrice()).build();
                    return  p_credit_dao.insert(fieldData_insert_details);

                });
        if(result){
          purchaseCreditNoteDao.commit();


        }else{
            purchaseCreditNoteDao.rollback();
        }
        return result;
    }
    return false;
    }




    @Override
    protected void initialize() {


    }





}
