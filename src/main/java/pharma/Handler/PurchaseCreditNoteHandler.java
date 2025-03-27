package pharma.Handler;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.table.TableRowExpanderColumn;
import pharma.Model.FieldData;
import pharma.config.ListCalculate;
import pharma.config.PopulateChoice;
import pharma.config.spinner.SpinnerTableCellOrder;
import pharma.config.TableUtility;
import pharma.dao.*;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class PurchaseCreditNoteHandler extends  DialogHandler{
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
    private PurchaseCreditNoteOrder p_credit_order;
    public PurchaseCreditNoteHandler(String content,FieldData fieldData_header,List<GenericJDBCDao> list) {
      super(content,list,fieldData_header);
        getDialogPane().setPrefWidth(1200);
        getDialogPane().setPrefHeight(900);
        purchaseCreditNoteDao=(PurchaseCreditNoteDao) list.stream().filter(genericJDBCDao -> genericJDBCDao instanceof PurchaseCreditNoteDao).findFirst().
                orElseThrow(()->new IllegalArgumentException("Not found"));
    }
    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {
       obs_table= FXCollections.observableArrayList();
       obs_details=FXCollections.observableHashMap();
       s_update=new SimpleIntegerProperty(-1);
        if(optionalfieldData.isPresent() && optionalgenericJDBCDao.isPresent()) {
            List<GenericJDBCDao> genericJDBCDaoList=optionalgenericJDBCDao.get();
            PurchaseOrderDao purchaseOrderDao= (PurchaseOrderDao) genericJDBCDaoList.stream().filter(genericJDBCDao -> genericJDBCDao instanceof  PurchaseOrderDao).findFirst().
                    orElseThrow(()->new IllegalArgumentException("Not found"));
           PurchaseOrderDetailDao p_detail= (PurchaseOrderDetailDao) genericJDBCDaoList.stream().filter(genericJDBCDao -> genericJDBCDao instanceof PurchaseOrderDetailDao).findFirst().
                    orElseThrow(()->new IllegalArgumentException("Not found"));
             FieldData fieldData_header=optionalfieldData.get();
            textField_credit_note_number = add_text_field("Inserisci Numero Nota di Credito");
            date_credit_note = add_calendar();
            textField_motivo = this.add_text_field("Inserisci Motivazione");
            add_header(fieldData_header);
            table_id = add_table();
            add_column(p_detail);
            List<FieldData> list=purchaseOrderDao.findByInvoiceid(fieldData_header.getId());

         obs_table.setAll(list);
            table_id.setItems(obs_table);
            calculus_attribute();



        }
    }




    private  void add_column(PurchaseOrderDetailDao p_detail){
        TableRowExpanderColumn<FieldData> expanderColumn=new TableRowExpanderColumn<>(col->createExpandendRow_order_items(col.getValue(),p_detail));


        // Force Expand All Rows After UI Loads
        Platform.runLater(() -> {
            for (int i = 0; i < table_id.getItems().size(); i++) {
                if (!expanderColumn.getExpandedProperty(table_id.getItems().get(i)).get()) {
                    expanderColumn.toggleExpanded(i);
                }
            }
        });
        TableColumn<FieldData,Double> col_subtotal= TableUtility.generate_column_double("Subtotale","subtotal");
        TableUtility.formatting_double(col_subtotal);
        TableColumn<FieldData,Double> col_vat=TableUtility.generate_column_double("Iva","vat_amount");
        TableUtility.formatting_double(col_vat);
        TableColumn<FieldData,Double> col_total=TableUtility.generate_column_double("Totale","total");
        TableUtility.formatting_double(col_total);
      table_id.getColumns().addAll(expanderColumn,
                TableUtility.generate_column_string("Data Ordine","production_date"),
                TableUtility.generate_column_string("Id Ordine Fornitore","original_order_id"),
                col_subtotal,col_total,col_vat);
    }

    private VBox createExpandendRow_order_items(FieldData fieldData,PurchaseOrderDetailDao p_detail) {
ObservableList<FieldData> obs_table_fd_details=FXCollections.observableArrayList();
        TableView<FieldData> t_expanded_inner=new TableView<>();
        TableColumn<FieldData, Double> col_price=TableUtility.generate_column_double("Prezzo","price");


        col_price.setCellFactory(col-> new SpinnerTableCellOrder<>(Double.class,s_update,"setPrice"));

        TableColumn<FieldData, Integer> col_qty= TableUtility.generate_column_int("Quantità","quantity");
        col_qty.setCellFactory(col->new SpinnerTableCellOrder<>(Integer.class,s_update,"setQuantity"));

        TableColumn<FieldData, Integer> col_vat=TableUtility.generate_column_int("Iva","vat_percent");
        col_vat.setCellFactory(col->new SpinnerTableCellOrder<>(Integer.class,s_update,"setPercent"));

        t_expanded_inner.getColumns().addAll(
                TableUtility.generate_column_string("Lotto","lotto_id"),
                TableUtility.generate_column_string("Farmaco","nome_farmaco"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),col_price,col_qty,col_vat
              );

        if(fieldData.getFieldDataList()==null) {
            t_expanded_inner.setItems(obs_table_fd_details);
            obs_table_fd_details.setAll(p_detail.findDetailbyPurchaseOrderId(fieldData.getId()));

            t_expanded_inner.setItems(obs_table_fd_details);
            fieldData.setFieldDataList(obs_table_fd_details);
        }else{
            obs_table_fd_details.setAll(fieldData.getFieldDataList());
            t_expanded_inner.setItems(obs_table_fd_details);

        }

    t_expanded_inner.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        VBox vBox=new VBox(t_expanded_inner);
        vBox.setPrefHeight(100);
        return vBox;
    }



    private void add_header(FieldData fieldData_header){

        HBox hBox_label=add_hbox(88);
        HBox hBox_textfiender=add_hbox(20.0);
        createLabeledTextField("Fattura ID",String.valueOf(fieldData_header.getId()),hBox_label,hBox_textfiender);

        /*
        Casa Farmaceutica lo eredito dal view
         */
        createLabeledTextField("Casa Farmaceutica",String.valueOf(fieldData_header.getNome_casa_farmaceutica()),hBox_label,hBox_textfiender);
        createLabeledTextField("Data Emissione",String.valueOf(fieldData_header.getProduction_date()),hBox_label,hBox_textfiender);
       createLabeledTextField("Metodo Pagamento",String.valueOf(fieldData_header.getPayment_mode()),hBox_label,hBox_textfiender).setPrefWidth(250);
        createLabeledTextField("Imponibile",String.valueOf(fieldData_header.getSubtotal()),hBox_label,hBox_textfiender);
        createLabeledTextField("Totale",String.valueOf(fieldData_header.getTotal()),hBox_label,hBox_textfiender);
        createLabeledTextField("Iva",String.valueOf(fieldData_header.getVat_amount()),hBox_label,hBox_textfiender);
        invoice_id =fieldData_header.getId();
        pharma_id=fieldData_header.getCasa_farmaceutica();



    }



    private  void calculus_attribute() {
        // newvalue  is the value of order_id
        s_update.addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(-1)){
                int order_id= (int) newValue;
                // find a row that have same order_id
                Optional<FieldData> fieldData_order_row=table_id.getItems().stream().filter(fieldData -> fieldData.getId()==order_id).findFirst();

                        fieldData_order_row.ifPresent(fieldData_order_parent -> {

                        List<FieldData> fieldData_details=fieldData_order_parent.getFieldDataList();
                        double subtotale= ListCalculate.amount_subtotal(FXCollections.observableList(fieldData_details));
                        double totalVatAmount=ListCalculate.amount_vat(FXCollections.observableList(fieldData_details));
                        fieldData_order_parent.setSubtotal(subtotale);
                        fieldData_order_parent.setVat_amount(totalVatAmount);
                        fieldData_order_parent.setTotal(ListCalculate.total(totalVatAmount,subtotale));
                        table_id.refresh();
                    });
            }
            s_update.set(-1);

        });

    }



    @Override
    protected FieldData get_return_data() {
        return FieldData.FieldDataBuilder.getbuilder().
                setCredit_note_number(textField_credit_note_number.getText()).
                setInvoice_id(invoice_id).
                setProduction_date(Date.valueOf(date_credit_note.getValue())).
                setMotive(textField_motivo.getText()).
                setCasa_Farmaceutica(pharma_id).setFieldDataListAll(obs_table).
                build();



    }

    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {

        purchaseCreditNoteDao.setTransaction(true);
    int value=purchaseCreditNoteDao.insertAndReturnID(fieldData);
    if((value!=-1) && (value!=0)){
        System.out.println("value: "+value);
        System.out.println("size order :"+fieldData.getFieldDataList().size());
        System.out.println("table detail: "+obs_table.getFirst().getFieldDataList().size());
        System.out.println("table detail 2: "+obs_table.get(1).getFieldDataList().size());

/*fieldData.getFieldDataList().forEach(fieldata_order->{

           return p_credit_order.insert(FieldData.FieldDataBuilder.getbuilder().setInvoice_id(value).
                setPurchase_order_id(fieldata_order.getId()).setSubtotal(fieldata_order.getSubtotal()).
                setVat_amount(fieldata_order.getVat_amount()).setTotal(fieldata_order.getTotal()).build());



/*    create table purchase_credit_note_details(

            credit_note_id int NOT NULL  REFERENCES purchase_credit_note(id),
            order_details  int not null references  purchase_order_detail(id),
            quantity int not null,
            price double precision not null,
            vat_percent double precision,
    lotto varchar not null,
            farmaco int not null,
            FOREIGN KEY (lotto,farmaco) REFERENCES lotto(id,farmaco)
);*/
/*    fieldata_order.getFieldDataList().stream().forEach(fieldata_detail->{
        System.out.println("id order detail"+fieldata_detail.getId());
        System.out.println("price"+fieldata_detail.getPrice());
        System.out.println("quantity"+fieldata_detail.getQuantity());


    })
   });


 */








        return true;
    }
    return false;
    }




    @Override
    protected void initialize() {

    }





}
