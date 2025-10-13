package pharma.Handler.Table;

import javafx.scene.control.TableView;
import pharma.Handler.DialogHandler;
import pharma.Model.FieldData;
import pharma.Model.WarehouseModel;
import pharma.config.PopulateChoice;
import pharma.config.Status;
import pharma.config.TableUtility;
import pharma.dao.GenericJDBCDao;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.javafxlib.RadioOptions;

import java.util.List;
import java.util.Optional;

public class LotStorageView  extends DialogHandler<FieldData> {
    @Override
    protected boolean condition_event(FieldData type) throws Exception {
        return false;
    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }

    public LotStorageView(String content) {
        super(content);
    }

    @Override
    protected void initialize() {
    add_label("Seleziona Ricerca per lotto o per scaffale");
    add_radios(List.of(new RadioOptions("lotto","Lotto"),new RadioOptions("scaffale","Scaffale")), CustomDialog.Mode.Horizontal);
    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {

    }

    @Override
    protected FieldData get_return_data() {
        return null;
    }

}
