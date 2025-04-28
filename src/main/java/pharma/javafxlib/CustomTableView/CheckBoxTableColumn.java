package pharma.javafxlib.CustomTableView;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;
import pharma.Model.FieldData;


public class CheckBoxTableColumn<S> extends TableColumn<S,Void> {
    private ObservableList<S> selected_row;
    public CheckBoxTableColumn() {
        setCellFactory(createRadiobuttonCellFactory());
        selected_row= FXCollections.observableArrayList();
    }

    private Callback<TableColumn<S,Void>, TableCell<S,Void>> createRadiobuttonCellFactory(){

        return  new Callback<TableColumn<S, Void>, TableCell<S, Void>>() {
            @Override
            public TableCell<S, Void> call(TableColumn<S, Void> param) {
                return new TableCell<>() {
                    private final CheckBox checkBox=new CheckBox();
                    {

                     checkBox.setOnAction(event ->{
                               S rowData= getTableView().getItems().get(getIndex());
                               selected_row.add(rowData);

                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setGraphic(null);
                        }else{
                             setGraphic(checkBox);


                        }
                    }
                };
            }
        };


    }

    public ObservableList<S> getSelected_row() {
        if(selected_row.isEmpty()){

            throw new IllegalArgumentException("Argument is empty");
        }
        return selected_row;
    }
}
