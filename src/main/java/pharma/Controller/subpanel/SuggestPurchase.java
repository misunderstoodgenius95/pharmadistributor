package pharma.Controller.subpanel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import kotlin.jvm.internal.PackageReference;
import org.controlsfx.control.SearchableComboBox;
import org.jetbrains.annotations.TestOnly;
import pharma.Handler.Table.ProductTableCustom;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.CatConf.CatConf;
import pharma.config.database.Database;
import pharma.dao.*;
import pharma.formula.suggest.Model.Lots;
import pharma.formula.suggest.Model.SellerOrders;
import pharma.formula.suggest.Model.SuggestConfig;
import pharma.formula.suggest.SingleProductSuggest;
import pharma.javafxlib.CustomTableView.RadioButtonTableColumn;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SuggestPurchase implements Initializable {

    public Button btn_send_id;
    @FXML
    public Label label_response;
    @FXML
    AnchorPane anchor_id;
    @FXML
    public TextField text_single;

    private FarmacoDao farmacoDao;
    private ProductTableCustom productTableCustom;
    private SingleProductSuggest singleProductSuggest;
    private LotAssigmentDao lotAssigmentDao;
    private SuggestConfig suggestConfig;
    private CatConf catConf;
    private SellerOrderDao sellerOrderDao;

    @TestOnly
    public SuggestPurchase(FarmacoDao farmacoDao, LotAssigmentDao assigmentDao, SuggestConfig suggestConfig, SellerOrderDao sellerOrderDao) {
        this.farmacoDao=farmacoDao;
        this.lotAssigmentDao=assigmentDao;
        config_table();
        this.suggestConfig=suggestConfig;
        this.sellerOrderDao=sellerOrderDao;


    }
    public SuggestPurchase() {
        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            farmacoDao=new FarmacoDao(Database.getInstance(properties));
            lotAssigmentDao=new LotAssigmentDao(Database.getInstance(properties));
            catConf=new CatConf("configcat-sdk-1/LxreCILqCUKPiPgevSQGoQ/w1WIJVMWoUOKocMj7FderA");
            suggestConfig=new SuggestConfig(catConf.get_value_integer("maximumexpreday"),
            catConf.get_value_integer("maximumavailability"),
            catConf.get_value_integer("minimumsellerorder"));
            sellerOrderDao=new SellerOrderDao(Database.getInstance(properties));
            config_table();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void config_table(){

        productTableCustom=new ProductTableCustom("Inserisci tabella");
        productTableCustom.add_radio();
        productTableCustom.getButtonOK().setOnAction(event -> {
        FieldData fieldData=productTableCustom.getRadio_value().getValue();
            if(fieldData!=null){
                List<Lots> list_lot=convert_fd_lots(lotAssigmentDao.findQuantitybyFarmacoId(fieldData.getFarmaco_id()));
                List<SellerOrders> sellerOrders=convert_fs_orders(sellerOrderDao.findByOrders(fieldData.getFarmaco_id()));
                singleProductSuggest=new SingleProductSuggest(list_lot,suggestConfig,sellerOrders);
                boolean value=singleProductSuggest.calculate_suggest();
                label_response.setText(view_suggest(value));
        }
        });


    }
    private String view_suggest(boolean value){
        if(value){
            return " Suggerimento: Acquista il prodotto";
        }else{
            return "Suggerimento: Non Acquista il prodotto";
        }
    }


    private List<SellerOrders> convert_fs_orders(List<FieldData> fieldData){
        return fieldData.stream().map(fd->new SellerOrders(fd.getFarmaco_id(),fd.getQuantity(),fd.getElapsed_date())).toList();
    }
    private List<Lots> convert_fd_lots(List<FieldData> fdlist){
        return fdlist.stream().map(fieldData -> new Lots(fieldData.getCode(),fieldData.getFarmaco_id(),fieldData.getProduction_date(),fieldData.getAvailability())).toList();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {







    }
        @FXML
    public void send_action_btn(ActionEvent event) {
        if(!text_single.getText().isEmpty()){
            productTableCustom.getTableView().getItems().setAll(farmacoDao.findByLikeName(text_single.getText()));
            productTableCustom.show();


        }


    }
}
