package pharma.DialogController;

import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.config.Utility;
import pharma.javafxlib.Status;
import pharma.config.View.FarmaciaLotConvert;
import pharma.dao.GenericJDBCDao;
import pharma.dao.InfoSocietyDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class Info_SocietyControllerBase extends DialogControllerBase<FieldData> {
    private TextField textfield_header;
    private  TextField textField_vat;
    private  TextField textField_street;
    private  TextField textField_cap;
    private SearchableComboBox<FieldData> combo_province;
    private  TextField textField_comune;
    private  TextField tf_website;
    private   TextField tf_image;
    private  TextField tf_email;
    private InfoSocietyDao infoSocietyDao;




    public Info_SocietyControllerBase(String content, InfoSocietyDao infoSocietyDao) {
        super(content);
        this.infoSocietyDao=infoSocietyDao;
        textfield_header=add_text_field("Inserisci Intestazione Utente");
        textField_vat=add_text_field_with_validation("Inserisci Partita Iva",Validation.Vat);
        textField_street=add_text_field("Inserisci Via");
        textField_cap=add_text_field_with_validation("Inserisci Cap",Validation.Cap);
        combo_province=add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setProvince("Inserisci Provincia").build());
        combo_province.setConverter(new FarmaciaLotConvert());
        combo_province.setItems(FXCollections.observableArrayList(Utility.extract_province(setting_province())));
        textField_comune=add_text_field("Inserisci Comune");
        tf_image=add_text_field("Inserisci url Image");
        tf_website=add_text_field("Inserisci Website");
        tf_email=add_text_field("Inserisci Email");
        List<FieldData> list=infoSocietyDao.findAll();
        if(!list.isEmpty()){
            FieldData fd_list=list.getFirst();
            textfield_header.setText(fd_list.getAnagrafica_cliente());
            textField_vat.setText(fd_list.getPartita_iva());
            textField_street.setText(fd_list.getStreet());
            textField_cap.setText(String.valueOf(fd_list.getCap()));
            textField_comune.setText(fd_list.getComune());
            combo_province.setValue(FieldData.FieldDataBuilder.getbuilder().setProvince(fd_list.getProvince()).build());
            tf_image.setText(fd_list.getPicture());
            tf_email.setText(fd_list.getEmail());
            tf_website.setText(fd_list.getWebsite());

        }



    }

    @Override
    protected void initialize() {

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
                setWebsite(tf_website.getText()).
                setPicture(tf_image.getText()).setEmail(tf_email.getText()).
                build();
    }

    @Override
    protected boolean condition_event(FieldData type) throws Exception {
        return  infoSocietyDao.insert(type);
    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }




    private String setting_province(){

        String json_string = "";
        try {
            json_string=new String(Files.readAllBytes(Path.of("src/main/resources/json_file/province.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json_string;
    }

}
