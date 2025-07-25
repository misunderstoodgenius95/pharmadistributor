package pharma.Handler;

import algo.ChoiceWarehouse;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.jetbrains.annotations.TestOnly;
import pharma.Handler.Table.LotTableBase;
import pharma.Model.ChoiceAssigned;
import pharma.Model.Farmacia;
import pharma.Model.FieldData;
import pharma.Model.Warehouse;
import pharma.config.PopulateChoice;
import pharma.config.Utility;
import pharma.dao.*;
import pharma.javafxlib.Dialog.CustomDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LottoStorageHandler extends DialogHandler<FieldData> {
    private TextField  textField_lot_code;
    private TableView<FieldData> tableView;
    private LottiDao lottiDao;
    private Button select_lot;
    private LotTableBase lotTableBase;
    private Button bnt_lot_dimension;
    private Spinner<Integer> quantity;
private SellerOrderDetails s_order_details;
private SellerOrderDao s_order;
private MagazzinoDao magazzinoDao;
    private LotDimension lotDimension;
    private Label visibile_label;
    CustomLots customLots;
    FarmaciaDao farmaciaDao;
    private ObservableList<Control> optional_value;
    public LottoStorageHandler(String content, List<GenericJDBCDao> genericJDBCDao) {
        super(content, genericJDBCDao);
        lotDimension=(LotDimension) genericJDBCDao.stream().
                filter(dao->dao instanceof LotDimension).findFirst().orElseThrow();
        this.lottiDao = (LottiDao) genericJDBCDao.stream().filter(dao -> dao instanceof LottiDao).toList().getFirst();
        s_order_details=(SellerOrderDetails) genericJDBCDao.stream().filter(dao->dao instanceof SellerOrderDetails).findFirst().
                orElseThrow(()->new IllegalArgumentException(" SellerOrderDetails Not found!"));
        s_order=(SellerOrderDao) genericJDBCDao.stream().filter(dao->dao instanceof SellerOrderDetails).findFirst().
                orElseThrow(()->new IllegalArgumentException(" SellerOrderDao Not found!"));
        farmaciaDao=(FarmaciaDao) genericJDBCDao.stream().filter(dao->dao instanceof FarmaciaDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" Farmacia Not found!"));
        magazzinoDao=(MagazzinoDao) genericJDBCDao.stream().filter(dao->dao instanceof MagazzinoDao).findFirst().
                orElseThrow(()->new IllegalArgumentException(" Farmacia Not found!"));
         lotTableBase=new LotTableBase("Scegli lotti");
         lotTableBase.add_radio();
        //lotTableBase.add_check_box_column();
        customLots=new CustomLots("", List.of(lotDimension),lotTableBase.getCheckBoxValue());
        listener_addedd_table_lot();



    }








    @TestOnly
    public LotTableBase getTableLot(){
        return  lotTableBase;
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
        quantity=add_spinner();

      //  add_optional_data();
        listener_text_table();
        listener_button_table();
        listener_lot_dimension();














    }
 /*   private void listener_addedd_table_lot(){

        lotTableBase.getCheckBoxValue().addListener(new ChangeListener<FieldData>() {
            @Override
            public void changed(ObservableValue<? extends FieldData> observable, FieldData oldValue, FieldData newValue) {
                if(newValue!=null){
                    select_lot.setText("Lotto Selezionato");
                    FieldData fieldData=lotDimension.findByIds(newValue.getFarmaco_id(),newValue.getCode());
                    if(fieldData==null){
                        visibile_label.setVisible(true);
                        bnt_lot_dimension.setVisible(true);

                    }




                }
            }
        });
    }*/
    private void listener_addedd_table_lot(){

        lotTableBase.radio_valueProperty().addListener(new ChangeListener<FieldData>() {
            @Override
            public void changed(ObservableValue<? extends FieldData> observable, FieldData oldValue, FieldData newValue) {
                if(newValue!=null){
                    select_lot.setText("Lotto Selezionato");
                  Optional<FieldData> optionalFieldData=lotDimension.findByIds(newValue.getFarmaco_id(),newValue.getCode());
                  if(optionalFieldData.isPresent()){
                       visibile_label.setVisible(true);
                       bnt_lot_dimension.setVisible(true);

                   }




                }
            }
        });
    }
    private void listener_lot_dimension(){
        bnt_lot_dimension.setOnAction(event -> {
            customLots.execute();








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

        return FieldData.FieldDataBuilder.getbuilder().setcode(lotTableBase.getCheckBoxValue().get().getCode()).
                setFarmaco_id(lotTableBase.getCheckBoxValue().get().getFarmaco_id()).setQuantity(quantity.getValue()).build();

    }


    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {
        //find by farmaco_id and lotto_id
     List<ChoiceAssigned> choiceAssigneds=new ArrayList<>();
        List<Warehouse> fd_warehouse=magazzinoDao.findAll().stream().map(value->new Warehouse(value.getId(),value.getNome(),value.getLocation())).toList();

        List<FieldData> order_details=s_order_details.findbyProduct(fieldData.getFarmaco_id(),fieldData.getCode());
        for (FieldData orderDetail : order_details) {
            //obtein  the order id
            int order_id=orderDetail.getOrder_id();
         //obtein the  order
            FieldData fd_order=s_order.findById(order_id);
            // obetin the farmacia
            int farmacia_id=fd_order.getForeign_id();

            FieldData fd_farmacia=farmaciaDao.findById(farmacia_id);
           Farmacia farmacia= new Farmacia(fd_farmacia.getNome(),fd_farmacia.getId(), fd_farmacia.getLocation());
            choiceAssigneds.add(new ChoiceAssigned(farmacia,orderDetail.getQuantity()));

        }
        ChoiceWarehouse choiceWarehouse=new ChoiceWarehouse(fd_warehouse,choiceAssigneds);





        return true;
    }
    private void listener_button_table(){
        select_lot.setOnAction(event -> {



            List<FieldData> list=lottiDao.findByLottoCode(textField_lot_code.getText());
            if(!list.isEmpty()) {
                lotTableBase.getTableViewProductTable().getItems().addAll(list);
                lotTableBase.show();
            }else{
                Utility.create_alert(Alert.AlertType.WARNING,"","No Lottocode found");
            }

          //  lotTableBase.getTableViewProductTable().getItems()


        });

    }
}
