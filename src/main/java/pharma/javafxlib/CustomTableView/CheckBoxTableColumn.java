package pharma.javafxlib.CustomTableView;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.jetbrains.annotations.TestOnly;


import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that can be choice multiple field
 * @param <S>
 */
public abstract class CheckBoxTableColumn<S> extends TableColumn<S,Void> {
// using a list for isnert checkbox
    private final ObservableMap<S, CheckBox> checkBoxMap;
    public CheckBoxTableColumn(String columnn_header) {
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
    @TestOnly
    public ObservableMap<S, CheckBox> getCheckBoxMap() {
        return checkBoxMap;
    }


}
