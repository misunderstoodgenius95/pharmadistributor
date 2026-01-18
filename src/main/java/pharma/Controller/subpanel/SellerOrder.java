package pharma.Controller.subpanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.controlsfx.control.table.TableRowExpanderColumn;
import pharma.DialogController.CustomHandlerController;
import pharma.Model.FieldData;

import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.dao.SellerOrderDao;
import pharma.dao.SellerOrderDetails;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class SellerOrder implements Initializable {
    public ToggleGroup searchToggleGroup;
    public TextField tf_multi_id;
    public DatePicker range_start_id;
    public DatePicker range_end_id;
    public TableView table_id;
    private SellerOrderDao sellerOrderDao;
    private SellerOrderDetails sellerOrderDetails;
    private CustomHandlerController handlerController;
    private ObservableList<FieldData> observableList;
    private  ObservableList<FieldData> obs_table_fd_details;

    public SellerOrder(SellerOrderDao sellerOrderDao,SellerOrderDetails sellerOrderDetails) {
      this.sellerOrderDao=sellerOrderDao;
      this.sellerOrderDetails=sellerOrderDetails;

    }
        @FXML
    public void action_send_btn(ActionEvent event) {
        if(handlerController.btn_validate()){
            System.out.println("validate");
            if(searchToggleGroup.getSelectedToggle() instanceof RadioButton radioButton){
                if(radioButton.getId().equals("all")){
                    observableList.setAll(sellerOrderDao.findAllWithPharmaName());
                }
                else if(radioButton.getId().equals("header_range")){
                    observableList.setAll(sellerOrderDao.findByRangeBetweenAndRagioneSociale(Date.valueOf(range_start_id.getValue()), Date.valueOf(range_end_id.getValue()),tf_multi_id.getText()));
                }
                else if(radioButton.getId().equals("only_id")) {
                    observableList.setAll(sellerOrderDao.findById(Integer.parseInt(tf_multi_id.getText())));
                }
            }

        }else{
            Utility.create_alert(Alert.AlertType.WARNING, "Attenzione!", " Riempire tutti campi!");
        }


    }

    private VBox createExpandendRow(TableRowExpanderColumn.TableRowDataFeatures<FieldData> param){
        TableView<FieldData> t_expanded=new TableView<>();
        FieldData fd_order=param.getValue();
        t_expanded.getColumns().addAll(
                TableUtility.generate_column_string("Farmaco","nome_farmaco"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),
                TableUtility.generate_column_double("Pezzo","price"),
                TableUtility.generate_column_double("Quantità","quantity"),
                TableUtility.generate_column_int("Iva","vat_percent")
        );


        obs_table_fd_details.setAll(sellerOrderDetails.findDetailbySellerOrderId(fd_order.getId()));

        t_expanded.setItems(obs_table_fd_details);
        t_expanded.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #636165;");
        t_expanded.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        return new VBox(t_expanded);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableList= FXCollections.observableArrayList();
        obs_table_fd_details=FXCollections.observableArrayList();
        TableRowExpanderColumn<FieldData>expanderColumn=new TableRowExpanderColumn<>(this::createExpandendRow);
        handlerController=new CustomHandlerController(searchToggleGroup,tf_multi_id,range_start_id,range_end_id);
        table_id.getColumns().addAll(expanderColumn,
                TableUtility.generate_column_int("Ordine Id","id"),
                TableUtility.generate_column_string("Ragione Sociale","nome_casa_farmaceutica"),
                TableUtility.generate_column_double("Subtotale","subtotal"),
                TableUtility.generate_column_double("Iva","vat_amount"),
                TableUtility.generate_column_double("Totale","total"),
                TableUtility.generate_column_date("Data","elapsed_date"));
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table_id.setItems(observableList);

    }

    public void editAction(ActionEvent event) {

    }
}
