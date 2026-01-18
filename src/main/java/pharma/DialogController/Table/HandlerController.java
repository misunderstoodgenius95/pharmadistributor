package pharma.DialogController.Table;

import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

public abstract class HandlerController {


    public abstract boolean btn_validate();

    public boolean validate_controls(Control control){
        if(!control.isDisable()){
            switch (control) {
                case DatePicker datePicker -> {
                    return datePicker.getValue() != null;
                }
                case TextField textField -> {
                    return !textField.getText().isEmpty();
                }
                case SearchableComboBox searchableComboBox -> {
                    return searchableComboBox.getValue() != null;
                }
                default -> {
                    throw  new IllegalArgumentException("Controls not present!");
                }
            }
        }else{
            return true;
        }




    }

}
