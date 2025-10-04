package pharma.Handler;

import algoWarehouse.password.SecurePasswordGenerator;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.config.Status;
import pharma.config.View.FarmacoLotsConvert;
import pharma.config.auth.UserService;
import pharma.config.auth.UserServiceResponse;
import pharma.dao.GenericJDBCDao;

import java.util.List;
import java.util.Optional;

public class AdminCreateCredentialsHandler extends DialogHandler<FieldData>  {
    private TextField textField_email;
    private TextField textField_first_name;
    private TextField  textField_last_name;
    private SearchableComboBox<FieldData> choice_role;
    private final UserService userService;

    public AdminCreateCredentialsHandler(String content, UserService userService) {
        super(content, userService);
        this.userService=userService;

    }

    @Override
    protected boolean condition_event(FieldData type) throws Exception {
        return false;
    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        String password=SecurePasswordGenerator.generatePassword(12);
        System.out.println(password);
        UserServiceResponse ur=userService.register(type.getEmail(),password,type.getRole(),type.getNome(),type.getLast_name());
        System.out.println("status"+ur.getStatus());
        return switch (ur.getStatus()) {
            case 200 -> new Status("Registrazione con Successso!", true);
            case 429 -> new Status("Email già presente!", false);
            default -> new Status("Errore!", false);
        };

    }


    @Override
    protected void initialize() {
        textField_email=add_text_field_with_validation("Inserisci Email",Validation.Email);
        textField_first_name=add_text_field("Inserisci Nome");
        textField_last_name=add_text_field("Inserisci Cognome");
        choice_role=add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Seleziona Addetto").build());
        choice_role.setConverter(new FarmacoLotsConvert());
        choice_role.getItems().addAll(List.of(FieldData.FieldDataBuilder.getbuilder().setNome("Acquisti").setRole("purchase").build(),
                FieldData.FieldDataBuilder.getbuilder().setNome("Vendita").setRole("seller").build(),FieldData.FieldDataBuilder.getbuilder().
                        setNome("Magazziniere").setRole("warehouse").build()));
    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {

    }

    @Override
    protected FieldData get_return_data() {

         return FieldData.FieldDataBuilder.getbuilder().setEmail(textField_email.getText())
                .setNome(textField_first_name.getText()).setLast_name(textField_last_name.getText()).setRole(choice_role.getValue().getRole())
                 .build();
    }


}
