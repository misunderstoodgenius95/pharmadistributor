package pharma.DialogController.Table;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import pharma.DialogController.DialogControllerBase;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.config.Utility;
import pharma.javafxlib.Status;
import pharma.dao.GenericJDBCDao;
import pharma.dao.LotAssigmentShelvesDao;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.javafxlib.RadioOptions;

import java.util.List;
import java.util.Optional;

public class LotStorageView  extends DialogControllerBase<FieldData> {
    private ToggleGroup group;
    private Button btn_show;
    private TextField tf_multi;
    private LotAssigmentShelvesDao shelvesDao;
    private LottoViewTableView lottoViewTableView;
    private ObservableList<FieldData> obs_table;

    public LotStorageView(String content, LotAssigmentShelvesDao lotAssigmentShelvesDao) {
        super(content);
        this.shelvesDao=lotAssigmentShelvesDao;
        lottoViewTableView=new LottoViewTableView("Visualizza Dati");
        this.lottoViewTableView=lottoViewTableView;
        obs_table=FXCollections.observableArrayList();
        lottoViewTableView.getTableView().setItems(obs_table);
    }


    @Override
    protected boolean condition_event(FieldData type) throws Exception {
        return false;
    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }

    @Override
    protected void initialize() {
    add_label("Seleziona Ricerca per lotto o per scaffale");
    group=add_radios(List.of(new RadioOptions("lotto","Lotto"),new RadioOptions("scaffale","Scaffale")), CustomDialog.Mode.Horizontal);
    this.tf_multi=add_text_field("");
    this.btn_show=addButton("Esegui Ricerca");
    setting_value();
    listener_btn();
    }

    private void listener_btn(){
        btn_show.setOnAction(event -> {
            RadioButton radioButton =(RadioButton) group.getSelectedToggle();
            if(radioButton.getId().equals("lotto")){
            List<FieldData> dataList=shelvesDao.findbyLotCode(tf_multi.getText());
            if(dataList.isEmpty()){
                empty_value();
            }else {
                obs_table.setAll(dataList);
                lottoViewTableView.show();
            }
            }else{
                List<FieldData> dataList=shelvesDao.findbyShelfCode(tf_multi.getText());
                if(dataList.isEmpty()){
                    empty_value();
                }else {
                    obs_table.setAll(dataList);
                    lottoViewTableView.show();
                }
            }


        });

    }

    private void empty_value(){
        Utility.create_alert(Alert.AlertType.WARNING," ","Campo non presente!");

    }
    private void setting_value(){
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(newValue instanceof RadioButton radioButton){
                    if(radioButton.getId().equals("lotto")){
                        tf_multi.setPromptText("Inserisci Lotto");
                    }else{
                        tf_multi.setPromptText("Inserisci Numero Scaffale");
                    }
                }
            }
        });


    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {

    }

    @Override
    protected FieldData get_return_data() {
        return null;
    }

}
