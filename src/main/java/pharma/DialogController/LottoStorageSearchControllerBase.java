package pharma.DialogController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import pharma.DialogController.Table.LottoStorageTableView;
import pharma.Model.FieldData;
import pharma.Model.WarehouseModel;
import pharma.config.PopulateChoice;
import pharma.javafxlib.Status;
import pharma.dao.*;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.javafxlib.RadioOptions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LottoStorageSearchControllerBase extends DialogControllerBase<FieldData> {
    private ToggleGroup group;
    private SearchableComboBox<WarehouseModel> searchable_warehouse;
    private ObservableList<FieldData> obs_magazzino;
    private TextField textField_text;
    private MagazzinoDao magazzinoDao;
    private LottoStorageTableView lottoStorageTableView;
    private LotAssigmentDao lotAssigmentDao;
    private LotAssigmentShelvesDao lotAssigmentShelvesDao;
    public LottoStorageSearchControllerBase(String content, List<GenericJDBCDao> genericJDBCDao) {
        super(content, genericJDBCDao);
        this.lotAssigmentDao = (LotAssigmentDao) genericJDBCDao.stream().
                filter(dao -> dao instanceof LotAssigmentDao).findFirst().orElseThrow(() -> new IllegalArgumentException("PharmaDao not found in the list"));
        this.lotAssigmentShelvesDao = (LotAssigmentShelvesDao) genericJDBCDao.stream().
                filter(dao -> dao instanceof LotAssigmentShelvesDao).findFirst().orElseThrow(() -> new IllegalArgumentException("PharmaDao not found in the list"));

        lottoStorageTableView =new LottoStorageTableView("Visualizza Lotti",lotAssigmentDao,lotAssigmentShelvesDao);
    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {
        if (optionalgenericJDBCDao.isPresent()) {
            System.out.println(optionalgenericJDBCDao.get().size());
            this.magazzinoDao = (MagazzinoDao) optionalgenericJDBCDao.get().stream().
                    filter(dao -> dao instanceof MagazzinoDao).findFirst().orElseThrow(() -> new IllegalArgumentException("PharmaDao not found in the list"));
            add_label("Ricerca Per Lotti o Per Scaffale");
            group = add_radios(Arrays.asList(new RadioOptions("lotto", "Lotto "), new RadioOptions("shelf", "scaffale")), CustomDialog.Mode.Horizontal);
            WarehouseModel warehouseModel = new WarehouseModel();
            warehouseModel.setNome("Ricerca Magazzino");
            searchable_warehouse = add_SearchComboBox(warehouseModel);
            searchable_warehouse.setDisable(true);
            getControlList().remove(searchable_warehouse);
            group.getToggles().getFirst().setSelected(true);
            searchable_warehouse.setConverter(new StringConverter<WarehouseModel>() {
                @Override
                public String toString(WarehouseModel object) {
                    if (object != null) {
                        return " " + object.getNome();
                    } else {
                        return "";
                    }
                }

                @Override
                public WarehouseModel fromString(String string) {
                    return null;
                }
            });
            searchable_warehouse.setItems(FXCollections.observableArrayList(magazzinoDao.findAll()));
            textField_text = add_text_field("");
            listener_group();


        }
    }

    public void listener_group() {
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton radioButton = (RadioButton) newValue;
                if (radioButton.getId().equals("shelf")) {
                    searchable_warehouse.setDisable(false);
                    textField_text.setPromptText("Inserisci Codice Scaffale");
                    getControlList().add(searchable_warehouse);
                } else {
                    searchable_warehouse.setDisable(true);
                    textField_text.setPromptText("Inserisci Lotto");
                    System.out.println(getControlList().size());
                    getControlList().remove(searchable_warehouse);
                    System.out.println(getControlList().size());
                }
            }
        });


    }


    @Override
    protected boolean condition_event(FieldData type) throws Exception {
        System.out.println(type.getCode());
        System.out.println(type.getId());
        return  true;




    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }

    @Override
    protected void initialize() {

    }


    @Override
    protected FieldData get_return_data() {
        if (group.getSelectedToggle() instanceof RadioButton radioButton) {
            if (radioButton.getId().equals("shelf")) {
                return FieldData.FieldDataBuilder.getbuilder().setcode(textField_text.getText()).setId(searchable_warehouse.getValue().getId()).build();

            } else {
                return FieldData.FieldDataBuilder.getbuilder().setcode(textField_text.getText()).build();

            }
        }
        return null;
    }
}
