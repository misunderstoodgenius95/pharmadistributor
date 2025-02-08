package pharma.config;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableUtility {
    public static<S> TableColumn<S,String> generate_column_string(String header_column, String model_data){
        TableColumn<S, String> p_id =
                new TableColumn<>(header_column);

        p_id.setCellValueFactory(new PropertyValueFactory<S,String>(model_data));
        return  p_id;
    }


    public static <S> TableColumn<S,Integer> generate_column_int(String header_column, String model_data){
        TableColumn<S, Integer> p_id =
                new TableColumn<>(header_column);

        p_id.setCellValueFactory(new PropertyValueFactory<S,Integer>(model_data));
        return  p_id;
    }





}
