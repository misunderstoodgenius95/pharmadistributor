package pharma.Handler;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.config.Status;
import pharma.dao.GenericJDBCDao;
import pharma.dao.PromotionDao;
import pharma.dao.SellerPriceDao;
import pharma.formula.promotion.PromotionsSuggest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class SuggestPromotionHandler extends DialogHandler<FieldData> {
    private SearchableComboBox<FieldData> search_product;
    private Button btn_calculate_price;
    private TextField textField_promotion;
    private PromotionsSuggest promotionsSuggest;
    private SellerPriceDao sellerPriceDao;
    private SimpleBooleanProperty s_boolean;
    private PromotionDao promotionDao;
    private Label price_value;
    private DatePicker date_end;
     private  SearchableComboBox<FieldData> searchableComboBox;
    public SuggestPromotionHandler(String content, SellerPriceDao sellerPriceDao, PromotionsSuggest suggest, PromotionDao promotionDao,SimpleBooleanProperty s_boolean) {
        super(content, List.of(promotionDao));
        this.sellerPriceDao = sellerPriceDao;
        this.promotionsSuggest=suggest;
        this.s_boolean=s_boolean;
        this.promotionDao=promotionDao;
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
        if(optionalgenericJDBCDao.isPresent()){
            PromotionDao promodao = (PromotionDao) optionalgenericJDBCDao.get().stream().filter(value -> value instanceof PromotionDao).findFirst().get();
             searchableComboBox=this.search_product = add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Cerca Farmaco").build());
            this.search_product.setItems(FXCollections.observableArrayList(promodao.getProductNoPromotionActive()));
            this.add_label("Prezzo:");
            price_value=this.add_label("");
            this.btn_calculate_price=addButton("Calcola valore suggerito");
            this.add_label("Sconto Suggerito %");
            textField_promotion =this.add_text_field("");
            add_label("Scegli la data di fine");
            date_end=add_calendar();
            search_product.setConverter(new StringConverter<>() {
                @Override
                public String toString(FieldData object) {
                    if (object == null) {
                        return "";

                    }
                    if (object.getUnit_misure() == null) {
                        return " " + object.getNome();
                    }
                    return " " + object.getNome() + " " + object.getNome_tipologia() + " " + object.getUnit_misure() + " " + object.getQuantity();
                }

                @Override
                public FieldData fromString(String string) {
                    return null;
                }
            });

            listener_btn_choice();
            listener_choice();
        }
    }
private void listener_choice() {
    searchableComboBox.valueProperty().addListener(new ChangeListener<FieldData>() {
        @Override
        public void changed(ObservableValue<? extends FieldData> observable, FieldData oldValue, FieldData newValue) {
            if (newValue != null) {
                String query="select price from seller_price\n" +
                        "where farmaco= ? ";
                double price = sellerPriceDao.findCurrentPricebyFarmaco(newValue.getForeign_id(),query);
                price_value.setText(String.valueOf(price));
            }
        }
    });

    /**
     * Listener on btn Calculate
     */
}
    private void listener_btn_choice(){
            btn_calculate_price.setOnAction(event -> {
            int id=search_product.getValue().getForeign_id();
                System.out.println("id"+id);

            double suggestion=promotionsSuggest.get_promotion_discount(id);
            textField_promotion.setText(String.valueOf(suggestion));
            textField_promotion.setId(String.valueOf(id));
        });


    }

    @Override
    protected FieldData get_return_data() {

        int farmaco_id=searchableComboBox.getValue().getForeign_id();
        return FieldData.FieldDataBuilder.getbuilder().
                setDiscount_value((int)Double.parseDouble(textField_promotion.getText())).
                setElapsed_date(Date.valueOf(date_end.getValue())).
                setForeign_id(farmaco_id).build();
    }
    @Override
    protected boolean condition_event(FieldData type) throws Exception {
         boolean value=promotionDao.insert(type);
            s_boolean.setValue(value);
        return value;

    }

}
