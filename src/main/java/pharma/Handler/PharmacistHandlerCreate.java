package pharma.Handler;

import algoWarehouse.password.SecurePasswordGenerator;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.config.Status;
import pharma.config.auth.UserService;
import pharma.config.auth.UserServiceResponse;
import pharma.dao.FarmaciaDao;
import pharma.dao.GenericJDBCDao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PharmacistHandlerCreate extends DialogHandler<FieldData> {
    private TextField textField_email;
    private TextField textField_first_name;
    private TextField  textField_last_name;
    private  UserService userService;
    public PharmacistHandlerCreate(FarmaciaDao farmaciaDao, UserService userService) {
        super("Crea Farmacista", Collections.singletonList(farmaciaDao));
        getDialogPane().setPrefWidth(800);
        getDialogPane().setPrefHeight(400);
        this.userService=userService;
    }

    private SearchableComboBox<FieldData> searchable_farmacia;


    @Override
    protected boolean condition_event(FieldData type) throws Exception {
        return false;
    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        String password= SecurePasswordGenerator.generatePassword(12);
        System.out.println(password);
        UserServiceResponse ur=userService.register(type.getEmail(),password,"pharmacist",type.getNome(),type.getLast_name());
        return switch (ur.getStatus()) {
            case 200 -> new Status("Registrazione con Successso!", true);
            case 429 -> new Status("Email già presente!", false);
            default -> new Status("Errore!", false);
        };

    }

    @Override
    protected void initialize() {



    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {
        textField_email = add_text_field_with_validation("Inserisci Email", Validation.Email);
        textField_first_name = add_text_field("Inserisci Nome");
        textField_last_name = add_text_field("Inserisci Cognome");
        if( optionalgenericJDBCDao.isPresent()) {
            List<GenericJDBCDao> genericJDBCDaoList = optionalgenericJDBCDao.get();
            FarmaciaDao farmaciaDao = (FarmaciaDao) genericJDBCDaoList.stream().filter(genericJDBCDao -> genericJDBCDao instanceof FarmaciaDao).findFirst().
                    orElseThrow(() -> new IllegalArgumentException("Not found FarmaciaDao"));
            searchable_farmacia = add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Seleziona Farmacia").build());
            searchable_farmacia.setItems(FXCollections.observableArrayList(farmaciaDao.findAll()));
            settingCombo();
        }

    }

    @Override
    protected FieldData get_return_data() {
        return FieldData.FieldDataBuilder.getbuilder().setEmail(textField_email.getText())
                .setNome(textField_first_name.getText()).setLast_name(textField_last_name.getText())
                .build();
    }



    public  void settingCombo(){

        searchable_farmacia.setConverter(new StringConverter<>() {
            @Override
            public String toString(FieldData object) {
                if (object != null) {
                    if (object.getNome()!= null) {

                        return object.getNome();
                    }else {
                        return object.getAnagrafica_cliente()+"  "+ object.getStreet() +"  "+object.getCap()+"  "+object.getComune()+"  "+object.getProvince();
                    }

                }
                return " ";
            }

            @Override
            public FieldData fromString(String string) {
                return null;
            }
        });


    }
}
