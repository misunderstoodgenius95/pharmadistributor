package pharma.DialogController;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.TestOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.DialogController.Table.OrderTableView;
import pharma.Model.FieldData;
import pharma.config.*;

import pharma.config.spinner.SpinnerTableCellOrder;
import pharma.dao.*;
import pharma.javafxlib.CustomTableView.RadioButtonTableColumn;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.javafxlib.RadioOptions;
import pharma.javafxlib.Status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SellerOrderControllerBase extends DialogControllerBase<FieldData> {
    private static final Logger log = LoggerFactory.getLogger(SellerOrderControllerBase.class);
    private TextField textField_search;
    private ToggleGroup group_choice;
    private Button search_btn;
    private OrderTableView orderTableView;
    private RadioButtonTableColumn<FieldData> radioButtonTableColumn;
    private SellerOrderDao s_dao;
    private SellerOrderDetails s_details;
    private SellerInvoiceDao s_invoice;
    private SellerCreditNoteDao sellerCreditNoteDao;
    private SellerCreditNoteDetailDao s_credit_detail;
    private DatePicker range_start;
    private DatePicker range_end;
    private TableView<FieldData> table_invoice;
    private TextField tf_order_id;
    private TextField tf_date;
    private TextField tf_header;
    private TextField tf_total;
    private SimpleIntegerProperty s_invoice_id;
    private ObservableList<FieldData> obs_invoice;
    private SimpleIntegerProperty s_update;
    private Label label_subtotale;
    private Label label_iva;
    private Label label_totale;


    public SellerOrderControllerBase(String content, SellerOrderDao s_dao, SellerOrderDetails s_details, SellerInvoiceDao s_invoice, SellerCreditNoteDao s_credit, SellerCreditNoteDetailDao s_credit_detail) {
        super(content, List.of(s_dao, s_details));
        getDialogPane().setPrefHeight(800);
        getDialogPane().setPrefWidth(900);
        orderTableView = new OrderTableView("Seleziona Ordine");
        radioButtonTableColumn=orderTableView.add_radio();
        this.s_dao = s_dao;
        this.s_details = s_details;
        setting_listener_checkbox();
        this.s_details = s_details;
        obs_invoice = FXCollections.observableArrayList();
        s_invoice_id = new SimpleIntegerProperty(-1);
        this.s_invoice = s_invoice;
        this.sellerCreditNoteDao = s_credit;
        this.s_credit_detail =s_credit_detail;
    }

    public RadioButtonTableColumn<FieldData> getRadioButtonTableColumn() {
        return radioButtonTableColumn;
    }

    public ToggleGroup getGroup_choice() {
        return group_choice;
    }

    public OrderTableView getOrderTableView() {
        return orderTableView;
    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {
        group_choice = add_radios(List.of(new RadioOptions("order_only", "Ordine"),
                        new RadioOptions("header_range", "Intestazione e Date"),
                        new RadioOptions("all", "Tutti PurchaseOrdini")),
                CustomDialog.Mode.Horizontal);
        textField_search = add_text_field("Cerca Prodotto");
        range_start = add_calendar();
        range_start.setDisable(true);
        s_update = new SimpleIntegerProperty();
        s_update.set(-1);
        range_end = add_calendar();
        range_end.setDisable(true);
        search_btn = addButton("Seleziona ordine");
        setting_header_invoice();
        search_btn.setDisable(true);
        send_order();
        listener_choice();
        listener_textfield();
        listener_range_end();
        add_label("Ciao", new HBox());
        table_invoice = add_table();
        setting_column_tb_invoice();
        listener_spinner();
        label_subtotale = add_label("SubTotale");
        label_iva = add_label("IVA");
        label_totale = add_label("Totale");
    }
    @TestOnly
    public Button getSearch_btn() {
        return search_btn;
    }

    /**
     * Setting
     */
    private void setting_header_invoice() {
        GridPane gridPane = add_gridpane(1);
        tf_order_id = createLabeledTextField("Id Ordine", "", gridPane, 0, 0, 0, 1);
        tf_date = createLabeledTextField("Data Ordine", "", gridPane, 1, 0, 1, 1);
        tf_header = createLabeledTextField("Ragione Sociale", "", gridPane, 2, 0, 2, 1);
        tf_total = createLabeledTextField("Totale", "", gridPane, 3, 0, 3, 1);
    }

    private void setting_column_tb_invoice() {
        TableColumn<FieldData, Double> tc_price = TableUtility.generate_column_double("Prezzo", "price");
        TableColumn<FieldData, Integer> tc_quantity = TableUtility.generate_column_int("Quantità", "quantity");
        tc_price.setCellFactory(col -> new SpinnerTableCellOrder<Double>(Double.class, s_update, "setPrice"));
        tc_quantity.setCellFactory(col -> new SpinnerTableCellOrder<Integer>(Integer.class, s_update, "setQuantity"));
        table_invoice.getColumns().addAll(TableUtility.generate_column_int("Nome", "nome"), tc_price, tc_quantity,
                TableUtility.generate_column_int("Iva", "vat_percent"));

    }

    /**
     * Listener
     */
    public void listener_textfield() {
        textField_search.setOnKeyPressed(event -> {
            if (group_choice.getSelectedToggle() != null) {
                search_btn.setDisable(textField_search.getText().isEmpty());
            }
        });

    }


    public void listener_range_end() {
        range_end.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if ((newValue != null && range_start.getValue() != null) && (!textField_search.getText().isEmpty())) {
                    search_btn.setDisable(false);
                } else {
                    search_btn.setDisable(true);
                }
            }
        });

    }


    /**
     * Radio button choice Listener
     */
    public void listener_choice() {
        group_choice.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof RadioButton radioButton) {
                if (radioButton.getId().equalsIgnoreCase("all")) {
                    search_btn.setDisable(false);
                    textField_search.setDisable(true);
                    removeControls(textField_search);
                    removeControls(range_start);
                    removeControls(range_end);
                     // Order
                } else {
                    search_btn.setDisable(true);
                    textField_search.setDisable(false);
                    addControls(textField_search);
                }
             if (radioButton.getId().equalsIgnoreCase("header_range")) {
                    range_start.setDisable(false);
                    range_end.setDisable(false);
                    textField_search.setPromptText("Inserisci Farmacia");
                    addControls(textField_search);
                    addControls(range_start);
                    addControls(range_end);
                }else {
                 System.out.println("me");
                    range_start.setDisable(true);
                    range_end.setDisable(true);
                    textField_search.setPromptText("");
                 removeControls(range_start);
                 removeControls(range_end);
                 removeControls(textField_search);
                }


            }
        });
    }

    public void listener_spinner() {
        s_update.addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(-1)) {
                Utility.resetLabelText(label_iva, label_subtotale, label_totale);
                double subtotale = ListCalculate.amount_subtotal(table_invoice.getItems());
                double totalVatAmount = ListCalculate.amount_vat(table_invoice.getItems());
                double totale = ListCalculate.total(totalVatAmount, subtotale);
                label_subtotale.setUserData(subtotale);
                label_iva.setUserData(totalVatAmount);
                label_totale.setUserData(totale);
                label_iva.setText(label_iva.getText() + ":  " + String.format("%.2f", totalVatAmount));
                label_totale.setText(label_totale.getText() + ": " + String.format("%.2f", totale));
                label_subtotale.setText(label_subtotale.getText() + ": " + String.format("%.2f", subtotale));
            }
            s_update.set(-1);

        });

    }


    /**
     * Action Button & Change Listener
     */

    /**
     * Search Button-> Button when selected it can be executed the query radio.
     */
    public void send_order() {
        search_btn.setOnAction(event -> {
            RadioButton radioButton = (RadioButton) group_choice.getSelectedToggle().getToggleGroup().getSelectedToggle();
            String id = radioButton.getId();
            execute_query(id, orderTableView.getTableView());
            orderTableView.show();
        });
    }

    /**
     * Obtein the row selected and find the items that addedd into invoice table
     */
    public void setting_listener_checkbox() {
        orderTableView.getRadio_value().addListener((ChangeListener<FieldData>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                tf_order_id.setText(String.valueOf(newValue.getId()));
                tf_date.setText(newValue.getElapsed_date().toString());
                tf_header.setText(newValue.getNome_casa_farmaceutica());
                tf_total.setText(String.valueOf(newValue.getTotal()));
                obs_invoice.setAll(s_details.findbyOrderId(newValue.getId()));
                s_invoice_id.setValue(s_invoice.findByOrderID(newValue.getId()).getId());
                table_invoice.setItems(obs_invoice);
            }
        });

    }


    /**
     * On Table Dialog Search
     * @param id
     * @param tableView
     */
    public void execute_query(String id, TableView<FieldData> tableView) {
        ObservableList<FieldData> observableList = FXCollections.observableArrayList();
        switch (id) {
            case "all" -> {
                observableList.addAll(s_dao.findAllOrderNotExistinSellerOrder());
            }
            case "order_only" -> {
                if (!isValidInteger(textField_search.getText())) {
                    Utility.create_alert(Alert.AlertType.WARNING, "WARNING", "Inserire un numero intero");
                } else {
                    observableList.addAll(s_dao.findByIdWhereNotExistintoSellerCreditNote(Integer.valueOf(textField_search.getText())));
                }
            }
            case "header_range" -> {
                observableList.addAll(s_dao.findByRangeBetweenAndRagioneSocialeWhereNotExistCreditNote(Date.valueOf(range_start.getValue()), Date.valueOf(range_end.getValue()), textField_search.getText()));
            }
            default -> {
                throw new IllegalArgumentException("Not found id!");
            }
        }
        tableView.setItems(observableList);


    }


    private boolean isValidInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected FieldData get_return_data() {
        return FieldData.FieldDataBuilder.getbuilder().
                setForeign_id(s_invoice_id.get()).
                setVat_amount((Double) label_iva.getUserData()).
                setSubtotal((Double) label_subtotale.getUserData()).
                setTotal((Double) label_totale.getUserData()).
                setFieldDataListAll(obs_invoice).build();
    }

    @Override
    protected boolean condition_event(FieldData type) throws Exception {

        sellerCreditNoteDao.setTransaction(true);
        int index_credit_note = sellerCreditNoteDao.insertAndReturnID(type);

        if ((index_credit_note != -1) && (index_credit_note != 0)) {

            boolean result= type.getFieldDataList().stream().allMatch(fieldata_detail -> {
                FieldData fieldData_insert_details=
                        FieldData.FieldDataBuilder.getbuilder().
                         setInvoice_id(index_credit_note).
                        setQuantity(fieldata_detail.getQuantity()).
                        setVat_percent(fieldata_detail.getVat_percent()).
                        setPrice(fieldata_detail.getPrice()).
                        setFarmaco_id(fieldata_detail.getFarmaco_id()).
                        build();
                return  s_credit_detail.insert(fieldData_insert_details);
            });
            if(result){
                sellerCreditNoteDao.commit();
            }else{
                sellerCreditNoteDao.rollback();
            }
            return result;

        }else{
            log.warn("Not passing");
        }
        return false;


    }



}
