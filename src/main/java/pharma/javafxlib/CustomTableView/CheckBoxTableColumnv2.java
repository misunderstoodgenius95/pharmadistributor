package pharma.javafxlib.CustomTableView;


import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import pharma.Model.FieldData;

import java.util.function.Consumer;
import java.util.function.Function;


public abstract class CheckBoxTableColumnv2<S> extends TableColumn<S,Void> {

    private final ObservableMap<S, CheckBox> checkBoxMap;
    public CheckBoxTableColumnv2(String columnn_header) {
        setText(columnn_header);
        setCellFactory(createCheckBoxTableFactory());
        checkBoxMap= FXCollections.observableHashMap();

    }

    private Callback<TableColumn<S,Void>, TableCell<S,Void>> createCheckBoxTableFactory(){

        return  new Callback<TableColumn<S, Void>, TableCell<S, Void>>() {
            @Override
            public TableCell<S, Void> call(TableColumn<S, Void> param) {
                return new TableCell<>() {
                    private final CheckBox checkBox=new CheckBox();
                    {

                     checkBox.setOnAction(event ->{
                               S rowData= getTableView().getItems().get(getIndex());
                               selectedRow(rowData);



                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setGraphic(null);
                        }else{

                             setGraphic(checkBox);
                            S s= getTableView().getItems().get(getIndex());
                             checkBoxMap.put(s,checkBox);



                        }
                    }
                };
            }
        };


    }
    protected void  selectedRow(S data){
        System.out.println("Button clicked for: " + data);
    }

    public ObservableMap<S, CheckBox> getCheckBoxMap() {
        return checkBoxMap;
    }

    protected abstract void selectedRow(FieldData data);
}
