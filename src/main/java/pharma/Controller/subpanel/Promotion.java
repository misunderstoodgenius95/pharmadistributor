package pharma.Controller.subpanel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import pharma.DialogController.SuggestPromotionControllerBase;
import pharma.Handler.PromotionHandler;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.PathConfig;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.config.database.Database;
import pharma.dao.*;
import pharma.Service.Promotion.PromotionsSuggestService;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class Promotion implements Initializable {
    @FXML
    public TableView<FieldData> table_id;
    @FXML
    public Button btn_id_add;

    SellerOrderDetails s_detail;
    PurchaseOrderDetailDao p_detail;
    LottiDao lottiDao;
    LotAssigmentDao assigmentDao;
    SellerPriceDao s_dao;
    private ObservableList<FieldData> observable_table_id;
    private PromotionsSuggestService suggestion;
    private PromotionDao promotionDao;
    private SimpleBooleanProperty s_boolean;
    private SuggestPromotionControllerBase suggestPromotionDialog;
    private PromotionHandler promotionHandler;
    public Promotion() {

        s_boolean=new SimpleBooleanProperty(false);
        Properties properties = null;
        HashMap<String,String> properties_suggestion=null;
        URI uri=null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader(PathConfig.DATABASE_CONF.getValue()));
            s_detail=new SellerOrderDetails(Database.getInstance(properties));
            p_detail=new PurchaseOrderDetailDao(Database.getInstance(properties));
            lottiDao=new LottiDao(Database.getInstance(properties),"lotto");
            assigmentDao=new LotAssigmentDao(Database.getInstance(properties));
            s_dao=new SellerPriceDao(Database.getInstance(properties));
            promotionDao=new PromotionDao(Database.getInstance(properties));
            promotionHandler=new PromotionHandler(p_detail,s_detail,lottiDao,assigmentDao,s_dao);
             suggestPromotionDialog=new SuggestPromotionControllerBase("Promozione",s_dao,promotionHandler,promotionDao,s_boolean);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //   suggestion=new PriceSuggestion();

    }
    @FXML
    public void btn_action_add(ActionEvent event) {
        suggestPromotionDialog.execute();
        if(s_boolean.get()){
            observable_table_id.clear();
            observable_table_id.addAll(promotionDao.findAll());
            table_id.setItems(observable_table_id);


        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observable_table_id=FXCollections.observableArrayList();
        Utility.add_iconButton(btn_id_add, FontAwesomeSolid.PLUS_SQUARE);
        table_id.getColumns().addAll(TableUtility.generate_column_string("Farmaco","nome"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),
                TableUtility.generate_column_int("Quantity","quantity"),
                TableUtility.generate_column_int("Prezzo","price"),
                TableUtility.generate_column_int("Sconto","discount_value"),
                TableUtility.generate_column_int("Data Inizio","production_date"),
                TableUtility.generate_column_int("Data Fine","elapsed_date")
        );
        observable_table_id.addAll(promotionDao.findAll());
        table_id.setItems(observable_table_id);








    }

}
