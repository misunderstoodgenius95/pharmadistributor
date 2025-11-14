package pharma.Handler;

import algoWarehouse.LotAssigment;
import algoWarehouse.ShelfInfo;
import algoWarehouse.ShelvesAssigment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import jdk.jshell.execution.Util;
import org.controlsfx.control.SearchableComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.Handler.Table.TableBase;
import pharma.Model.FieldData;
import pharma.Model.WarehouseModel;
import pharma.config.PopulateChoice;
import pharma.config.Status;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.config.View.BasicConvert_FD;
import pharma.config.View.BasicConvert_WS;
import pharma.config.spinner.SpinnerGeneric;
import pharma.dao.*;
import pharma.javafxlib.Controls.TextFieldComboBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class SpostaLottoHandler extends DialogHandler<FieldData> {
    private static final Logger log = LoggerFactory.getLogger(SpostaLottoHandler.class);
    private LotAssigmentDao lotAssigment;
    private LotAssigmentShelvesDao shelvesAssigment;
    private TextField tf_lots;
    private Button btn_choice;
    private TableBase<FieldData> tableBase;
    private TableView<FieldData> tableView_selected;
    private ObservableList<FieldData> obs_selected;
    private TextFieldComboBox<WarehouseModel> s_warehouse;
    private TextFieldComboBox<ShelfInfo> s_shelf;
    private TextFieldComboBox<FieldData> s_shelves;
    private Button btn_add;
    public SpostaLottoHandler(String content, LotAssigmentDao lotAssigment, LotAssigmentShelvesDao shelvesAssigment, MagazzinoDao magazzinoDao, ShelfDao shelfDao) {
        super(content, List.of(magazzinoDao,shelfDao));
        this.lotAssigment = lotAssigment;
        this.shelvesAssigment = shelvesAssigment;
        tableBase=new TableBase<FieldData>("Scegli Lotto") {
            @Override
            protected void setupBaseColumns(TableView<FieldData> tableView) {
                tableView.getColumns().addAll(TableUtility.generate_column_string("Codice Lotto","code"),
                        TableUtility.generate_column_string("Casa Faramaceutica","nome_casa_farmaceutica")
                        );

            }
        };
        tableBase.add_radio();

        getDialogPane().setPrefWidth(800);
        getDialogPane().setPrefHeight(800);
        listener_btn_table_choice();
        obs_selected= FXCollections.observableArrayList();

    }
    private void empty_value(){
        Utility.create_alert(Alert.AlertType.WARNING," ","Campo non presente!");
    }

    private void  setting_table_select(){
        TableColumn<FieldData, Integer> tc_quantity=TableUtility.generate_column_int(" Modifica Disponibilità","quantity");
        tc_quantity.setCellFactory(tc-> new SpinnerGeneric());
        tableView_selected.getColumns().addAll(TableUtility.generate_column_double("Codice Lotto","code"),
                TableUtility.generate_column_int("Magazzino Id","foreign_id"),
                TableUtility.generate_column_string("Nome Magazzino","nome"),
                TableUtility.generate_column_string("Scaffale","shelf_code"),
                TableUtility.generate_column_int("Ripiano","shelves_code"),tc_quantity,TableUtility.generate_column_int("Disponibilità","quantity"),
                TableUtility.generate_column_int("Quantità A/D","s_quantity"));
    }
    private  void listener_btn_send(){
        getButtonOK().addEventFilter(ActionEvent.ACTION,event -> {
            int result=tableView_selected.getItems().stream().map(FieldData::getS_quantity).reduce((a, b)->a -b).orElse(0);
            if(result!=0){
                Utility.create_alert(Alert.AlertType.WARNING,"","La somma non corrisponde pari a zero");
                event.consume();

            }







        });


    }
    private void listener_choice(){
        btn_choice.setOnAction(event -> {
           List<FieldData> list=lotAssigment.findPharmaHousebyLotCode(tf_lots.getText());
            if(list.isEmpty()) {
                empty_value();
            }else{
                tableBase.getTableView().getItems().setAll(list);
                tableBase.show();
            }
            btn_choice.setText("Lotto Scelto");


        });
    }
    private void listener_btn_table_choice(){
        tableBase.getButtonOK().setOnAction(event -> {
            if(tableBase.getRadio_value().getValue()==null){
                Utility.create_alert(Alert.AlertType.WARNING,"","Lotto non selezionato");
                tableBase.show();
            }else{
                List<FieldData> list=shelvesAssigment.findbyLotCode(tf_lots.getText());
                log.info("list"+list.size());
                obs_selected.setAll(list);
                tableView_selected.setItems(obs_selected);
                disabled_choice(false);

            }

        });





    }



    @Override
    protected boolean condition_event(FieldData type) throws Exception {
        System.out.println("executedd");
        shelvesAssigment.setTransaction(true);
        boolean result=tableView_selected.getItems().stream().allMatch(value->{

            if(value.s_quantityProperty().get()==0){
                return  true;
            }
            if(value.getId()==0){
               ShelvesAssigment assigment=new ShelvesAssigment(value.getShelf_code(),value.s_quantityProperty().get(),value.getShelves_code(),value.getId(),value.getCapacity());
              return shelvesAssigment.insert(assigment);
            }else{
                 return shelvesAssigment.update(new ShelvesAssigment(value.getId(),value.s_quantityProperty().get()));
            }
        });
        if(result){
            shelvesAssigment.commit();
        }else{
            shelvesAssigment.rollback();
        }
        shelvesAssigment.setTransaction(false);




        return true;
    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }

    @Override
    protected void initialize() {



    }



    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {
        if(optionalgenericJDBCDao.isPresent()) {
            MagazzinoDao magazzinoDao=(MagazzinoDao)optionalgenericJDBCDao.get().stream().filter(value->value instanceof MagazzinoDao).findFirst().get();
            ShelfDao shelfDao=(ShelfDao) optionalgenericJDBCDao.get().stream().filter(value->value instanceof ShelfDao).findFirst().get();
            tf_lots = add_text_field("Scegli Lotto Da Spostare");
            btn_choice = addButton("Scegli Lotto");
            listener_choice();
            tableView_selected = add_table();
            tableView_selected.setItems(obs_selected);

            s_warehouse=addComboWithTextNoValidation(FXCollections.observableArrayList());

            System.out.println("size"+getControlList().size());
            s_warehouse.getChoiceBox().setValue(new WarehouseModel("Scegli Magazzino"));
            s_warehouse.getChoiceBox().setItems(FXCollections.observableArrayList(magazzinoDao.findAll()));
            s_warehouse.getChoiceBox().setConverter(new BasicConvert_WS());
            s_shelf = addComboWithTextNoValidation(FXCollections.observableArrayList());
            s_shelf.getChoiceBox().setValue(ShelfInfo.ShelfInfoBuilder.get_builder().setShelf_code("Scegli Scaffale").build());
            FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Scaffale").build();
            setting_convert_shelf();

            s_shelves = addComboWithTextNoValidation(FXCollections.observableArrayList());

            s_shelves.getChoiceBox().setValue(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Ripiano").build());
            setting_convert_shelevs();
            btn_add=addButton("Aggiungi");
            btn_add.setDisable(true);
            setting_table_select();
            listener_warehouse(shelfDao);
            listener_shelf();
            disabled_choice(true);
            listener_btnAdd();
            listener_btn_send();
        }

    }

    private void listener_btnAdd(){
        btn_add.setOnAction(event -> {
            WarehouseModel warehouseModel=(WarehouseModel) s_warehouse.getChoiceBox().getValue();
            ShelfInfo shelfInfo=(ShelfInfo) s_shelf.getChoiceBox().getValue();
            FieldData fd_shelves=(FieldData)s_shelves.getChoiceBox().getValue();
            int num_rip= fd_shelves.getNum_rip();




         tableView_selected.getItems().add(FieldData.FieldDataBuilder.getbuilder().setcode(tf_lots.getText()).
                    setNome(warehouseModel.getNome()).setForeign_id(warehouseModel.getId()).setShelf_code(shelfInfo.getShelf_code()).setShelves_code(num_rip).setIs_enable(true).build());






        });




    }
    private void listener_warehouse(ShelfDao shelfDao){
        s_warehouse.getChoiceBox().setOnAction(event -> {
            WarehouseModel w_model= (WarehouseModel) s_warehouse.getChoiceBox().getValue();
            List<ShelfInfo> list=shelfDao.findShelfByMagazzinoId(w_model.getId());
            s_shelf.getChoiceBox().setItems(FXCollections.observableArrayList(list));
        });


    }

    private void listener_shelf(){
       s_shelf.getChoiceBox().valueProperty().addListener(new ChangeListener<ShelfInfo>() {
           @Override
           public void changed(ObservableValue<? extends ShelfInfo> observable, ShelfInfo oldValue, ShelfInfo newValue) {
               if(newValue!=null){
                   System.out.println("num_ripiani"+newValue.getNum_rip());
                   int index=newValue.getNum_rip();
                   List<FieldData> list=new ArrayList<>();
                for(int i=1;i<=index;i++){
                    list.add(FieldData.FieldDataBuilder.getbuilder().setNum_rip(i).setcode("ax").build());

                }
                   s_shelves.getChoiceBox().setItems(FXCollections.observableArrayList(list));
                btn_add.setDisable(false);




               }
           }


       });

    }

    @Override
    protected FieldData get_return_data() {
        return FieldData.FieldDataBuilder.getbuilder().setId(1).build();
    }


    private void setting_convert_shelf(){


        s_shelf.getChoiceBox().setConverter(new StringConverter<ShelfInfo>() {
            @Override
            public String toString(ShelfInfo object) {
                if(object!=null) {

                    return " " + object.getShelf_code();
                }
                return "";
            }

            @Override
            public ShelfInfo fromString(String string) {
                return null;
            }
        });
    }


    private void setting_convert_shelevs(){

        s_shelves.getChoiceBox().setConverter(new StringConverter<FieldData>() {
            @Override
            public String toString(FieldData object) {
                if(object!=null){
                    if(object.getNome()!=null){
                        return " "+object.getNome();
                    }
                    return ""+object.getNum_rip();
                }
                return "";
            }

            @Override
            public FieldData fromString(String string) {
                return null;
            }
        });
    }

    private void disabled_choice(boolean value){
        s_warehouse.getChoiceBox().setDisable(value);
        s_shelves.setDisable(value);
        s_shelf.setDisable(value);
    }
}
