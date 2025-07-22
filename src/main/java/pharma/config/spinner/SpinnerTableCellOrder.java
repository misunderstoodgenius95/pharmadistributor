package pharma.config.spinner;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import pharma.Model.FieldData;
import pharma.javafxlib.CustomTableView.SpinnerTableCells;

public class SpinnerTableCellOrder<K> extends SpinnerTableCells<FieldData,K> {
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
        K currentValue = getSpinner().getValue();
        if (currentValue instanceof Integer) {
            getSpinner().getValueFactory().setValue((K) (Integer) ((Integer) currentValue + 1));
        } else if (currentValue instanceof Double) {
            getSpinner().getValueFactory().setValue((K) (Double) ((Double) currentValue + 1.0));
        }



    }

    public SpinnerTableCellOrder(Class<K> type, K min, K max, K step, SimpleBooleanProperty s_update_result, String method) {
        super(type, min, max, step);
        this.method=method;
        this.s_update_result=s_update_result;
        initialize=false;

        listener();
        K currentValue = getSpinner().getValue();
        if (currentValue instanceof Integer) {
            getSpinner().getValueFactory().setValue((K) (Integer) ((Integer) currentValue + 1));
        } else if (currentValue instanceof Double) {
            getSpinner().getValueFactory().setValue((K) (Double) ((Double) currentValue + 1.0));
        }

    }


    private void listener() {
        getSpinner().valueProperty().addListener(new ChangeListener<K>() {
            @Override
            public void changed(ObservableValue<? extends K> observable, K oldValue, K newValue) {


                System.out.println("change");
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
                                s_update.set(fd.getOrder_id());
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
