package pharma.Handler;

import pharma.Model.LotDimensionModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Spinner;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.dao.GenericJDBCDao;
import pharma.dao.LotDimensionDao;

import java.util.List;
import java.util.Optional;

public class CustomLotsDimension extends  DialogHandler<LotDimensionModel> {

    private Spinner<Double> lenght;
    private Spinner<Double> height;
    private Spinner<Double>deep;
    private Spinner<Integer> weight;
    private SimpleObjectProperty<FieldData> object_lots_id;
    private LotDimensionDao lotDimensionDao;

    public CustomLotsDimension(String content, List<GenericJDBCDao> genericJDBCDao, SimpleObjectProperty<FieldData> object_lots_id) {
        super(content, genericJDBCDao);
       lotDimensionDao =(LotDimensionDao) genericJDBCDao.stream().
               filter(dao->dao instanceof LotDimensionDao).findFirst().orElseThrow();
       this.object_lots_id =object_lots_id;

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
    protected LotDimensionModel get_return_data() {
        int farmaco_id = object_lots_id.get().getFarmaco_id();
        String lotto_id =object_lots_id.get().getCode();
        return  new LotDimensionModel(lotto_id,farmaco_id,lenght.getValue(),height.getValue(),deep.getValue(),weight.getValue());

    }

    @Override
    protected boolean condition_event(LotDimensionModel type) throws Exception {
         return lotDimensionDao.insert(type);
    }






}
