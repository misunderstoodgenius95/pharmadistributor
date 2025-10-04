package pharma.Controller.subpanel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.dao.DetailDao;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;

public class SuggestPurchase implements Initializable {

    public Button btn_send_id;
    @FXML
    AnchorPane anchor_id;
    @FXML
    public TextField text_single;
    @FXML
    private ToggleGroup productToggleGroup;
    private DetailDao detailDao;

    public SuggestPurchase(DetailDao detailDao) {
        this.detailDao = detailDao;
    }
    public SuggestPurchase() {
        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.detailDao = new DetailDao(Database.getInstance(properties));
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SearchableComboBox<FieldData> searchableComboBox=new SearchableComboBox<>();
        anchor_id.getChildren().add(searchableComboBox);
        AnchorPane.setLeftAnchor(searchableComboBox,150.11);
        AnchorPane.setTopAnchor(searchableComboBox,220.1);
        searchableComboBox.setVisible(false);
        searchableComboBox.getItems().addAll(detailDao.findAll());
        searchableComboBox.setConverter(new StringConverter<FieldData>() {
            @Override
            public String toString(FieldData object) {
                if(object!=null){
                    return " "+object.getNome();
                }
                return "";
            }


            @Override
            public FieldData fromString(String string) {
                return null;
            }
        });
        productToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(newValue instanceof RadioButton radioButton){
                    if(radioButton.getUserData().equals("single")){
                        searchableComboBox.setVisible(false);
                        text_single.setVisible(true);

                    }else{
                        searchableComboBox.setVisible(true);
                        text_single.setVisible(false);
                    }
                    System.out.println(radioButton.getUserData());
                }
            }
        });




    }

    public void send_action_btn(ActionEvent event) {



    }
}
