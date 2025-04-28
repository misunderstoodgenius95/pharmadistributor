package pharma.config.snippet;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import pharma.Model.FieldData;
import pharma.javafxlib.Controls.TextFieldComboBox;

import java.sql.Date;
import java.util.List;

public class Event {
    private TextFieldComboBox<FieldData> combo_pharma;
    private  ObservableList<FieldData> list_populate;
    private DatePicker calendar_order;
    private  TextFieldComboBox<FieldData>combo_order_provider_id;
    private MenuItem menuItem;
    private void setting_listener_combo() {

        ChangeListener<Object> changeListener = (obs, oldVal, newVal) -> {
            if (combo_pharma.getChoiceBox().getValue() != null && calendar_order.getValue() != null) {
                executeEventBoth(combo_pharma.getChoiceBox().getValue().toString(), calendar_order.getValue().toString());
            }
        };

        combo_pharma.getChoiceBox().getSelectionModel().selectedItemProperty().addListener(changeListener);
        calendar_order.valueProperty().addListener(changeListener);

    }


    public void executeEventBoth(String combo_pharma, String date_order) {
        List<FieldData> fieldDatalist = list_populate.stream().filter(fd -> fd.getNome_casa_farmaceutica().equals(combo_pharma) &&
                fd.getProduction_date().equals(Date.valueOf(date_order))).toList();

        combo_order_provider_id.getChoiceBox().setItems(FXCollections.observableArrayList(fieldDatalist));
        if (menuItem.isDisable()) {
            menuItem.setDisable(false);
        }


    }

}
