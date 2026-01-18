package pharma.DialogController;

import javafx.collections.FXCollections;

import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.javafxlib.Status;
import pharma.config.Utility;
import pharma.config.View.FarmaciaLotConvert;
import pharma.dao.FarmaciaDao;
import pharma.dao.GenericJDBCDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


public class FarmaciaControllerBase extends DialogControllerBase<FieldData> {
    private TextField  textfield_header;
    private  TextField textField_vat;
    private  TextField textField_street;
    private  TextField textField_cap;
    private SearchableComboBox<FieldData> combo_province;
    private  TextField textField_comune;
    private FarmaciaDao farmaciaDao;
    private  TextField textField_lat;
    private TextField textField_lng;
    public FarmaciaControllerBase(String content, List<GenericJDBCDao> genericJDBCDao) {
        super(content, genericJDBCDao);
        textfield_header=add_text_field("Inserisci Intestazione Farmaceutica");
        textField_vat=add_text_field_with_validation("Inserisci Partita Iva",Validation.Vat);
        textField_street=add_text_field("Inserisci Via");
        textField_cap=add_text_field_with_validation("Inserisci Cap",Validation.Cap);
        combo_province=add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setProvince("Inserisci Provincia").build());
        combo_province.setConverter(new FarmaciaLotConvert());
        textField_lat= add_text_field("Inserisci Latitudine");
        textField_lng=add_text_field("Inserisci Longitudine");

        String json_string = "";
        try {
            json_string=new String(Files.readAllBytes(Path.of("src/main/resources/json_file/province.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        combo_province.setItems(FXCollections.observableArrayList(Utility.extract_province(json_string)));
        textField_comune=add_text_field("Inserisci Comune");
        farmaciaDao = (FarmaciaDao) genericJDBCDao.stream().
                filter(dao -> dao instanceof FarmaciaDao).findFirst().orElseThrow(() -> new IllegalArgumentException("FarmaciaDao not found in the list"));

    }



    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {
    }


    @Override
    protected FieldData get_return_data() {
        return FieldData.FieldDataBuilder.getbuilder().
                setAnagrafica_cliente(textfield_header.getText()).
                setPartita_iva(textField_vat.getText()).
                setStreet(textField_street.getText()).
                setCap(Integer.parseInt(textField_cap.getText())).
                setComune(textField_comune.getText()).
                setProvince(combo_province.getValue().getSigla()).
                setLatitude(Double.parseDouble(textField_lat.getText())).
                setLongitude(Double.parseDouble(textField_lng.getText())).
        build();
    }

    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {
        return farmaciaDao.insert(fieldData);


    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }

    @Override
    protected void initialize() {

    }




}
