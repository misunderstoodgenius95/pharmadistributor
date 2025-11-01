package pharma.Handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import pharma.Handler.Table.HandlerController;
import pharma.config.Utility;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SellerInvoiceHandlerController extends HandlerController {
    private final ToggleGroup toggleGroup;
    private final TextField tfMulti;
    private final DatePicker startRange;
    private final DatePicker endRange;
    private Set<Control> controlList;
    public SellerInvoiceHandlerController(ToggleGroup toggleGroup, TextField tf_multi, DatePicker start_range,DatePicker end_range) {
        this.toggleGroup = toggleGroup;
        this.tfMulti = tf_multi;
        this.startRange = start_range;
        this.endRange = end_range;
        controlList=new LinkedHashSet<>();
        listener_toggle();
    }
    private void listener_toggle(){
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(newValue instanceof RadioButton radioButton){
                    if(radioButton.getId().equals("all")){
                      disabled_all();
                    }
                    if(radioButton.getId().equals("header_range")){
                           renabled_all();
                        }
                    if(radioButton.getId().equals("only_invoice")){
                        only_multi_enabled();
                    }


                    }

                }

        });


}

    private void disabled_all() {
      tfMulti.setDisable(true);
      endRange.setDisable(true);
        startRange.setDisable(true);

    }
    private void renabled_all(){
        tfMulti.setDisable(false);
        endRange.setDisable(false);
        startRange.setDisable(false);
    }
    private void only_multi_enabled(){
        tfMulti.setDisable(false);
        endRange.setDisable(true);
        startRange.setDisable(true);

    }

    @Override
    public boolean btn_validate(){
        return validate_controls(tfMulti) &&
                validate_controls(startRange) &&
                validate_controls(endRange);
    }







}

