package pharma.config.spinner;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import pharma.Model.FieldData;
import pharma.javafxlib.CustomTableView.SpinnerTableCells;

import java.net.Inet4Address;

public class SpinnerGeneric extends SpinnerTableCells<FieldData,Integer> {


    public SpinnerGeneric() {
        super(Integer.class);
        listener_spinner();

    }
    private void listener_spinner(){
        getSpinner().valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue);
                Platform.runLater(() -> {
                    FieldData fd = getTableRow().getItem();
                    if (fd != null) {
                        if(fd.getQuantity()!=0) {
                            fd.s_quantityProperty().setValue(fd.getQuantity() - newValue);
                        }else{
                            fd.s_quantityProperty().setValue(newValue);
                        }

                        System.out.println(fd.getQuantity()-newValue);
                    }else{
                        System.out.println("null");
                    }


                });

            }
        });




    }








}
