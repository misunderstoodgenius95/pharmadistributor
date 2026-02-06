package pharma.DialogController;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import org.apache.commons.math3.distribution.FDistribution;
import org.controlsfx.control.SearchableComboBox;
import pharma.Model.FieldData;
import pharma.config.*;
import pharma.config.ListCalculate;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.config.View.BasicConvert_FD;
import pharma.config.View.InvoicexConvert;
import pharma.dao.GenericJDBCDao;
import pharma.dao.PharmaDao;
import pharma.dao.PurchaseInvoiceDao;
import pharma.dao.PurchaseOrderDao;
import pharma.javafxlib.Controls.TextFieldComboBox;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.javafxlib.DoubleClick_Menu;
import pharma.javafxlib.RadioOptions;
import pharma.javafxlib.Status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PurchaseInvoiceControllerBase extends DialogControllerBase<FieldData> {
    private DatePicker date_invoice;
    private ObservableList<FieldData> list_populate;
    private TextField textField_number_invoice;
    private SearchableComboBox<FieldData> choice_payment;
    private ToggleGroup toggleGroup_order_choice;
    private TextFieldComboBox<FieldData> comboBox_order;
    private  TextFieldComboBox<FieldData> combo_pharma;

    private  TableView<FieldData> table_id;
    private  ObservableList<FieldData> obs_table;
    private  MenuItem menuItem;
    private Label label_subtotale;
    private  Label label_iva;
    private  Label label_totale;
    private PurchaseInvoiceDao p_invoice_dao;
    private  PurchaseOrderDao p_dao;
    private SimpleBooleanProperty simpleBooleanProperty;
    public PurchaseInvoiceControllerBase(PharmaDao pharmaDao, PurchaseInvoiceDao p_inv_dao, PurchaseOrderDao p_dao, SimpleBooleanProperty s_update) {
        super("purchase_invoice", Arrays.asList(pharmaDao,p_dao));
        this.getDialogPane().setPrefHeight(1000);
        this.getDialogPane().setPrefWidth(900);
        p_invoice_dao=p_inv_dao;
        this.p_dao=p_dao;
        this.simpleBooleanProperty=s_update;

    }

    public PurchaseInvoiceControllerBase(PurchaseInvoiceDao p_invoice_dao, PurchaseOrderDao p_dao) {
        super("Content");
        this.p_dao=p_dao;
        this.p_invoice_dao=p_invoice_dao;
    }
    @Override
    protected  <K>void  initialize(Optional<PopulateChoice<K>> optionalpopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {
        if(optionalgenericJDBCDao.isPresent()) {
            List<GenericJDBCDao> genericJDBCDao=optionalgenericJDBCDao.get();
            obs_table = FXCollections.observableArrayList();
            PharmaDao pharmaDao = (PharmaDao) genericJDBCDao.stream().
                    filter(dao -> dao instanceof PharmaDao).findFirst().orElseThrow(() -> new IllegalArgumentException("PharmaDao not found in the list"));
            PurchaseOrderDao purchaseOrderDao = (PurchaseOrderDao) genericJDBCDao.stream().
                    filter(dao -> dao instanceof PurchaseOrderDao).findFirst().orElseThrow(() -> new IllegalArgumentException("PurchaseOrderDao not found in the list"));
            list_populate = FXCollections.observableArrayList();
            textField_number_invoice = add_text_field("Inserisci Numero Fattura");
            date_invoice = add_calendar();
            choice_payment = add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli il metodo di pagamento").build());
            choice_payment.setItems(FXCollections.observableArrayList(FieldData.FieldDataBuilder.getbuilder().setNome("Bonifico").build(),
                    FieldData.FieldDataBuilder.getbuilder().setNome("RD").build(),
                    FieldData.FieldDataBuilder.getbuilder().setNome("RB").build()));
            choice_payment.setConverter(new BasicConvert_FD());
            combo_pharma = add_combox_search_with_textfield(FXCollections.observableArrayList(pharmaDao.findAllName()));
            combo_pharma.getChoiceBox().setValue(FieldData.FieldDataBuilder.getbuilder().setNome_casa_farmaceutica("Seleziona Casa Farmaceutica").build());
            combo_pharma.setPrefWidth(300);
            combo_pharma.setConvert(new InvoicexConvert(InvoicexConvert.Type.combo_id));
            comboBox_order = add_combox_search_with_textfield(FXCollections.observableArrayList());
            comboBox_order.getChoiceBox().setValue(FieldData.FieldDataBuilder.getbuilder().setcode("Seleziona Ordine Id").build());
            comboBox_order.setConvert(new InvoicexConvert(InvoicexConvert.Type.order_id));

            listener_pharma(purchaseOrderDao);
            table_id = add_table();
            table_id.setItems(obs_table);
            add_column();
            listen_order_add_row();

            DoubleClick_Menu<FieldData> doubleClickMenu = new DoubleClick_Menu<>(table_id);

            menuItem = doubleClickMenu.create_menu_item("Elimina Riga");
            listen_menu_item_remove(menuItem);
            label_subtotale = add_label("SubTotale");
            label_iva = add_label("IVA");
            label_totale = add_label("Totale");
            getButtonOK().setText("Crea Fattura");
            getControlList().removeAll(combo_pharma);
        }
    }

    private  void calculus_attribute() {

        resetLabelText(label_iva,label_subtotale,label_totale);

        double subtotale= ListCalculate.sumbySubtotals(table_id.getItems());
        double totalVatAmount= ListCalculate.sumbyVats(table_id.getItems());
        double totale= ListCalculate.sumbyTotals(table_id.getItems());

        label_subtotale.setUserData(subtotale);
        label_iva.setUserData(totalVatAmount);
        label_totale.setUserData(totale);
        label_iva.setText(label_iva.getText()+":  "+String.format("%.2f", totalVatAmount));
        label_totale.setText(label_totale.getText()+": "+String.format("%.2f",totale));
        label_subtotale.setText(label_subtotale.getText()+": "+String.format("%.2f",subtotale));


    }


    private void resetLabelText(Label... labels) {
        for (Label label : labels) {
            label.setText(label.getText().split(":")[0]);
        }
    }


    private  void add_column(){
        TableColumn<FieldData,Double> col_subtotal= TableUtility.generate_column_double("Subtotale","subtotal");
        TableUtility.formatting_double(col_subtotal);
        TableColumn<FieldData,Double> col_vat= TableUtility.generate_column_double("Iva","vat_amount");
        TableUtility.formatting_double(col_vat);
        TableColumn<FieldData,Double> col_total= TableUtility.generate_column_double("Totale","total");
        TableUtility.formatting_double(col_total);
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table_id.getColumns().addAll(
                TableUtility.generate_column_string("Ordine ID","id"),
                TableUtility.generate_column_string("Data Ordine","production_date"),
                TableUtility.generate_column_string("Id Ordine Fornitore","original_order_id"),
                TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),
                col_subtotal,col_total,col_vat);
    }

    private  void listen_order_add_row(){
        ChangeListener<Object> changeListener=((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                FieldData fieldData = (FieldData) newValue;
                if (table_id.getItems().isEmpty()) {
                    table_id.setUserData(fieldData.getCasa_farmaceutica());
                }
                if (table_id.getUserData().equals(fieldData.getCasa_farmaceutica())) {

                    obs_table.add(fieldData);
                    table_id.setItems(obs_table);
                    if(comboBox_order.getChoiceBox().getItems().size()!=1){
                        list_populate.remove(fieldData);
                        comboBox_order.getChoiceBox().setItems(list_populate);
                    }
                    if (menuItem.isDisable()) {
                        menuItem.setDisable(false);
                    }
                    if(table_id.getItems().size()==1){
                        combo_pharma.setDisable(true);
                        comboBox_order.setDisable(true);
                    }

                    calculus_attribute();
                } else {
                    Utility.create_alert(Alert.AlertType.WARNING, "", "Inserimento articolo proviente da una casa farmaceutica differente!");
                }
            }
        });
        comboBox_order.getChoiceBox().getSelectionModel().selectedItemProperty().addListener(changeListener);



    }
    private void listen_menu_item_remove(MenuItem menuitem){
        menuitem.setOnAction(event -> {
            FieldData fieldData = table_id.getSelectionModel().getSelectedItem();
            if (fieldData != null) {
                System.out.println(fieldData.getId());
                Platform.runLater(() -> {
                    obs_table.remove(fieldData);
                    list_populate.add(fieldData);
                    if(table_id.getItems().isEmpty()){
                        menuitem.setDisable(true);
                        combo_pharma.setDisable(false);
                        comboBox_order.setDisable(false);
                    }
                    calculus_attribute();
                });

            }

        });


    }
    private  void listener_pharma(PurchaseOrderDao p_dao){

        combo_pharma.getChoiceBox().valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue instanceof FieldData fieldData){
                if(table_id.getItems().isEmpty()) {
                    list_populate.setAll(p_dao.findAllWithInvoiceIdNullByPharmaId(fieldData.getId()));
                    comboBox_order.getChoiceBox().setItems(list_populate);
                }




            }
        });

    }




    public void listen_toggle(){
        toggleGroup_order_choice.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue instanceof RadioButton radioButton){
                if(radioButton.getId().equals("order_id")){
                    comboBox_order.setVisible(true);
                    combo_pharma.setVisible(true);
                    comboBox_order.getChoiceBox().setItems(list_populate);

                }else{
                    System.out.println("combo id");
                    comboBox_order.setVisible(false);
                    combo_pharma.setVisible(true);



                }


            }
        });

    }

    @Override
    protected FieldData get_return_data() {
        FieldData fieldData_pharma=(FieldData) combo_pharma.getChoiceBox().getValue();

        return FieldData.FieldDataBuilder.getbuilder().
                setInvoice_number(textField_number_invoice.getText()).
                setProduction_date(Date.valueOf(date_invoice.getValue())).
                setPayment_mode(choice_payment.getValue().getNome()).
                setSubtotal((Double) label_subtotale.getUserData()).
                setVat_amount((Double)label_iva.getUserData()).
                setTotal((Double)label_totale.getUserData()).
                setCasa_Farmaceutica(fieldData_pharma.getId()).
                setOrder_id(table_id.getItems().getFirst().getId()).
                setFieldDataListAll(table_id.getItems())
                .build();
    }

    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {;

        p_invoice_dao.setTransaction(true);
        int index=p_invoice_dao.insertAndReturnID(fieldData);

        System.out.println("value: "+index);

        if((index!=-1) && (index!=0)) {
            boolean update_cond=fieldData.getFieldDataList().stream().allMatch(fd_inner -> {
                System.out.println("update value");
                System.out.println(index);
                System.out.println(fd_inner.getOrder_id());
                FieldData fieldData_update = FieldData.FieldDataBuilder.getbuilder().setInvoice_id(index).setOrder_id(fd_inner.getId()).build();
                return p_dao.update(fieldData_update);
            });
            if(update_cond){
                p_dao.commit();
                simpleBooleanProperty.set(true);
                return  true;

            }else{
                p_dao.rollback();
                return  false;
            }
        }else{
            p_invoice_dao.rollback();
        }
        return false;
    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }






    @Override
    protected void initialize() {

    }

}