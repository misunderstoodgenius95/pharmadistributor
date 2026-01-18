package pharma.Controller.subpanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pharma.DialogController.CustomHandlerController;
import pharma.Model.FieldData;

import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.dao.SellerInvoiceDao;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class SellerInvoice implements Initializable {
    public Button send_btn_id;
    @FXML
    private TableView<FieldData> table_id;
    @FXML
    private TextField tf_multi_id;
    @FXML
    private DatePicker range_start_id;
    @FXML
    private DatePicker range_end_id;
    @FXML
    private ToggleGroup searchToggleGroup;
    private SellerInvoiceDao sellerInvoiceDao;
    private CustomHandlerController s_controller;
    private ObservableList<FieldData> observableList;


    public SellerInvoice(SellerInvoiceDao sellerInvoiceDao) {

        this.sellerInvoiceDao = sellerInvoiceDao;
    }

    @FXML
    public void action_send_btn(ActionEvent event) {
        if(s_controller.btn_validate()){
      if(searchToggleGroup.getSelectedToggle() instanceof RadioButton radioButton){
          if(radioButton.getId().equals("all")){
              observableList.setAll(sellerInvoiceDao.findAll());
          }
         else if(radioButton.getId().equals("header_range")){
              observableList.setAll(sellerInvoiceDao.findByRangeBetweenAndRagioneSociale(Date.valueOf(range_start_id.getValue()), Date.valueOf(range_end_id.getValue()),tf_multi_id.getText()));
          }
         else if(radioButton.getId().equals("only_id")) {
              observableList.setAll(sellerInvoiceDao.findByOrderID(Integer.parseInt(tf_multi_id.getText())));
          }
      }

        }else{
            Utility.create_alert(Alert.AlertType.WARNING, "Attenzione!", " Riempire tutti campi!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableList= FXCollections.observableArrayList();
        s_controller=new CustomHandlerController(searchToggleGroup,tf_multi_id,range_start_id,range_end_id);
        table_id.getColumns().addAll(
                TableUtility.generate_column_int("Numero fattura","id"),
                TableUtility.generate_column_string("Ragione Sociale","nome_casa_farmaceutica"),
                TableUtility.generate_column_double("Subtotale","subtotal"),
                TableUtility.generate_column_double("Iva","vat_amount"),
                TableUtility.generate_column_double("Totale","total"),
                TableUtility.generate_column_date("Data","elapsed_date"));
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table_id.setItems(observableList);


    }


}
