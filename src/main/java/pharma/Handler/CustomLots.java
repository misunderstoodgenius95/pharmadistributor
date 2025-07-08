package pharma.Handler;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.dao.GenericJDBCDao;
import pharma.dao.LotDimension;

import java.util.List;
import java.util.Optional;

public class CustomLots extends  DialogHandler<FieldData> {

    Spinner<Double> lenght;
    Spinner<Double> height;
    Spinner<Double>deep;
    Spinner<Integer> weight;
    SimpleObjectProperty<FieldData> s_fd;
    private LotDimension lotDimension;
    public CustomLots(String content, List<GenericJDBCDao> genericJDBCDao, SimpleObjectProperty<FieldData> s_fd) {
        super(content, genericJDBCDao);
       lotDimension=(LotDimension) genericJDBCDao.stream().
               filter(dao->dao instanceof LotDimension).findFirst().orElseThrow();
       this.s_fd=s_fd;

    }

    @Override
    protected void initialize() {
      add_label("Inserisci Lunghezza");
        lenght = add_spinner_double();
        add_label("Inseerisci Altezza");
        height = add_spinner_double();
        add_label("Inserisci Profondità");
        deep = this.add_spinner_double();
        this.add_label("Inserisci Peso");
        weight = this.add_spinner();

    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {

    }

    @Override
    protected FieldData get_return_data() {
        int farmaco_id = s_fd.get().getFarmaco_id();
        String lotto_id = s_fd.get().getCode();
        return FieldData.FieldDataBuilder.getbuilder().setcode(lotto_id).setFarmaco_id(farmaco_id).setLunghezza(lenght.getValue()).
                setAltezza(height.getValue()).
                setProfondita(deep.getValue()).setCapacity(weight.getValue()).build();
    }

    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {
        return lotDimension.insert(fieldData);

    }



}
