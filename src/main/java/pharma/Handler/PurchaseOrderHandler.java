package pharma.Handler;



import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import pharma.Model.FieldData;
import pharma.config.*;
import pharma.config.View.ComboxLotsConvert;
import pharma.config.View.InvoicexConvert;
import pharma.config.spinner.SpinnerTableCellOrder;
import pharma.dao.*;
import pharma.javafxlib.Controls.TextFieldComboBox;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.javafxlib.DoubleClick_Menu;
import pharma.javafxlib.RadioOptions;

import java.sql.Date;
import java.util.*;


public class PurchaseOrderHandler extends DialogHandler<FieldData> {
    private PurchaseOrderDao purchaseOrderDao;
    private PurchaseOrderDetailDao purchaseOrderDetailDao;
    private DatePicker date_order;
    private ObservableList<FieldData> table_obs;
    private ObservableList<FieldData> list_populate;
private  SimpleBooleanProperty s_update_result;
    private  TextFieldComboBox<FieldData> combotextfield_lot;
    private  TableView<FieldData> tableView;
    private TableColumn<FieldData,Double> price;
    private  TextFieldComboBox<FieldData> combotextfield_farmaco;
    private  Label label_subtotale;
    private  Label label_iva;
    private  Label label_totale;
    private MenuItem menuItem;
    private ToggleGroup group;
    private TextFieldComboBox<FieldData> combox_pharma;
    private  ObservableList<FieldData> current_farmaco;
    private  ObservableList<FieldData> current_lot_farmaco;
    private SimpleBooleanProperty update_property;
    private  TextField textField_ordini;
    public PurchaseOrderHandler(LottiDao lottiDao,PurchaseOrderDao purchaseOrderDao,PurchaseOrderDetailDao p_dao,SimpleBooleanProperty update_property, PharmaDao pharmaDao) {
        super("Aggiungi Ordini", new PopulateChoice(lottiDao), Arrays.asList(lottiDao,pharmaDao));
        getDialogPane().setPrefHeight(900);
        getDialogPane().setPrefWidth(1200);
        this.purchaseOrderDao=purchaseOrderDao;
        this.purchaseOrderDetailDao=p_dao;
        this.update_property=update_property;
    }




    @Override
    protected  <K>void  initialize(Optional<PopulateChoice<K>> optionalpopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {

            if (optionalgenericJDBCDao.isPresent()) {

                List<GenericJDBCDao> genericJDBCDao = optionalgenericJDBCDao.get();
                LottiDao lottidao = (LottiDao) genericJDBCDao.stream().filter(dao -> dao instanceof LottiDao).toList().getFirst();
                PharmaDao pharmaDao = (PharmaDao) genericJDBCDao.stream().filter(dao -> dao instanceof PharmaDao).toList().getFirst();
                s_update_result=new SimpleBooleanProperty(false);
                table_obs = FXCollections.observableArrayList();
                list_populate = FXCollections.observableArrayList();
                current_farmaco = FXCollections.observableArrayList();
                current_lot_farmaco = FXCollections.observableArrayList();
                add_label("Inserisci Data");
                date_order = add_calendar();
                textField_ordini = add_text_field("Inserisci ID Ordini Fornitore");
                add_label("Cerca o Seleziona Casa Farmaceutica");
                combox_pharma = add_combox_search_with_textfield(FXCollections.observableArrayList(pharmaDao.findAllName()));
                combox_pharma.setConvert(new InvoicexConvert(InvoicexConvert.Type.combo_id
                ));

                group = add_radios(Arrays.asList(new RadioOptions("","Lotto"), new RadioOptions("","Farmaco")), CustomDialog.Mode.Horizontal);
                group.getToggles().getFirst().setSelected(true);

                combotextfield_farmaco = add_combox_search_with_textfield(FXCollections.observableArrayList());
                combotextfield_farmaco.setVisible(false);
                combotextfield_farmaco.setConvert(new ComboxLotsConvert("Farmaco"));


                combotextfield_lot = add_combox_search_with_textfield(FXCollections.observableArrayList());
                combotextfield_lot.setUserData("lots");
                combotextfield_lot.setConvert(new ComboxLotsConvert("Lotto"));
                getControlList().removeAll( combotextfield_lot,combotextfield_farmaco);
                tableView = add_table();
                listen_combo_pharma(lottidao);
                listener_combo_farmaco();
                tableView.setItems(table_obs);
                add_column();
                getButtonOK().setText("Conferma Ordine");
                label_subtotale = add_label("SubTotale");
                label_iva = add_label("IVA");
                label_totale = add_label("Totale");

                listener_group();
                listener_boolean_change();


                DoubleClick_Menu<FieldData> doubleClickMenu = new DoubleClick_Menu<>(tableView);

                menuItem = doubleClickMenu.create_menu_item("Elimina Riga");
                listen_menu_item_remove(menuItem);
                listen_combo();
            }


        }



    @Override
    protected FieldData get_return_data() {
        FieldData fieldData=(FieldData) combox_pharma.getChoiceBox().getValue();
     return FieldData.FieldDataBuilder.getbuilder().
               setProduction_date(Date.valueOf(date_order.getValue())).
               setSubtotal((Double) label_subtotale.getUserData()).
               setVat_amount((Double)label_iva.getUserData()).
               setTotal((Double) label_totale.getUserData()).
               setFieldDataListAll(tableView.getItems()).
               setOriginal_order_id(textField_ordini.getText()).
               setCasa_Farmaceutica(fieldData.getId())
                       .build();
    }



    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {
        purchaseOrderDao.setTransaction(true);
        int index=purchaseOrderDao.insertAndReturnID(fieldData);
        System.out.println("value: "+index);


        if((index!=-1) && (index!=0)) {

           boolean value= fieldData.getFieldDataList().stream().allMatch(fieldata_detail -> {
                FieldData fieldData_curr = FieldData.FieldDataBuilder.getbuilder().
                        setLotto_id(fieldata_detail.getLotto_id()).
                        setFarmaco_id(fieldata_detail.getFarmaco_id())
                        .setPurchase_order_id(index).
                        setQuantity(fieldata_detail.getQuantity()).
                        setVat_percent(fieldata_detail.getVat_percent()).setPrice(fieldata_detail.getPrice()).build();
                  return purchaseOrderDetailDao.insert(fieldData_curr);
            });
           if(value){
               purchaseOrderDao.commit();
               update_property.set(true);

           }else{
               purchaseOrderDao.rollback();
           }
           return value;





        }else{
            purchaseOrderDao.rollback();
            return  false;
        }





    }


    private  void add_column(){

       TableColumn<FieldData,String> column_lots=TableUtility.generate_column_string("Lotto Code","lotto_id");
        TableColumn<FieldData,String> nome= TableUtility.generate_column_string("Nome Farmaco","nome");
        TableColumn<FieldData,Date> date_elapsed= TableUtility.generate_column_date("Data Produzione ","production_date");
        TableColumn<FieldData,Date> date_production=  TableUtility.generate_column_date("Data Scadenza ","elapsed_date");
       price=TableUtility.generate_column_double("Prezzo","price");
       price.setCellFactory(col->new SpinnerTableCellOrder<>(Double.class,1.1,20000.1,0.1,s_update_result,"setPrice"));
       price.setCellFactory(col->new SpinnerTableCellOrder<>(Double.class,1.1,20000.1,0.1,s_update_result,"setPrice"));
        TableColumn<FieldData,Integer> qty=TableUtility.generate_column_int("Quantità","quantity");
        qty.setCellFactory(col->new SpinnerTableCellOrder<>(Integer.class,1,1000,1,s_update_result,"setQuantity"));
        TableColumn<FieldData,String> tipologia=TableUtility.generate_column_string("Tipologia","nome_tipologia");
        TableColumn<FieldData,String> misura=TableUtility.generate_column_string("Misura","unit_misure");
        TableColumn<FieldData,Integer> p_iva=TableUtility.generate_column_int("Iva","vat_percent");
        p_iva.setCellFactory(col->new SpinnerTableCellOrder<>(Integer.class,1,1000,1,s_update_result,"setPercent"));
        tableView.getColumns().addAll(column_lots,TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),date_elapsed,date_production,nome,price,qty,tipologia,misura,p_iva);

    }



private  void listener_boolean_change(){
    s_update_result.addListener((observable, oldValue, newValue) -> {
        if(newValue){
            calculus_attribute();
            s_update_result.set(false);
        }
    });
}





    private void listen_menu_item_remove(MenuItem menuitem){
        menuitem.setOnAction(event -> {
                FieldData fieldData = tableView.getSelectionModel().getSelectedItem();

                if (fieldData != null) {

                    Platform.runLater(() -> {


                        table_obs.remove(fieldData);
                        list_populate.add(fieldData);

                        tableView.refresh();

                        calculus_attribute();
                        if(tableView.getItems().isEmpty()){
                            menuitem.setDisable(true);
                            combox_pharma.setDisable(false);

                        }
                    });
                }


        });


    }
    private void listen_combo(){
 combotextfield_lot.getChoiceBox().valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;

     if(newValue instanceof  FieldData fieldData) {
         Platform.runLater(() -> {
    if(tableView.getItems().isEmpty()){
        tableView.setUserData(fieldData.getCasa_farmaceutica());
    }
    if(tableView.getUserData().equals(fieldData.getCasa_farmaceutica())) {



        table_obs.add(fieldData);
        tableView.setItems(table_obs);
        //  combotextfield_lot.getChoiceBox().setValue(FieldData.FieldDataBuilder.getbuilder().setNome("").build());
        list_populate.remove(fieldData);
        calculus_attribute();
        if (menuItem.isDisable()) {
            menuItem.setDisable(false);
        }
        if(tableView.getItems().size()==1){
            combox_pharma.setDisable(true);

        }
    }else{
       Utility.create_alert(Alert.AlertType.WARNING,"Warning","Impossibile inserire articoli con propertari differenti!");

    }
             });
         }

    });






    }
    private void listen_combo_pharma(LottiDao lottiDao){
        combox_pharma.getChoiceBox().valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                if(newValue instanceof FieldData fieldData) {


                if(tableView.getItems().isEmpty()){

                  List<FieldData> fd_list = lottiDao.findLotsbyPharma(fieldData.getId());

                    list_populate.setAll(fd_list);
                    combotextfield_lot.getChoiceBox().setItems(list_populate);
                    Set<String> seenDuplicates = new HashSet<>();
                    List<FieldData> filteredList = fd_list.stream()
                            .filter(fd -> seenDuplicates.add(fd.getNome()))
                            .toList();
                    current_farmaco.setAll(filteredList);
                    combotextfield_farmaco.getChoiceBox().setItems(current_farmaco);

                }
                }



            }




        });


    }

    public void calculus_attribute(){

        Utility.resetLabelText(label_iva,label_subtotale,label_totale);

        double subtotale=ListCalculate.amount_subtotal(tableView.getItems());
        double totalVatAmount=ListCalculate.amount_vat(tableView.getItems());
        double totale=ListCalculate.total(totalVatAmount,subtotale);

        label_subtotale.setUserData(subtotale);
        label_iva.setUserData(totalVatAmount);
        label_totale.setUserData(totale);
        label_iva.setText(label_iva.getText()+":  "+String.format("%.2f", totalVatAmount));
        label_totale.setText(label_totale.getText()+": "+String.format("%.2f",totale));
        label_subtotale.setText(label_subtotale.getText()+": "+String.format("%.2f",subtotale));
    }



    public  void listener_combo_farmaco(){

    combotextfield_farmaco.getChoiceBox().valueProperty().addListener((observable, oldValue, newValue) -> {
        if(newValue!=null){
            FieldData fieldData=(FieldData) newValue;
            current_lot_farmaco.setAll(list_populate.stream().filter(fd->fd.getNome().equals(fieldData.getNome())).toList());
            combotextfield_lot.getChoiceBox().setItems(current_lot_farmaco);








        }
    });





    }
    private  void listener_group(){
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton radioButton= (RadioButton) newValue;
                if(radioButton.getText()!=null){

                    if(radioButton.getText().equals("Farmaco")){
                        combotextfield_farmaco.setVisible(true);
                    }else{
                        combotextfield_farmaco.setVisible(false);
                        combotextfield_lot.getChoiceBox().setItems(list_populate);


                    }
                }



            }
        });


    }


    @Override
    protected void initialize() {

    }





}
