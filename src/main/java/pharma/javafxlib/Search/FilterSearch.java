package pharma.javafxlib.Search;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pharma.Model.FieldData;


import java.util.Map;
import java.util.logging.Filter;
import java.util.stream.Stream;

public class FilterSearch {
    private  TableView<FieldData> tableView;
    private FilteredList<FieldData> fieldDataFilteredList;

    public FilterSearch(TableView<FieldData>  tableView,TextField textField) {
        this.tableView=tableView;
        fieldDataFilteredList = new FilteredList<>(tableView.getItems(), p -> true);
        tableView.setItems(fieldDataFilteredList);

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fieldDataFilteredList.setPredicate(fieldData -> {


                    System.out.println("Filtering...");
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                     return condition(newValue,fieldData);


                });
            }
        });
        }


        protected boolean condition(String newValue,FieldData fieldData){
                return false;

        }


        public  static String choice_value(String choice, Map.Entry<String,String> ...entities){
            return Stream.of(entities).
                    filter(entry->entry.getKey().equalsIgnoreCase(choice)).
                    map(Map.Entry::getValue).
                    findFirst().
                    orElse(null);
        }




}
