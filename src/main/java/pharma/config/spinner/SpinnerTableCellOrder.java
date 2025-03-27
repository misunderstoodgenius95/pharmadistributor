package pharma.config.spinner;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.control.SpinnerValueFactory;
import pharma.Model.FieldData;

import java.lang.reflect.Field;
import java.util.Arrays;

public class SpinnerTableCellOrder<K> extends  SpinnerTableCells<FieldData,K> {
    private  String method;
    private SimpleIntegerProperty s_update;
    private  boolean initialize;
    private  SimpleBooleanProperty s_update_result;
    public SpinnerTableCellOrder(Class<K> type,  SimpleIntegerProperty s_update, String method) {
        super(type);
        this.method=method;
     this.s_update=s_update;
     initialize=false;
        listener();


    }

    public SpinnerTableCellOrder(Class<K> type, K min, K max, K step, SimpleBooleanProperty s_update_result, String method) {
        super(type, min, max, step);
        this.method=method;
        this.s_update_result=s_update_result;
        initialize=false;
        listener();
    }


    private void listener() {
        getSpinner().valueProperty().addListener(new ChangeListener<K>() {
            @Override
            public void changed(ObservableValue<? extends K> observable, K oldValue, K newValue) {
                if(!initialize){
                    initialize=true;
                    return;
                }


                if (newValue != null) {

                    Platform.runLater(() -> {


                        FieldData fd = getTableRow().getItem();

                        if (fd != null) {
                            switch (method) {
                                case "setPrice": {

                                    fd.setPrice((Double) newValue);

                                    break;
                                }
                                case "setQuantity": {


                                    fd.setQuantity((Integer) newValue);
                                    break;

                                }
                                case "setPercent": {

                                    fd.setVat_percent((Integer) newValue);
                                    break;
                                }
                            }
                            if(s_update!=null) {
                                s_update.set(fd.getPurchase_order_id());
                            }else{
                                s_update_result.set(true);
                            }







                        }

                    });
                }
            }
        });
    }



}
