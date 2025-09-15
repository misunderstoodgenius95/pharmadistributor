package pharma.Handler;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import pharma.Model.FieldData;
import pharma.Model.Warehouse;
import pharma.config.PopulateChoice;
import pharma.config.Status;
import pharma.dao.GenericJDBCDao;
import pharma.dao.MagazzinoDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MagazzinoHandler extends DialogHandler<Warehouse> {
    private TextField textField_warehouse;
    private TextField textField_address;
    private TextField textField_comune;
    private TextField textField_province;
    private TextField textfield_lat;
    private  TextField textField_lng;
    private MagazzinoDao magazzinoDao;
    private  ObservableList<Warehouse> observableList;
    public MagazzinoHandler(String content, List<GenericJDBCDao> genericJDBCDao, ObservableList<Warehouse> observableList) {
        super(content, genericJDBCDao);
       this.magazzinoDao = (MagazzinoDao) genericJDBCDao.stream().
                filter(dao -> dao instanceof MagazzinoDao).findFirst().orElseThrow(() -> new IllegalArgumentException("PharmaDao not found in the list"));
        this.observableList=observableList;

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
    protected Warehouse get_return_data() {

        Warehouse warehouse=new Warehouse();
        Point point=new Point(Double.parseDouble(textfield_lat.getText()),Double.parseDouble(textField_lng.getText()));
        warehouse.setNome(textField_warehouse.getText());
        warehouse.setAddress(textField_address.getText());
        warehouse.setComune(textField_comune.getText());
        warehouse.setProvince(textField_province.getText());
        warehouse.setpGgeometry(new PGgeometry(point));
        return warehouse;

    }

    @Override
    protected boolean condition_event(Warehouse type) throws Exception {
        magazzinoDao.insert(type);
        observableList.add(type);
        return false;
    }

    @Override
    protected Status condition_event_status(Warehouse type) throws Exception {
        return null;
    }


}
