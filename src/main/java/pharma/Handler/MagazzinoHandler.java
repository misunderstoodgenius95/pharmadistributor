package pharma.Handler;

import javafx.scene.control.TextField;
import okhttp3.HttpUrl;
import pharma.Controller.subpanel.Magazzino;
import pharma.Model.FieldData;
import pharma.config.HttpJsonClient;
import pharma.config.PopulateChoice;
import pharma.dao.GenericJDBCDao;
import pharma.dao.MagazzinoDao;
import pharma.dao.PharmaDao;
import pharma.javafxlib.Dialog.CustomDialog;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public class MagazzinoHandler extends DialogHandler<FieldData> {
    private TextField textField_warehouse;
    private TextField textField_address;
    private TextField textField_comune;
    private TextField textField_province;
    private TextField textfield_lat;
    private  TextField textField_lng;
    private MagazzinoDao magazzinoDao;
    public MagazzinoHandler(String content, List<GenericJDBCDao> genericJDBCDao) {
        super(content, genericJDBCDao);
       this.magazzinoDao = (MagazzinoDao) genericJDBCDao.stream().
                filter(dao -> dao instanceof MagazzinoDao).findFirst().orElseThrow(() -> new IllegalArgumentException("PharmaDao not found in the list"));


    }


    @Override
    protected void initialize()  {
        textField_warehouse= add_text_field("Inserisci Nome Magazzino");
        textField_address=add_text_field("Indirizzo");
        textField_province= add_text_field("Provincia");
        textField_comune=add_text_field("Comune");
        textfield_lat=add_text_field_with_validation("Inserisci Latitudine",Validation.lat);
        textField_lng=add_text_field_with_validation("Inserisci Longitudine",Validation.lng);
     /*   String url = HttpUrl.get("http://esamebasicalio.altervista.org/map.php")
                .newBuilder().addQueryParameter("lat", textfield_lat.getText())
                .addQueryParameter("lng", textField_lng.getText()).build().toString();
        add_web_page(url);
*/
    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {


    }

    @Override
    protected FieldData get_return_data() {
        return FieldData.FieldDataBuilder.getbuilder().
                setNome(textField_warehouse.getText()).
                setStreet(textField_address.getText()).setProvince(textField_province.getText()).
                setComune(textField_comune.getText()).setLatitude(Double.parseDouble(textfield_lat.getText())).
                setLongitude(Double.parseDouble(textField_lng.getText())).
                build();
    }

    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {
        return  magazzinoDao.insert(fieldData);


    }
}
