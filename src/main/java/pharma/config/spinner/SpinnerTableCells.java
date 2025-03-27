package pharma.config.spinner;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import pharma.Model.FieldData;

public abstract class SpinnerTableCells<T,K> extends TableCell<T, K> {
    private Spinner<K> spinner;
    private  Class<K> type;
    private  boolean update;
    private  K min;
    private K max;
    private  K step;

    public SpinnerTableCells(Class<K> type,K min,K max,K step) {
        spinner = new Spinner<>();
        spinner.setEditable(true);
        this.type = type;
        this.min = min;
        this.step = step;
        this.max = max;
        update = false;
        if (type == Integer.class) {
            spinner.setValueFactory((SpinnerValueFactory<K>) new SpinnerValueFactory.IntegerSpinnerValueFactory((Integer)min, (Integer)max, (Integer)min,(Integer) step));
        } else if (type == Double.class) {
            spinner.setValueFactory((SpinnerValueFactory<K>) new SpinnerValueFactory.DoubleSpinnerValueFactory((Double)1.1, (Double) 2000.79, (Double) 1.1, (Double) 0.1));


        }
    }
    public SpinnerTableCells(Class<K> type) {
        spinner = new Spinner<>();

        spinner.setEditable(true);
        spinner.setPrefWidth(90.0);
        this.type=type;
        update=true;

    }



    public Spinner<K> getSpinner() {
       return  spinner;
    }




    @Override
    protected void updateItem(K item, boolean empty) {
        super.updateItem(item, empty);
        if(empty){
            setGraphic(null);
        }else{

            if(update) {
                if (type == Integer.class) {
                    int maxValue = (int) item;
                    spinner.setValueFactory((SpinnerValueFactory<K>) new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxValue, maxValue, 1));
                } else if (type == Double.class) {
                    double maxValue = (double) item;
                    spinner.setValueFactory((SpinnerValueFactory<K>) new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, maxValue, maxValue, 0.1));
                }
            }



            setGraphic(spinner);
        }
    }



}
