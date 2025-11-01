package pharma.Handler;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.config.Status;
import pharma.dao.GenericJDBCDao;
import pharma.dao.SellerPriceDao;
import pharma.formula.PriceSuggestion;

import java.util.List;
import java.util.Optional;

public class SuggestPriceHandler extends DialogHandler<FieldData> {
    private SearchableComboBox<FieldData> search_product;
    private Button btn_calculate_price;
    private TextField textField_price;
    private PriceSuggestion priceSuggestion;
    private SellerPriceDao sellerPriceDao;
    private SimpleBooleanProperty s_boolean;
    public SuggestPriceHandler(String content, SellerPriceDao sellerPriceDao, PriceSuggestion priceSuggestion, SimpleBooleanProperty s_boolean) {
        super(content, List.of(sellerPriceDao));
        this.priceSuggestion=priceSuggestion;
        this.sellerPriceDao = sellerPriceDao;
        this.s_boolean=s_boolean;
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
            SellerPriceDao sellerPriceDao = (SellerPriceDao) optionalgenericJDBCDao.get().stream().filter(value -> value instanceof SellerPriceDao).findFirst().get();
            this.search_product = add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Cerca Farmaco").build());
            this.search_product.setItems(FXCollections.observableArrayList(sellerPriceDao.findNotExistPrice()));
            this.btn_calculate_price=addButton("Calcola valore suggerito");
            this.add_label("Prezzo Suggerito");
            textField_price=this.add_text_field("");
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
        }
    }

    /**
     * Lisener on btn Calculate
     */
    private void listener_btn_choice(){
            btn_calculate_price.setOnAction(event -> {
            int id=search_product.getValue().getId();
                System.out.println("id"+id);
            double suggestion=priceSuggestion.suggest_price(id);
            textField_price.setText(String.valueOf(suggestion));
            textField_price.setId(String.valueOf(id));
        });


    }

    @Override
    protected FieldData get_return_data() {
        return FieldData.FieldDataBuilder.getbuilder().
                setPrice(Double.parseDouble(textField_price.getText())).
               setForeign_id(Integer.parseInt(textField_price.getId())).build();
    }
    @Override
    protected boolean condition_event(FieldData type) throws Exception {
         boolean value= sellerPriceDao.insert(type);
            s_boolean.setValue(value);
        return value;

    }

}
