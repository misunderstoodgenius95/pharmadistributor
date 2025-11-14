
package pharma.Controller.subpanel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.SearchableComboBox;
import org.jetbrains.annotations.TestOnly;
import pharma.Handler.SuggestPriceHandlerController;
import pharma.Model.FieldData;
import pharma.config.Utility;
import pharma.dao.DetailDao;
import pharma.dao.FarmacoDao;
import pharma.javafxlib.Controls.TextFieldComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SuggestPrice implements Initializable {
    @FXML
    public TextField single_product;
    @FXML
    public AnchorPane anchor_id;
    @FXML
    public ToggleGroup choice;
    @FXML
    private RadioButton single;
    @FXML
    private RadioButton category;
    @FXML Button send_btn_id;
    private SearchableComboBox<FieldData>searchableComboBox;
    private SuggestPriceHandlerController s_handler;
    private FarmacoDao farmacoDao;
    private DetailDao detailDao;
    @TestOnly
    public SuggestPrice(FarmacoDao farmacoDao,DetailDao detailDao) {
        this.farmacoDao=farmacoDao;
        this.detailDao=detailDao;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        searchableComboBox=new SearchableComboBox<>();
        anchor_id.getChildren().add(searchableComboBox);
        AnchorPane.setTopAnchor(searchableComboBox,180.0);
        AnchorPane.setLeftAnchor(searchableComboBox,180.0);
        choice.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof RadioButton radioButton) {
                if (radioButton.getId().equals("category")) {
                    single_product.setDisable(true);
                    searchableComboBox.setDisable(false);
                } else {
                    single_product.setDisable(false);
                    searchableComboBox.setDisable(true);
                }

            }
        });
        s_handler=new SuggestPriceHandlerController(single_product,searchableComboBox,farmacoDao,detailDao);


    }

  /*  public boolean validate_controls(Control control){
        if(!control.isDisable()){
            if(control instanceof SearchableComboBox searchableComboBox) {
                return searchableComboBox.getValue() != null;
            }
            else if(control instanceof  TextField textField){
                return !textField.getText().isEmpty();
            }
        }else{
            return true;
        }
        return false;



    }
/*
    public boolean btn_validate(){
        return validate_controls(single_product) &&
                validate_controls(searchableComboBox);

    }

*/




    @FXML
    public void on_action_send(ActionEvent event) {
        if(s_handler.btn_validate()){
            if(choice.getSelectedToggle().equals(single)){
                s_handler.execute_table();
            }


        }else{
            Utility.create_alert(Alert.AlertType.WARNING, "Attenzione!", " Riempire tutti campi!");
        }


    }
}