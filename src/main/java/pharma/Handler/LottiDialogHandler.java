package pharma.Handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.dao.FarmacoDao;
import pharma.dao.LottiDao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class LottiDialogHandler  extends DialogHandler{
    private final LottiDao lottiDao;
    private final FarmacoDao farmacoDao;
    private SearchableComboBox<FieldData> farmaco_searchable;
    private DatePicker production_date;
    private DatePicker elapsed_date;
    private TextField  lotto_code;
    private  TextField price_text;
    private Spinner<Integer> spinner_quantity;
    private ObservableList<FieldData> obs;
    private Spinner<Integer> spinner_vat;
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
    protected void initialize(PopulateChoice populateChoice) {
        lotto_code=add_text_field_with_validation("Lotto Id",Validation.Lotto_code);
        farmaco_searchable=add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Aggiungi Farmaco").build());
        farmaco_searchable.getItems().addAll(populateChoice.populate("farmaco"));
       add_label(" Aggiungi Data di produzione");
        production_date=add_calendar();
       add_label(" Aggiungi Data di scadenza");
        elapsed_date=add_calendar();
        price_text=add_text_field_with_validation("Aggiungi Prezzo 0.0",Validation.Double_Digit);
        spinner_vat=add_spinner();
      add_label(" Aggiungi quantità");
        spinner_quantity=add_spinner();
        setupListener();
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
        return FieldData.FieldDataBuilder.getbuilder().setLotto_id(lotto_code.getText()).setTipologia(farmaco_searchable.getValue().getId()).
                setProduction_date(Date.valueOf(production_date.getValue()))
                .setElapsed_date(Date.valueOf(elapsed_date.getValue()))
                .setPrice(Double.parseDouble(price_text.getText()))
                .setQuantity(spinner_quantity.getValue()).setVat_percent(spinner_vat.getValue()).build();

    }

    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {
        boolean success=lottiDao.insert(fieldData);
        if(success){
          FieldData fieldData_obs= FieldData.FieldDataBuilder.getbuilder().setLotto_id(fieldData.getLotto_id()).
                    setTipologia(fieldData.getTipologia()).
                    setProduction_date(fieldData.getProduction_date())
                    .setElapsed_date(fieldData.getElapsed_date())
                    .setPrice(fieldData.getPrice())
                    .setQuantity(fieldData.getQuantity()).setVat_percent(fieldData.getVat_percent()).
                  setNome(farmaco_searchable.getValue().getNome()).
                  setUnit_misure(farmaco_searchable.getValue().getUnit_misure()).
                  setNome_tipologia(farmaco_searchable.getValue().getNome_tipologia()).
                  setNome_casa_farmaceutica(farmaco_searchable.getValue().getNome_casa_farmaceutica()).
                  build();

            obs.add(fieldData_obs);
        }
        return success;
    }
    private void change_date(){
        System.out.println("execute change");
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





}
