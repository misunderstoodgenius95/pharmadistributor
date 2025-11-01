package pharma.Handler;


import algoWarehouse.*;
import pharma.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.jetbrains.annotations.TestOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.Handler.Table.LottoTableBase;
import pharma.Handler.Table.TableBase;
import pharma.config.PopulateChoice;
import pharma.config.Status;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.dao.*;
import pharma.javafxlib.CustomTableView.RadioButtonTableColumn;

import java.util.*;

public class LottoStorageHandler extends DialogHandler<FieldData> {
    private static final Logger log = LoggerFactory.getLogger(LottoStorageHandler.class);
    private TextField  textField_lot_code;
    private TableView<FieldData> tableView;
    private LottiDao lottiDao;
    private Button select_lot;
    private TableBase<FieldData> choiceLogDialog;
    private Button bnt_lot_dimension;
    private Spinner<Integer> quantity;
    private  Optional<FieldData>  optionalRowChoice;
    private RadioButtonTableColumn<FieldData> radio_btn_choice_lot;
private SellerOrderDetails s_order_details;
private SellerOrderDao s_order;
private LotDimensionDao lotDimensionDao;
private MagazzinoDao magazzinoDao;
private  LotAssigmentDao lotAssigmentDao;
private  LotAssigmentShelvesDao lotAssigmentShelvesDao;
private LotAssigment choice_assigment;
 private    TableView<ShelvesAssigment> table_choice;
private ShelfDao shelfDao;
private ShelvesDao shelvesDao;
private  Button btn_calculate_warehouse;
    private LotDimensionDao lotDimension_dao;
    private Label visibile_label;
    CustomLotsDimension lotDimensionDialog;
    FarmaciaDao farmaciaDao;
    private ObservableList<Control> optional_value;
;
    public LottoStorageHandler(String content, List<GenericJDBCDao> genericJDBCDao) {
        super(content, genericJDBCDao);
        lotDimension_dao =(LotDimensionDao) genericJDBCDao.stream().
                filter(dao->dao instanceof LotDimensionDao).findFirst().orElseThrow(()->new IllegalArgumentException(" LotDimensionModel Not found!"));
        this.lottiDao = (LottiDao) genericJDBCDao.stream().filter(dao -> dao instanceof LottiDao).toList().getFirst();
        s_order_details=(SellerOrderDetails) genericJDBCDao.stream().filter(dao->dao instanceof SellerOrderDetails).findFirst().
                orElseThrow(()->new IllegalArgumentException(" SellerOrderDetails Not found!"));
        s_order=(SellerOrderDao) genericJDBCDao.stream().filter(dao->dao instanceof SellerOrderDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" SellerOrderDao Not found!"));
        farmaciaDao=(FarmaciaDao) genericJDBCDao.stream().filter(dao->dao instanceof FarmaciaDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" Farmacia Not found!"));
        lotDimensionDao=(LotDimensionDao) genericJDBCDao.stream().filter(dao->dao instanceof LotDimensionDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" Magazzino Not found!"));
        magazzinoDao=(MagazzinoDao) genericJDBCDao.stream().filter(dao->dao instanceof MagazzinoDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" Magazzino Not found!"));
        shelfDao=(ShelfDao) genericJDBCDao.stream().filter(dao->dao instanceof ShelfDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" Magazzino Not found!"));
        shelvesDao=(ShelvesDao) genericJDBCDao.stream().filter(dao->dao instanceof ShelvesDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" Magazzino Not found!"));
        lotAssigmentDao=(LotAssigmentDao) genericJDBCDao.stream().filter(dao->dao instanceof LotAssigmentDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" Magazzino Not found!"));
        lotAssigmentShelvesDao=(LotAssigmentShelvesDao) genericJDBCDao.stream().filter(dao->dao instanceof LotAssigmentShelvesDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" Magazzino Not found!"));

        choiceLogDialog =new LottoTableBase("Scegli lotti");
        listener_choice_lot_dialog();
         radio_btn_choice_lot=choiceLogDialog.add_radio();
         choiceLogDialog.getRadio_value().addListener((observable, oldValue, newValue) -> {
             if(newValue!=null){
                 optionalRowChoice=Optional.of(newValue);
             }
         });


        lotDimensionDialog =new CustomLotsDimension("", List.of(lotDimension_dao,s_order_details,s_order,farmaciaDao,magazzinoDao,lottiDao),radio_btn_choice_lot.value_radio_property_choiceProperty() );




    }


    public CustomLotsDimension getLotDimensionDialog() {
        return lotDimensionDialog;
    }

    @TestOnly
    public TableBase getTableLot(){
        return choiceLogDialog;
    }

    public Button getBnt_lot_dimension() {
        return bnt_lot_dimension;
    }

    @TestOnly
    public Button getSelect_lot() {
        return select_lot;
    }
    @TestOnly

    public TextField getTextField_lot_code() {
        return textField_lot_code;
    }

    @Override
    protected void initialize() {
        getDialogPane().setPrefWidth(700);
        getDialogPane().setPrefHeight(800);
        optional_value= FXCollections.observableArrayList();
        textField_lot_code=add_text_field("Inserisci Lotto");
        select_lot=addButton("Seleziona Lotto");
        select_lot.setDisable(true);
        visibile_label= add_label("Dati dimensione prodotto non presenti");
        visibile_label.setVisible(false);
        bnt_lot_dimension=addButton("Inserisci i dati");
        bnt_lot_dimension.setVisible(false);
        add_label("Seleziona Quantità");
        quantity=add_spinner();
        btn_calculate_warehouse=addButton("Calcola Scaffali");
        calculate_warehouse();
        table_choice=add_tableCustom();



      //  add_optional_data();
        listener_text_table();
        listener_button_table();
        listener_lot_dimension();
        table_config();













    }
    // Calculate the shelf and warehouse.
    private void  calculate_warehouse() {

       btn_calculate_warehouse.setOnAction(event -> {
            if(optionalRowChoice.isPresent()) {
                    FieldData fieldData_choice=optionalRowChoice.get();
                List<PharmacyAssigned> pharmacyAssigneds = new ArrayList<>();
                MagazzinoModelDao magazzinoModelDao =new MagazzinoModelDao(shelfDao,shelvesDao, magazzinoDao);
                List<WarehouseModel> modelList=magazzinoModelDao.getFullWarehouseModel();

             List<FieldData> order_details = s_order_details.findbyProduct(fieldData_choice.getFarmaco_id());
                for (FieldData orderDetail : order_details) {
                    //obtein  the order id
                    int order_id = orderDetail.getOrder_id();
                    log.info("order_id" + order_id);
                    //obtain the  order
                    FieldData fd_order = s_order.findById(order_id);
                    // obtain the farmacia
                    int farmacia_id = fd_order.getForeign_id();

                    FieldData fd_farmacia = farmaciaDao.findById(farmacia_id);
                    Farmacia farmacia = new Farmacia(fd_farmacia.getNome(), fd_farmacia.getId(), fd_farmacia.getLocation());
                    pharmacyAssigneds.add(new PharmacyAssigned(farmacia, orderDetail.getQuantity()));

                }
                if(pharmacyAssigneds.isEmpty()){
                   pharmacyAssigneds.addAll(farmaciaDao.findAll().stream().map
                           (farmacia_fd->new PharmacyAssigned(new Farmacia(farmacia_fd.getAnagrafica_cliente(),farmacia_fd.getId(),  farmacia_fd.getLocation()),0)).toList());
                }
// find lot dimension
                Optional<LotDimensionModel> dimension = lotDimensionDao.findByLots(fieldData_choice.getCode(),fieldData_choice.getFarmaco_id());
                if (dimension.isPresent()) {
                    LotDimensionModel lotDimensionModel = dimension.get();
                   ChoiceWarehouse choiceWarehouse = new ChoiceWarehouse(modelList, pharmacyAssigneds);
                List<WarehouseDistances> distances= choiceWarehouse.calculate_warehouse(lotDimensionModel, quantity.getValue());
                  if(!table_choice.getItems().isEmpty()){
                      table_choice.getItems().clear();
                  }
                   distances.forEach(ws->{
                    PlacementShelf shelf=new PlacementShelf(ws.getWarehouseModel().getShelfInfos());
                    choice_assigment=shelf.assignmentLots(lotDimensionModel,quantity.getValue());
                    table_choice.getItems().addAll(choice_assigment.getShelvesAssigmentList());


                });



                }
            }
        });



    }
    private void table_config(){
        table_choice.setPrefHeight(400);
        table_choice.getColumns().addAll(
                TableUtility.generate_column_string("Magazzino Id","magazzino_id"),
                TableUtility.generate_column_string("Scaffale","shelf_code"),
                TableUtility.generate_column_int("Ripiano","shelf_level"),
                TableUtility.generate_column_int("Qty","quantity"));


    }
// LotDimension Action Button if the dimensions it is not setting, settings
    private void listener_choice_lot_dialog() {
        choiceLogDialog.getButtonOK().setOnAction(event -> {
            Object object = radio_btn_choice_lot.getValue_radio_property_choice();
            if (object instanceof FieldData fieldData) {

                Optional<LotDimensionModel> dimensionModel = lotDimension_dao.findByLots(fieldData.getCode(), fieldData.getFarmaco_id());
                if (dimensionModel.isEmpty()) {
                    visibile_label.setVisible(true);
                    log.info("Set true");
                    bnt_lot_dimension.setVisible(true);
                    visibile_label.setVisible(false);
                }



            }
        });
    }


    public Label getVisibile_label() {
        return visibile_label;
    }

    private void listener_lot_dimension(){
        bnt_lot_dimension.setOnAction(event -> {
            lotDimensionDialog.execute();
            Button button=(Button) event.getSource();
            button.setText("Dati inseriti");
            button.setDisable(true);


        });

    }

    private void listener_text_table(){
        textField_lot_code.setOnKeyReleased(event -> {
            select_lot.setDisable(textField_lot_code.getText().isEmpty());


        });
    }

    private void change_visibility(boolean value){
        optional_value.forEach(control -> control.setVisible(value));
    }


    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {

    }

    @Override
    protected FieldData get_return_data() {

        return FieldData.FieldDataBuilder.getbuilder().setcode(choiceLogDialog.getRadio_value().get().getCode()).
                setFarmaco_id(choiceLogDialog.getRadio_value().get().getFarmaco_id()).setQuantity(quantity.getValue()).build();

    }


    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {

        if (choice_assigment == null) {
            log.error("choice_assigment is null - user must click 'Calcola Scaffali' button first");
            return false;
        }
        if(choice_assigment.getShelvesAssigmentList()==null){
            log.error("shelves are null");

        }

        int id=lotAssigmentDao.findExistAndReturnId(choice_assigment.getFarmaco_id(),choice_assigment.getLot_code());
        if(id==-1) {
            id = lotAssigmentDao.insertAndReturnID(choice_assigment);
        }else{
            lotAssigmentDao.update(choice_assigment);
        }
         final int finalId = id;
        return table_choice.getItems().stream().peek(
               value->value.setLot_assigment(finalId)
        ).allMatch(assigment-> lotAssigmentShelvesDao.insert(assigment));




    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }
// button table dialog
    private void listener_button_table(){
        select_lot.setOnAction(event -> {


            System.out.println("size"+choiceLogDialog.getTableView().getItems().size());
            if(!choiceLogDialog.getTableView().getItems().isEmpty()){
                choiceLogDialog.getTableView().getItems().clear();
            }
            List<FieldData> list=lottiDao.findByLottoCode(textField_lot_code.getText());

            if(!list.isEmpty()) {
                choiceLogDialog.getTableView().getItems().addAll(list);

                choiceLogDialog.show();

            }else{
                Utility.create_alert(Alert.AlertType.WARNING,"","No Lottocode found");
            }

          //  tableBase.getTableViewProductTable().getItems()


        });

    }
}
