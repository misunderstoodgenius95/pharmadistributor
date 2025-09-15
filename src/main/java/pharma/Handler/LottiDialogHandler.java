package pharma.Handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import pharma.Handler.Table.ProductTableCustom;
import pharma.Model.FieldData;
import pharma.config.*;
import pharma.javafxlib.Search.FilterSearch;
import pharma.dao.FarmacoDao;
import pharma.dao.GenericJDBCDao;
import pharma.dao.LottiDao;
import pharma.javafxlib.CustomTableView.RadioButtonTableColumn;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LottiDialogHandler  extends DialogHandler<FieldData> {
    private final LottiDao lottiDao;
    private final FarmacoDao farmacoDao;
    private DatePicker production_date;
    private DatePicker elapsed_date;
    private TextField  lotto_code;
    private Spinner<Integer> spinner_quantity;
    private ObservableList<FieldData> obs;
    private Button button;
    private  TextField text_selected;
    private  ObservableList<FieldData> obs_table;
    private FieldData fieldData_row;
    private  Label label_selected;
    public LottiDialogHandler(String content,LottiDao lottiDao, FarmacoDao farmacoDao,ObservableList<FieldData> observableList) {
        super(content, new PopulateChoice(farmacoDao));
        this.lottiDao = lottiDao;
        this.farmacoDao = farmacoDao;
        this.obs=observableList;
    }


    @Override
    protected void initialize() {

    }

    @Override
    protected  <K>void  initialize(Optional<PopulateChoice<K>> optionalpopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData){
        if(optionalpopulateChoice.isPresent()) {
            obs_table= FXCollections.observableArrayList();
            PopulateChoice populateChoice = optionalpopulateChoice.get();
            lotto_code = add_text_field_with_validation("Lotto Id", Validation.Lotto_code);
            button=addButton("Seleziona Farmaco");
            getDialogPane().setPrefHeight(500);
            getDialogPane().setPrefWidth(600);
            List<FieldData> list=populateChoice.populate("farmaco");
            label_selected=add_label("Farmaco Selezionato");

            add_label(" Aggiungi Data di produzione");
            production_date = add_calendar();
            add_label(" Aggiungi Data di scadenza");
            elapsed_date = add_calendar();
            text_selected=add_text_field("");
            text_selected.setVisible(false);
            text_selected.setEditable(false);
            text_selected.setPadding(new Insets(0,0,0,0));
            text_selected.setPrefHeight(0);
            text_selected.setPrefWidth(0);
            setupListener();
            listener_button(list);

        }
    }



    private void setupListener(){
        production_date.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if(elapsed_date.getValue()!=null){
                    elapsed_date.setValue(null);
                }
                change_date();
            }
        });

    }

    @Override
    protected FieldData get_return_data() {
        if(fieldData_row==null){
            throw new IllegalArgumentException("Not found");
        }
        System.out.println("id"+fieldData_row.getId());
        return FieldData.FieldDataBuilder.getbuilder().
                setcode(lotto_code.getText()).
                setFarmaco_id(fieldData_row.getId()).
                setProduction_date(Date.valueOf(production_date.getValue()))
                .setElapsed_date(Date.valueOf(elapsed_date.getValue())).
                        setNome(fieldData_row.getNome()).
                        setUnit_misure(fieldData_row.getUnit_misure()).
                        setNome_tipologia(fieldData_row.getNome_tipologia()).
                        setNome_casa_farmaceutica(fieldData_row.getNome_casa_farmaceutica()).
                        build();

    }

    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {
        boolean success=lottiDao.insert(fieldData);
        if(success){


            obs.add(fieldData);
        }
        return success;
    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }

    private void change_date(){

        elapsed_date.setDayCellFactory(picker->new DateCell() {

            @Override
            public void updateItem(LocalDate item, boolean empty) {
                if (item.isBefore(production_date.getValue().plusDays(1))) {
                    setDisable(true);
                    setStyle("-fx-background-color: #cccccc;");
                }
            }
        });




    }

    private  void listener_button(List<FieldData> list){
     button.setOnAction(event -> {
         ProductTableCustom productTableCustom=new ProductTableCustom("Scegli Farmaco");
        TableView<FieldData> tableView=productTableCustom.getTableView();
        TextField textField_search=productTableCustom.add_text_field("Inserisci Prodotto");
        productTableCustom.getControlList().add(textField_search);

         RadioButtonTableColumn<FieldData> action_radio_col = new RadioButtonTableColumn<>() {
             @Override
             protected void onButtonClick(FieldData rowData) {
                 button.setText("Farmaco Selezionato");
                 label_selected.setText("Farmaco Selezionato: "+rowData.getNome()+" "+rowData.getNome_tipologia()+" "+rowData.getUnit_misure()+" "+rowData.getNome_principio_attivo()+" "+rowData.getQuantity());
                 text_selected.setText(rowData.getNome()+" "+rowData.getNome_tipologia()+" "+rowData.getUnit_misure()+" "+rowData.getNome_principio_attivo()+" "+rowData.getQuantity());
                 fieldData_row=rowData;
             }
         };
         tableView.getColumns().addAll(action_radio_col);



         obs_table.setAll(list);
        tableView.setItems(obs_table);

            new FilterSearch(tableView, textField_search) {
             @Override
             protected boolean condition(String newValue, FieldData fieldData) {
                 return fieldData.getNome().toLowerCase().contains(newValue.toLowerCase());
             }
         };

        productTableCustom.show();
     });


    }





}
