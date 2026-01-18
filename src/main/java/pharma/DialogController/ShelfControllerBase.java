package pharma.DialogController;

import pharma.Model.ShelfInfo;
import pharma.Model.ShelvesCapacity;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.Model.FieldData;
import pharma.Model.WarehouseModel;
import pharma.config.PopulateChoice;
import pharma.javafxlib.Status;
import pharma.dao.GenericJDBCDao;
import pharma.dao.MagazzinoDao;
import pharma.dao.ShelfDao;
import pharma.dao.ShelvesDao;

import java.util.List;
import java.util.Optional;

public class ShelfControllerBase extends DialogControllerBase<ShelfInfo> {
    private static final Logger log = LoggerFactory.getLogger(ShelfControllerBase.class);
    private SearchableComboBox<WarehouseModel> s_choice_warehouse;
    private MagazzinoDao magazzinoDao;
    private ShelfDao shelfDao;
    private Spinner<Double> lunghezza;
    private Spinner<Double> altezza;
    private Spinner<Double> profondita;
    private Spinner<Double> spessore;
    private Spinner<Integer> num_rip;
    private TextField codice;
    private Spinner<Integer> capacity;
    private  ObservableList<ShelfInfo> shelfInfos;
    private ShelvesDao shelvesDao;
    public ShelfControllerBase(String content, List<GenericJDBCDao> genericJDBCDao, ObservableList<ShelfInfo> shelfInfos) {
        super(content, genericJDBCDao);
        this.magazzinoDao = (MagazzinoDao) genericJDBCDao.stream().
                filter(dao -> dao instanceof MagazzinoDao).findFirst().orElseThrow(() -> new IllegalArgumentException("MagazzinoDao not found in the list"));
        this.shelfDao = (ShelfDao) genericJDBCDao.stream().
                filter(dao -> dao instanceof ShelfDao).findFirst().orElseThrow(() -> new IllegalArgumentException("ShelfDao not found in the list"));
        this.shelvesDao = (ShelvesDao) genericJDBCDao.stream().
                filter(dao -> dao instanceof ShelvesDao).findFirst().orElseThrow(() -> new IllegalArgumentException("ShelvesDao not found in the list"));
        this.shelfInfos=shelfInfos;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {
        this.magazzinoDao = (MagazzinoDao) optionalgenericJDBCDao.get().stream().
                filter(dao -> dao instanceof MagazzinoDao).findFirst().orElseThrow(() -> new IllegalArgumentException("MagazzinoDao not found in the list"));
        List<WarehouseModel> list_warehouseModel =magazzinoDao.findAll();
        System.out.println(list_warehouseModel.size());
        WarehouseModel warehouseModel =new WarehouseModel();
        warehouseModel.setNome("Seleziona Magazzino");
        s_choice_warehouse = add_SearchComboBoxs(warehouseModel);
        s_choice_warehouse.getItems().addAll(list_warehouseModel);
        s_choice_warehouse.setConverter(new StringConverter<WarehouseModel>() {
            @Override
            public String toString(WarehouseModel object) {
                if(object!=null) {
                    return "" + object.getNome();
                }else{
                    return "";
                }
            }

            @Override
            public WarehouseModel fromString(String string) {
                return null;
            }
        });
        codice = add_text_field("Inserisci codice");
        add_label("Inserisci Lunghezza");
        lunghezza = add_spinner_double();
        add_label("Insersici altezza");
        altezza = add_spinner_double();
        add_label("Inserisci Profondità");
        profondita = add_spinner_double();
        add_label("Inserisci Spessore");
        spessore = add_spinner_double();
        add_label("Inserisci Numero di Ripiani");
        num_rip = add_spinner();
        add_label("Inserisci  Capacità totale");
        capacity=add_spinner();


    }

    @Override
    protected ShelfInfo get_return_data() {
        return ShelfInfo.ShelfInfoBuilder.get_builder().
                setShelf_code(codice.getText()).setNome_magazzino(s_choice_warehouse.getValue().getNome()).
                setMagazzino_id(s_choice_warehouse.getValue().getId()).
                setLenght(lunghezza.getValue()).setHeight(altezza.getValue()).
                setDeep(profondita.getValue()).setShelf_thickness(spessore.getValue()).setNum_rip(num_rip.getValue()).setWeight(capacity.getValue()).build();


    }

    @Override
    protected boolean condition_event(ShelfInfo type) throws Exception {
        try {
            shelfDao.setTransaction(true);
            boolean result = shelfDao.insert(type);
            if (result) {

                System.out.println(num_rip.getValue());
                for (int i = 0; i <num_rip.getValue() ; i++) {
                    int num_rip = i;
                    ShelvesCapacity shelvesCapacity = new ShelvesCapacity(codice.getText(), ++num_rip, 0, 0, 0);
                    boolean result_shelves = shelvesDao.insert(shelvesCapacity);
                    if (!result_shelves) {
                        shelfDao.rollback();
                        return false;
                    }
                }
                shelfDao.commit();
                shelfInfos.add(type);
                return result;

            } else {
                shelfDao.rollback();
                return false;
            }
        } catch (Exception e) {
            shelfDao.rollback();
            return false;
        }

    }

    @Override
    protected Status condition_event_status(ShelfInfo type) throws Exception {
        return null;
    }


}
