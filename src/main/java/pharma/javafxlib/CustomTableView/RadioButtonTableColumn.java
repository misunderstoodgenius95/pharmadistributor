package pharma.javafxlib.CustomTableView;


import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RadioButtonTableColumn<S> extends TableColumn<S,Void> {
    private static final Logger log = LoggerFactory.getLogger(RadioButtonTableColumn.class);
    private final ObservableMap<S,RadioButton> radioMap;
    private final SimpleObjectProperty<S> value_radio_property_choice;
    //  ==Constructor===
    public RadioButtonTableColumn() {
        value_radio_property_choice = new SimpleObjectProperty<>();
        this.radioMap = FXCollections.observableHashMap();
        setCellFactory(createRadiobuttonCellFactory());
    }

    private Callback<TableColumn<S,Void>, TableCell<S,Void>> createRadiobuttonCellFactory(){
       ToggleGroup toggleGroup = new ToggleGroup();
        return  new Callback<TableColumn<S, Void>, TableCell<S, Void>>() {
            @Override
            public TableCell<S, Void> call(TableColumn<S, Void> param) {
                return new TableCell<>() {
                    private RadioButton radioButton = new RadioButton();
                    {
                        radioButton.setToggleGroup(toggleGroup);
                        radioButton.setOnAction(event ->{

                               S rowData= getTableView().getItems().get(getIndex());
                                onButtonClick(rowData);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setGraphic(null);
                        }else{
                            radioButton.setSelected(getTableView().getSelectionModel().isSelected(getIndex()));

                             setGraphic(radioButton);
                             S s=getTableView().getItems().get(getIndex());
                             radioMap.put(s,radioButton);
                             value_radio_property_choice.set(s);


                        }
                    }
                };
            }
        };


    }

    /**
     * it can be overridden
     * @param rowData
     */
    protected void onButtonClick(S rowData) {
        // Default implementation (can be overridden)
        System.out.println("Button clicked for: " + rowData);
    }

    public ObservableMap<S, RadioButton> getRadioMap() {
        return radioMap;
    }

    public Object getValue_radio_property_choice() {
        return value_radio_property_choice.get();
    }

    public SimpleObjectProperty<S> value_radio_property_choiceProperty() {
        return value_radio_property_choice;
    }
}
