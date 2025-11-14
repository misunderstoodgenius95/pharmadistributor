package pharma.javafxlib.CustomTableView;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SpinnerTableCells<T,K> extends TableCell<T, K> {
    private static final Logger log = LoggerFactory.getLogger(SpinnerTableCells.class);
    private Spinner<K> spinner;
    private Class<K> type;
    private boolean update;
    private K min;
    private K max;
    private K step;

    public SpinnerTableCells(Class<K> type, K min, K max, K step) {
        spinner = new Spinner<>();
        spinner.setEditable(true);
        spinner.getStyleClass().add(".spinner");
        this.type = type;
        this.min = min;
        this.step = step;
        this.max = max;
        update = false;

        if (type == Integer.class) {
            spinner.setValueFactory((SpinnerValueFactory<K>) new SpinnerValueFactory.IntegerSpinnerValueFactory((Integer)min, (Integer)max, (Integer)min, (Integer)step));
        } else if (type == Double.class) {
            spinner.setValueFactory((SpinnerValueFactory<K>) new SpinnerValueFactory.DoubleSpinnerValueFactory((Double)min, (Double)max, (Double)min, (Double)step));
        }
    }

    public SpinnerTableCells(Class<K> type) {
        spinner = new Spinner<>();
        spinner.setEditable(true);
        spinner.setPrefWidth(90.0);
        this.type = type;
        update = true;
    }

    public Spinner<K> getSpinner() {
        return spinner;
    }

    @Override
    protected void updateItem(K item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            if (update) {
                if (type == Integer.class) {
                    int maxValue = (int) item;
                    log.info("Max value"+maxValue);
                    if(maxValue==0){
                        spinner.setValueFactory((SpinnerValueFactory<K>) new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0, 1));
                    }else {
                        spinner.setValueFactory((SpinnerValueFactory<K>) new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxValue, maxValue, 1));
                    }
                } else if (type == Double.class) {
                    double maxValue = (double) item;
                    spinner.setValueFactory((SpinnerValueFactory<K>) new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1000.0, maxValue, 0.1));
                }
            }
            setGraphic(spinner);
        }
    }
}