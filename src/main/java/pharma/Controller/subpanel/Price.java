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
import pharma.Handler.SuggestPriceHandler;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.config.database.Database;
import pharma.config.net.ClientHttp;
import pharma.dao.PurchaseOrderDetailDao;
import pharma.dao.SuggestPriceConfigDao;
import pharma.dao.SellerPriceDao;
import pharma.formula.PriceSuggestion;
import pharma.formula.TrendMarket;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class Price implements Initializable {
    @FXML
    public TableView<FieldData> table_id;
    @FXML
    public Button btn_id_add;

    private SellerPriceDao sellerPriceDao;
    private ObservableList<FieldData> observable_table_id;
    private SuggestPriceHandler suggestPriceHandler;
    private PriceSuggestion suggestion;
    private SimpleBooleanProperty s_boolean;
    public Price() {


        s_boolean=new SimpleBooleanProperty(false);
        Properties properties = null;
        HashMap<String,String> properties_suggestion=null;
        URI uri=null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            properties_suggestion=FileStorage.getProperties(List.of("gain","medium_stock_item","min_day_expire"),new FileReader("PriceSuggest.properties"));
             uri=new URI("https://gist.githubusercontent.com/misunderstoodgenius95/4006133ef46f0a0459094df99d6baa18/raw/8f34fbaf0779b38505370a08282f4293f74e33c2/trendmarket.json");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        //   suggestion=new PriceSuggestion();
        SuggestPriceConfigDao s_conf=new SuggestPriceConfigDao(Database.getInstance(properties));
        sellerPriceDao =new SellerPriceDao(Database.getInstance(properties));
        TrendMarket trendMarket=new TrendMarket(new ClientHttp(),uri);
        PurchaseOrderDetailDao p_dao=new PurchaseOrderDetailDao(Database.getInstance(properties));
        suggestion=new PriceSuggestion(properties_suggestion,trendMarket,s_conf,p_dao);
        suggestPriceHandler=new SuggestPriceHandler("Inserisci Prezzi", sellerPriceDao,suggestion,s_boolean);
    }
    @FXML
    public void btn_action_add(ActionEvent event) {
        suggestPriceHandler.execute();
        if(s_boolean.get()){
            observable_table_id.clear();
            observable_table_id.addAll(sellerPriceDao.findAll());
            table_id.refresh();
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
                TableUtility.generate_column_int("Prezzo","price")
        );
        observable_table_id.addAll(sellerPriceDao.findAll());
        table_id.setItems(observable_table_id);








    }

}
