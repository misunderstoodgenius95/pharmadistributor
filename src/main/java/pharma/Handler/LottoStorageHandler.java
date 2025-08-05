package pharma.Handler;

import algo.LotDimensionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.jetbrains.annotations.TestOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.Handler.Table.LottoTableBase;
import pharma.Handler.Table.TableBase;
import pharma.Model.PharmacyAssigned;
import pharma.Model.Farmacia;
import pharma.Model.FieldData;
import pharma.Model.Warehouse;
import pharma.config.PopulateChoice;
import pharma.config.Utility;
import pharma.dao.*;
import pharma.javafxlib.CustomTableView.RadioButtonTableColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LottoStorageHandler extends DialogHandler<FieldData> {
    private static final Logger log = LoggerFactory.getLogger(LottoStorageHandler.class);
    private TextField  textField_lot_code;
    private TableView<FieldData> tableView;
    private LottiDao lottiDao;
    private Button select_lot;
    private TableBase<FieldData> choiceLogDialog;
    private Button bnt_lot_dimension;
    private Spinner<Integer> quantity;
    private RadioButtonTableColumn<FieldData> radio_btn_choice_lot;
private SellerOrderDetails s_order_details;
private SellerOrderDao s_order;
private MagazzinoDao magazzinoDao;
    private LotDimensionDao lotDimension_dao;
    private Label visibile_label;
    CustomLotsDimension lotDimensionDialog;
    FarmaciaDao farmaciaDao;
    private ObservableList<Control> optional_value;
    public LottoStorageHandler(String content, List<GenericJDBCDao> genericJDBCDao) {
        super(content, genericJDBCDao);
        lotDimension_dao =(LotDimensionDao) genericJDBCDao.stream().
                filter(dao->dao instanceof LotDimensionDao).findFirst().orElseThrow(()->new IllegalArgumentException(" LotDimensionModel Not found!"));
        this.lottiDao = (LottiDao) genericJDBCDao.stream().filter(dao -> dao instanceof LottiDao).toList().getFirst();
       /* s_order_details=(SellerOrderDetails) genericJDBCDao.stream().filter(dao->dao instanceof SellerOrderDetails).findFirst().
                orElseThrow(()->new IllegalArgumentException(" SellerOrderDetails Not found!"));
        s_order=(SellerOrderDao) genericJDBCDao.stream().filter(dao->dao instanceof SellerOrderDetails).findFirst().
                orElseThrow(()->new IllegalArgumentException(" SellerOrderDao Not found!"));
        farmaciaDao=(FarmaciaDao) genericJDBCDao.stream().filter(dao->dao instanceof FarmaciaDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" Farmacia Not found!"));
        magazzinoDao=(MagazzinoDao) genericJDBCDao.stream().filter(dao->dao instanceof MagazzinoDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" Farmacia Not found!"));*/

        choiceLogDialog =new LottoTableBase("Scegli lotti");
        listener_choice_lot_dialog();
         radio_btn_choice_lot=choiceLogDialog.add_radio();






        lotDimensionDialog =new CustomLotsDimension("", List.of(lotDimension_dao),radio_btn_choice_lot.value_radio_property_choiceProperty() );




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

      //  add_optional_data();
        listener_text_table();
        listener_button_table();
        listener_lot_dimension();














    }
/*  private void listener_addedd_table_lot(){

        tableBase.getCheckBoxValue().addListener(new ChangeListener<FieldData>() {
            @Override
            public void changed(ObservableValue<? extends FieldData> observable, FieldData oldValue, FieldData newValue) {
                if(newValue!=null){
                    select_lot.setText("Lotto Selezionato");
                    FieldData fieldData=lotDimension.(newValue.getFarmaco_id(),newValue.getCode());
                    if(fieldData==null){
                        visibile_label.setVisible(true);
                        bnt_lot_dimension.setVisible(true);

                    }




                }
            }
        });
    }*/
    private void listener_choice_lot_dialog(){
            choiceLogDialog.getButtonOK().setOnAction(event -> {
                Object object=radio_btn_choice_lot.getValue_radio_property_choice();
              if(object instanceof  FieldData fieldData){

                  Optional<LotDimensionModel> dimensionModel =lotDimension_dao.findByLots(fieldData.getCode(),fieldData.getFarmaco_id());
                  if(dimensionModel.isEmpty()){
                      visibile_label.setVisible(true);
                      log.info("Set true");
                      bnt_lot_dimension.setVisible(true);



                  }
              }

            });
       /* tableBase.radio_valueProperty().addListener(new ChangeListener<FieldData>() {
            @Override
            public void changed(ObservableValue<? extends FieldData> observable, FieldData oldValue, FieldData newValue) {
                if(newValue!=null){
                    select_lot.setText("Lotto Selezionato");
                  Optional<FieldData> optionalFieldData=lotDimension.findByIds(newValue.getFarmaco_id(),newValue.getCode());

                  if(optionalFieldData.isPresent()){
                       visibile_label.setVisible(true);
                       bnt_lot_dimension.setVisible(true);

                   }else{
                      log.info("empty");


                  }




                }
            }
        });*/
    }


    public Label getVisibile_label() {
        return visibile_label;
    }

    private void listener_lot_dimension(){
        bnt_lot_dimension.setOnAction(event -> {
            lotDimensionDialog.execute();
            Button button=(Button) event.getSource();
            button.setText("Dati inseriti");

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
        //find by farmaco_id and lotto_id
     List<PharmacyAssigned> pharmacyAssigneds =new ArrayList<>();
        List<Warehouse> fd_warehouse=magazzinoDao.findAll().stream().map(value->new Warehouse(value.getId(),value.getNome(),value.getpGgeometry())).toList();

        List<FieldData> order_details=s_order_details.findbyProduct(fieldData.getFarmaco_id(),fieldData.getCode());
        for (FieldData orderDetail : order_details) {
            //obtein  the order id
            int order_id=orderDetail.getOrder_id();
         //obtein the  order
            FieldData fd_order=s_order.findById(order_id);
            // obtein the farmacia
            int farmacia_id=fd_order.getForeign_id();

            FieldData fd_farmacia=farmaciaDao.findById(farmacia_id);
           Farmacia farmacia= new Farmacia(fd_farmacia.getNome(),fd_farmacia.getId(), fd_farmacia.getLocation());
            pharmacyAssigneds.add(new PharmacyAssigned(farmacia,orderDetail.getQuantity()));

        }
        /*ChoiceWarehouse choiceWarehouse=new ChoiceWarehouse(fd_warehouse,pharmacyAssigneds);*/





        return true;
    }
    private void listener_button_table(){
        select_lot.setOnAction(event -> {



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
