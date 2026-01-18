package pharma.DialogController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import org.controlsfx.control.SearchableComboBox;
import org.jetbrains.annotations.TestOnly;
import org.json.JSONArray;
import org.json.JSONObject;
import pharma.DialogController.Table.ProductTableCustom;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.config.TableUtility;
import pharma.javafxlib.Status;

import pharma.config.net.ClientHttp;
import pharma.dao.GenericJDBCDao;
import pharma.dao.LotAssigmentDao;
import pharma.dao.LottiDao;
import pharma.javafxlib.CustomTableView.CheckBoxTableColumn;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class EXpireProductNotifyControllerBase extends DialogControllerBase<FieldData> {

    private  Button btn_products;
    private SearchableComboBox<FieldData> s_comboBox;
    private Spinner<Integer> spinner_time;
    private ObservableList<FieldData> checkbox_choice;
    private LottiDao lottiDao;
    private JSONArray jsonArrayExpire;
    private ClientHttp clientHttp;
    ProductTableCustom productTableCustom;
    CheckBoxTableColumn<FieldData> checkBoxTableColumn;

    public EXpireProductNotifyControllerBase(String content, LotAssigmentDao lotAssigmentDao) {
        super(content, List.of(lotAssigmentDao));
        checkbox_choice=FXCollections.observableArrayList();
        this.lottiDao=lottiDao;
        jsonArrayExpire=new JSONArray();
        clientHttp=new ClientHttp();
      productTableCustom=new ProductTableCustom("Scegli Prodotti");
      productTableCustom.getTableView().getColumns().add(TableUtility.generate_column_int("Disponibilità","availability"));

        checkBoxTableColumn= productTableCustom.add_check_box_column();

    }

    public ProductTableCustom getProductTableCustom() {
        return productTableCustom;
    }

    /*
      [{"lot_id":"1199a","product_id":30,"time_of_day":30,"expiration_date":"10/10/2028"},
     */
    @Override
    protected boolean condition_event(FieldData type) throws Exception {
        if(!checkbox_choice.isEmpty()){
            checkbox_choice.forEach(fd->{
                   JSONObject jsonObject=new JSONObject();
                   jsonObject.put("lot_id",fd.getCode());
                   jsonObject.put("product_id",fd.getFarmaco_id());
                   jsonObject.put("time_of_day",spinner_time.getValue());
                   jsonObject.put("expiration_date",fd.getElapsed_date());
                   jsonObject.put("availability",fd.getAvailability());
                   jsonArrayExpire.put(jsonObject);
            });
            System.out.println(jsonArrayExpire.toString());
            HttpResponse<String> response = clientHttp.send(HttpRequest.newBuilder(URI.create("http://localhost:3001/expire_items")).setHeader("Content-Type","application/json").
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
            LotAssigmentDao lotAssigment=(LotAssigmentDao) optionalgenericJDBCDao.get().stream().filter(assignment->assignment instanceof  LotAssigmentDao).findFirst().get();

            btn_products=addButton("Scegli i Prodotti");
            add_label("Inserisci Giorni di Anticipo");
            spinner_time=add_spinner();
            choice_btn(lotAssigment);




        }


    }

    public CheckBoxTableColumn<FieldData> getCheckBoxTableColumn() {
        return checkBoxTableColumn;
    }

    private void choice_btn(LotAssigmentDao dao){
        btn_products.setOnAction(event -> {
            List<FieldData> list_farmaco=dao.findByFarmacoAll();
            productTableCustom.getTableView().getItems().setAll(list_farmaco);
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
