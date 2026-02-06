package pharma.DialogController.Report;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import pharma.DialogController.DialogControllerBase;
import pharma.Model.FieldData;
import pharma.Service.Report.FormuleEngine;
import pharma.Service.Report.UserFormula;
import pharma.config.PopulateChoice;
import pharma.config.TableUtility;
import pharma.dao.GenericJDBCDao;
import pharma.javafxlib.Controls.TextFieldComboBox;
import pharma.javafxlib.CustomTableView.TableColumnButtonBase;
import pharma.javafxlib.Status;

import java.util.List;
import java.util.Optional;

public class CustomFormule extends DialogControllerBase<FieldData> {
    private TextFieldComboBox<String> formuleSearchable;
    private  TextFieldComboBox<String> operazioniSearchable;
    private TableView<UserFormula> tableView;
    private ObservableList<UserFormula> obs_tableview;
    private TableColumnButtonBase<UserFormula> tableButton;
    @Override
    protected boolean condition_event(FieldData type) throws Exception {
        return false;
    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }

    public CustomFormule(String content) {
        super(content);
        getDialogPane().setPrefWidth(800);
        getDialogPane().setPrefHeight(900);
        this.add_label("Crea la tua formula");

        operazioniSearchable=add_combox_search_with_textfield(FXCollections.observableArrayList());
        operazioniSearchable.getChoiceBox().setValue("Scegli Operazioni");
       // operazioniSearchable.getChoiceBox().setConverter(new OperazioniConvert());
        //operazioniSearchable.getChoiceBox().getItems().setAll(FormuleEngine.operazioni_map.keySet());
        formuleSearchable=this.add_combox_search_with_textfield(FXCollections.observableArrayList());
        //formuleSearchable.getChoiceBox().setConverter(new FormuleConvert());
        formuleSearchable.getChoiceBox().setValue("Scegli la formula");
        formuleSearchable.getChoiceBox().getItems().addAll(FormuleEngine.dati_map.keySet());
        tableView=this.add_tableCustom();
       /* obs_tableview=FXCollections.observableArrayList();
        tableView.setItems(obs_tableview);
        tableButton=new TableColumnButtonBase<>("","Rimuovi Riga",value->{
            obs_tableview.remove(value);
            tableView.refresh();
            return null;

        });
        setting_table_view();*/
        listener_formule();
        listener_operazioni();


    }




    private void setting_table_view(){
        tableView.getColumns().addAll(
                TableUtility.generate_column_string("Operazione","operazione"),
        TableUtility.generate_column_string("Formula","formula"),tableButton);



    }

    @Override
    protected void initialize() {

    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {

    }

    @Override
    protected FieldData get_return_data() {
        return null;
    }
    public void listener_operazioni() {
  /*      operazioniSearchable.getChoiceBox().valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    UserFormula formuleOperazioni=new UserFormula(newValue.toString());
                    obs_tableview.add(formuleOperazioni);


                });
*/
    }

        public void listener_formule() {

/*
        formuleSearchable.getChoiceBox().valueProperty().addListener(
        (observable, oldValue, newValue) -> {
            obs_tableview.getLast().setDato(newValue.toString());
            tableView.refresh();


        });
*/

        }










}
