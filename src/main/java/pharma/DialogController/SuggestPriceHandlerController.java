package pharma.DialogController;

import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import pharma.DialogController.Table.HandlerController;
import pharma.DialogController.Table.ProductTableCustom;
import pharma.Model.FieldData;
import pharma.dao.DetailDao;
import pharma.dao.FarmacoDao;

import java.util.List;

public class SuggestPriceHandlerController extends HandlerController {
    private TextField textField_single;
    private SearchableComboBox<FieldData> search_category;
    private ProductTableCustom productTableCustom;
    private FarmacoDao farmacoDao;
    public SuggestPriceHandlerController(TextField textFieldSingle, SearchableComboBox<FieldData> searchCategory,FarmacoDao farmacoDao,DetailDao detailDao) {
        textField_single = textFieldSingle;
        search_category = searchCategory;
        productTableCustom=new ProductTableCustom("Scegli Prodotto");
        productTableCustom.add_radio();
        this.farmacoDao=farmacoDao;

        List<FieldData> list=detailDao.findAll();
        System.out.println(list.size());
        searchCategory.setItems(FXCollections.observableArrayList(list));




    }



    @Override
    public boolean btn_validate() {
        return validate_controls(textField_single) &&
                validate_controls(search_category);

    }



    public void execute_table(){
        productTableCustom.getTableView().getItems().setAll(farmacoDao.findByName(textField_single.getText()));
        productTableCustom.show();

    }


}
