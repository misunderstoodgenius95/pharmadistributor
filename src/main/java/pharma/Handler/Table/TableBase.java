package pharma.Handler.Table;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableMap;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.TestOnly;

import pharma.javafxlib.CustomTableView.CheckBoxTableColumn;
import pharma.javafxlib.CustomTableView.RadioButtonTableColumn;
import pharma.javafxlib.Dialog.CustomDialog;

public abstract   class TableBase<T> extends CustomDialog<T> {
    
    private CheckBoxTableColumn<T> checkboxColumn;
    private RadioButtonTableColumn<T> radioButtonTableColumn;
    private SimpleObjectProperty<T> checkbox_value;
    private SimpleObjectProperty<T> radio_value;
    private TableView<T> tableView;
    public TableBase(String content) {
        super(content);
        checkbox_value=new SimpleObjectProperty<>();
        radio_value=new SimpleObjectProperty<>();
        tableView=add_table();
        getDialogPane().setPrefHeight(500);
        getDialogPane().setPrefWidth(600);
        setupBaseColumns(tableView);
    }

    protected abstract void setupBaseColumns(TableView<T> tableView);
    
    public CheckBoxTableColumn<T> add_check_box_column() {
        checkboxColumn = new CheckBoxTableColumn<T>("Seleziona") {
            @Override
            protected void selectedRow(T data) {
                System.out.println("Base execute");
                checkbox_value.set(data);
                // Call the abstract method that subclasses must implement
            }
        };

        tableView.getColumns().add(checkboxColumn);
        return checkboxColumn;
    }
    public RadioButtonTableColumn<T> add_radio() {
        radioButtonTableColumn = new RadioButtonTableColumn<T>() {
            @Override
            protected void onButtonClick(T rowData) {
                radio_value.set(rowData);
            }

        };
        tableView.getColumns().add(radioButtonTableColumn);
        return radioButtonTableColumn;
    }
    // Test Only Used for obtein the instance
    @TestOnly
    public ObservableMap<T, CheckBox> get_checkbox_instance(){
        if(checkboxColumn.getCheckBoxMap()==null){
            throw  new IllegalArgumentException("checkbox it is null");
        }
         return checkboxColumn.getCheckBoxMap();
    }

    /**
     * Getting ObservableMap<T,RadioButton> Radio Button Value
     * @return
     */
    public  ObservableMap<T, RadioButton> get_radioButton(){
        if(radioButtonTableColumn.getRadioMap()==null){
            throw new IllegalArgumentException("RadioMap is null!");
        }
         return radioButtonTableColumn.getRadioMap();
    }


    public SimpleObjectProperty<T> getCheckBoxValue() {
        return checkbox_value;
    }



    public TableView<T> getTableView() {
        return tableView;
    }

    /**
     * Getting Radio Value
     * @return
     */
    public SimpleObjectProperty<T> getRadio_value() {
        return radio_value;
    }
}
