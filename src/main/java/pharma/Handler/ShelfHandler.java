package pharma.Handler;

import algo.ShelfInfo;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import pharma.Model.FieldData;
import pharma.Model.Warehouse;
import pharma.config.PopulateChoice;
import pharma.dao.GenericJDBCDao;
import pharma.dao.MagazzinoDao;
import pharma.dao.ShelfDao;

import java.util.List;
import java.util.Optional;

public class ShelfHandler  extends DialogHandler<ShelfInfo> {
    private SearchableComboBox<Warehouse> s_choice_warehouse;
    private MagazzinoDao magazzinoDao;
    private ShelfDao shelfDao;
    private Spinner<Double> lunghezza;
    private Spinner<Double> altezza;
    private Spinner<Double> profondita;
    private Spinner<Double> spessore;
    private Spinner<Integer> num_rip;
    private TextField codice;
    private Spinner<Integer> capacity;
    public ShelfHandler(String content, List<GenericJDBCDao> genericJDBCDao) {
        super(content, genericJDBCDao);

        this.magazzinoDao = (MagazzinoDao) genericJDBCDao.stream().
                filter(dao -> dao instanceof MagazzinoDao).findFirst().orElseThrow(() -> new IllegalArgumentException("MagazzinoDao not found in the list"));
        this.shelfDao = (ShelfDao) genericJDBCDao.stream().
                filter(dao -> dao instanceof ShelfDao).findFirst().orElseThrow(() -> new IllegalArgumentException("ShelfDao not found in the list"));

    }

    @Override
    protected void initialize() {

    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {

        this.magazzinoDao = (MagazzinoDao) optionalgenericJDBCDao.get().stream().
                filter(dao -> dao instanceof MagazzinoDao).findFirst().orElseThrow(() -> new IllegalArgumentException("MagazzinoDao not found in the list"));
        List<Warehouse> list_warehouse=magazzinoDao.findAll();
        Warehouse warehouse=new Warehouse();
        warehouse.setNome("Seleziona Magazzino");
        s_choice_warehouse = add_SearchComboBoxs(warehouse);
        s_choice_warehouse.getItems().addAll(list_warehouse);
        s_choice_warehouse.setConverter(new StringConverter<Warehouse>() {
            @Override
            public String toString(Warehouse object) {
                return ""+object.getId();
            }

            @Override
            public Warehouse fromString(String string) {
                return null;
            }
        });
        codice = add_text_field("Inserisci codice");
        add_label("Inserisci Lunghezza");
        lunghezza = add_spinner_double();
        add_label("Insersici altezza");
        altezza = add_spinner_double();
        add_label("Inserisci Altezza");
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
                setShelf_code(codice.getText()).
                setMagazzino_id(s_choice_warehouse.getValue().getId()).
                setLenght(lunghezza.getValue()).setHeight(altezza.getValue()).
                setDeep(profondita.getValue()).setShelf_thickness(spessore.getValue()).setNum_rip(num_rip.getValue()).setWeight(capacity.getValue()).build();


    }

    @Override
    protected boolean condition_event(ShelfInfo type) throws Exception {
        return shelfDao.insert(type);
    }


}
