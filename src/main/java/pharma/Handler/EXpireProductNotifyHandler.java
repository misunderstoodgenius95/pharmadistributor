package pharma.Handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import org.jetbrains.annotations.TestOnly;
import org.json.JSONArray;
import org.json.JSONObject;
import pharma.Handler.Table.ProductTableCustom;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.config.Status;
import pharma.config.net.ClientHttp;
import pharma.dao.FarmacoDao;
import pharma.dao.GenericJDBCDao;
import pharma.dao.LottiDao;
import pharma.javafxlib.CustomTableView.CheckBoxTableColumn;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EXpireProductNotifyHandler extends  DialogHandler<FieldData> {

    private  Button btn_products;
    private SearchableComboBox<FieldData> s_comboBox;
    private Spinner<Integer> spinner_time;
    private ObservableList<FieldData> checkbox_choice;
    private LottiDao lottiDao;
    private JSONArray jsonArrayExpire;
    private ClientHttp clientHttp;
    ProductTableCustom productTableCustom;
    CheckBoxTableColumn<FieldData> checkBoxTableColumn;
    public EXpireProductNotifyHandler(String content, FarmacoDao farmacoDao,LottiDao lottiDao) {
        super(content, List.of(farmacoDao,lottiDao));
        checkbox_choice=FXCollections.observableArrayList();
        this.lottiDao=lottiDao;
        jsonArrayExpire=new JSONArray();
        clientHttp=new ClientHttp();
      productTableCustom=new ProductTableCustom("Scegli Prodotti");

    }

    public ProductTableCustom getProductTableCustom() {
        return productTableCustom;
    }

    /*
      [{"lot_id":"1199a","product_id":30,"time_of_day":30,"expiration_date":"10/10/2028"},
     */
    @Override
    protected boolean condition_event(FieldData type) throws Exception {
        System.out.println("esegui");
        if(!checkbox_choice.isEmpty()){
            checkbox_choice.forEach(fd->{
                int farmaco_id=fd.getFarmaco_id();
               List<FieldData> fd_lots=lottiDao.findByFarmaco(farmaco_id);
               fd_lots.forEach(fd_lot->{
                   JSONObject jsonObject=new JSONObject();
                   jsonObject.put("lot_id",fd_lot.getCode());
                   jsonObject.put("product_id",farmaco_id);
                   jsonObject.put("time_of_day",spinner_time.getValue());
                   jsonObject.put("expiration_date",fd_lot.getElapsed_date());
                   jsonArrayExpire.put(jsonObject);
               });
            });
            System.out.println(jsonArrayExpire.toString());
            HttpResponse<String> response = clientHttp.send(HttpRequest.newBuilder(URI.create("http://localhost:3000/expire_items")).setHeader("Content-Type","application/json").
                    POST(HttpRequest.BodyPublishers.ofString(jsonArrayExpire.toString())).build());
            System.out.println(response.statusCode());
            return response.statusCode() == 201;




        }
        return false;

    }
    @TestOnly
    public Button getBtn_products() {
        return btn_products;
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
        if(optionalgenericJDBCDao.isPresent()){
            FarmacoDao  farmacoDao=(FarmacoDao) optionalgenericJDBCDao.get().stream().filter(farmaco->farmaco instanceof FarmacoDao).findFirst().get();
            btn_products=addButton("Scegli i Prodotti");
            add_label("Inserisci Giorni di Anticipo");
            spinner_time=add_spinner();
            choice_btn(farmacoDao);




        }


    }

    public CheckBoxTableColumn<FieldData> getCheckBoxTableColumn() {
        return checkBoxTableColumn;
    }

    private void choice_btn(FarmacoDao dao){
        btn_products.setOnAction(event -> {
           checkBoxTableColumn= productTableCustom.add_check_box_column();
            List<FieldData> list_farmaco=dao.findAll();
            System.out.println(list_farmaco.size());
            productTableCustom.getTableView().getItems().addAll(list_farmaco);
            productTableCustom.show();
            productTableCustom.getCheckBoxValue().addListener(new ChangeListener<FieldData>() {
                @Override
                public void changed(ObservableValue<? extends FieldData> observable, FieldData oldValue, FieldData newValue) {
                    if(newValue!=null) {
                        checkbox_choice.add(newValue);
                        btn_products.setText("Prodotti Scelti");
                    }
                }
            });
        });




    }

    @Override
    protected FieldData get_return_data() {

       return FieldData.FieldDataBuilder.getbuilder().setQuantity(spinner_time.getValue()).build();
    }
}
