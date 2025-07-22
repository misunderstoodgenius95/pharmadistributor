package pharma.Handler.Table;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableMap;
import javafx.scene.control.CheckBox;
import org.jetbrains.annotations.TestOnly;
import pharma.Model.FieldData;
import pharma.config.TableUtility;
import pharma.javafxlib.CustomTableView.CheckBoxTableColumn;
import pharma.javafxlib.CustomTableView.RadioButtonTableColumn;

public  class LotTableBase extends ProductTableCustom {
    
    private CheckBoxTableColumn<FieldData> checkboxColumn;
    private RadioButtonTableColumn<FieldData> radioButtonTableColumn;
    private SimpleObjectProperty<FieldData> checkbox_value;
    private SimpleObjectProperty<FieldData> radio_value;
    public LotTableBase(String content) {
        super(content);
        setupBaseColumns();
        checkbox_value=new SimpleObjectProperty<>();
        radio_value=new SimpleObjectProperty<>();
    }
    
    private void setupBaseColumns() {
        getTableViewProductTable().getColumns().addAll(
                TableUtility.generate_column_string("lotto Code", "code"),
                TableUtility.generate_column_int("Farmaco ID", "farmaco_id"),
                TableUtility.generate_column_string("Data di produzione", "production_date"),
                TableUtility.generate_column_string("Data di scadenza", "elapsed_date"),
                TableUtility.generate_column_string("Disponibilità", "availability"),
                TableUtility.generate_column_int("Pezzi", "quantity")
        );
    }
    
    public CheckBoxTableColumn<FieldData> add_check_box_column() {
        checkboxColumn = new CheckBoxTableColumn<FieldData>("Seleziona") {
            @Override
            protected void selectedRow(FieldData data) {
                System.out.println("Base execute");
                checkbox_value.set(data);
                // Call the abstract method that subclasses must implement

            }
        };

        getTableViewProductTable().getColumns().add(checkboxColumn);
        return checkboxColumn;
    }
    public RadioButtonTableColumn<FieldData> add_radio() {
        radioButtonTableColumn = new RadioButtonTableColumn<>() {
            @Override
            protected void onButtonClick(FieldData rowData) {
                radio_value.set(rowData);

            }

        };
        getTableViewProductTable().getColumns().add(radioButtonTableColumn);
        return radioButtonTableColumn;
    }
    // Test Only Used for obtein the instance
    @TestOnly
    public ObservableMap<FieldData, CheckBox> get_checkbox_instance(){
        if(checkboxColumn.getCheckBoxMap()==null){
            throw  new IllegalArgumentException("checkbox it is null");
        }
         return checkboxColumn.getCheckBoxMap();
    }


    public SimpleObjectProperty<FieldData> getCheckBoxValue() {
        return checkbox_value;
    }


    public SimpleObjectProperty<FieldData> checkbox_valueProperty() {
        return checkbox_value;
    }



    public SimpleObjectProperty<FieldData> radio_valueProperty() {
        return radio_value;
    }
}
