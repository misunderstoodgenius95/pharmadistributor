package pharma.javafxlib.CustomTableView;


import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
import javafx.util.Callback;


public class RadioButtonTableColumn<S> extends TableColumn<S,Void> {

    public RadioButtonTableColumn() {
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


}
