package pharma.Controller.subpanel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.table.TableRowExpanderColumn;
import pharma.Handler.PurchaseOrderHandler;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.Database;
import pharma.config.TableUtility;
import pharma.dao.LottiDao;
import pharma.dao.PharmaDao;
import pharma.dao.PurchaseOrderDao;
import pharma.dao.PurchaseOrderDetailDao;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Ordini implements Initializable {


    @FXML
    public TableView<FieldData> table_id;
    public TableView<FieldData> t_expanded;
    private PurchaseOrderDao purchaseOrderDao;
    private PurchaseOrderDetailDao p_detail;
    private ObservableList<FieldData> obs_table_fd_details;
    private LottiDao lottiDao;
    private ObservableList<FieldData> obs_table;
    private SimpleBooleanProperty s_update;
    private PharmaDao pharmaDao;
    private List<VBox> vBoxes;

    public Ordini() {
        try {
            Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            purchaseOrderDao = new PurchaseOrderDao(Database.getInstance(properties));
            p_detail = new PurchaseOrderDetailDao(Database.getInstance(properties));
            lottiDao = new LottiDao(Database.getInstance(properties), "lotto");
            pharmaDao = new PharmaDao(Database.getInstance(properties));
            vBoxes = new ArrayList<>();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Ordini(PurchaseOrderDao purchaseOrderDao, LottiDao lottiDao, PharmaDao pharmaDao) {

        this.purchaseOrderDao=purchaseOrderDao;
        this.pharmaDao=pharmaDao;
        this.lottiDao=lottiDao;

        vBoxes = new ArrayList<>();



    }



    public TableView<FieldData> getTable_id() {
        return table_id;
    }


    public void btn_action_add(ActionEvent event) {

        PurchaseOrderHandler purchaseOrderHandler=new PurchaseOrderHandler(lottiDao,purchaseOrderDao,p_detail,s_update,pharmaDao);

        purchaseOrderHandler.execute();

    }


    private VBox createExpandendRow(TableRowExpanderColumn.TableRowDataFeatures<FieldData> param){
        TableView<FieldData> t_expanded=new TableView<>();
        FieldData fd_order=param.getValue();
        t_expanded.getColumns().addAll(
                TableUtility.generate_column_string("Lotto","lotto_id"),
                TableUtility.generate_column_string("Farmaco","nome_farmaco"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),
                TableUtility.generate_column_double("Pezzo","price"),
                TableUtility.generate_column_double("Quantità","quantity"),
                TableUtility.generate_column_int("Iva","vat_percent")
        );

        obs_table_fd_details.setAll(p_detail.findDetailbyPurchaseOrderId(fd_order.getId()));

        t_expanded.setItems(obs_table_fd_details);
        t_expanded.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #636165;");
        t_expanded.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        return new VBox(t_expanded);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obs_table_fd_details=FXCollections.observableArrayList();
        obs_table=FXCollections.observableArrayList();
        TableRowExpanderColumn<FieldData>expanderColumn=new TableRowExpanderColumn<>(this::createExpandendRow);
        TableColumn<FieldData,Double> col_subtotal=TableUtility.generate_column_double("Subtotale","subtotal");
        TableUtility.formatting_double(col_subtotal);
        TableColumn<FieldData,Double> col_vat=TableUtility.generate_column_double("Iva","vat_amount");
        TableUtility.formatting_double(col_vat);
        TableColumn<FieldData,Double> col_total=TableUtility.generate_column_double("Totale","total");
        TableUtility.formatting_double(col_total);
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
               table_id.getColumns().addAll(expanderColumn,
                       TableUtility.generate_column_string("Ordine ID","id"),
                TableUtility.generate_column_string("Data Ordine","production_date"),
                TableUtility.generate_column_string("Id Ordine Fornitore","original_order_id"),
                TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),
                       col_subtotal,col_total,col_vat);

        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        setvalue();


        table_id.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #636165;");
        s_update=new SimpleBooleanProperty(false);
        s_update.addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(true)){
           setvalue();
            }
        });


    }

    private  void setvalue(){


        obs_table.setAll( purchaseOrderDao.findAll().stream().
                peek( fieldData -> fieldData.setNome_casa_farmaceutica(pharmaDao.findById(fieldData.getCasa_farmaceutica()).getNome_casa_farmaceutica())).toList());
        table_id.setItems(obs_table);
    }
}
