package pharma.config;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.sun.jdi.ClassType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import pharma.Model.FieldData;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

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
    public static <S> TableColumn<S,Double> generate_column_double(String header_column, String model_data){
        TableColumn<S, Double> p_id =
                new TableColumn<>(header_column);

        p_id.setCellValueFactory(new PropertyValueFactory<>(model_data));
        return  p_id;

    }
    public static <S> TableColumn<S, Date> generate_column_date(String header_column, String model_data) {
        TableColumn<S, Date> p_id =
                new TableColumn<>(header_column);

        p_id.setCellValueFactory(new PropertyValueFactory<S, Date>(model_data));
        return p_id;
    }
    public static <S> TableColumn<S, Timestamp> generate_column_timestamp(String header_column, String model_data) {
        TableColumn<S, Timestamp> p_id =
                new TableColumn<>(header_column);

        p_id.setCellValueFactory(new PropertyValueFactory<>(model_data));
        return p_id;
    }


    /*public static <S,T> TableColumn<S, Date> generate_column(String header_column, String model_data,Class<T> classType) {
        switch(classType.getName()){
            case "java.lang.String": {

            }




                break;
            default:
                throw new IllegalStateException("Unexpected value: " + classType);
        }


        }
        if(classType==Integer.class){
            TableColumn<S, Integer> p_id =
                    new TableColumn<>(header_column);

            p_id.setCellValueFactory(new PropertyValueFactory<S,Integer>(model_data));

        }

        TableColumn<S, Date> p_id =
                new TableColumn<>(header_column);

        p_id.setCellValueFactory(new PropertyValueFactory<S, Date>(model_data));
        return p_id;
    }

*/

    public static  <T> void  formatting_double(TableColumn<T,Double> t_column){
        t_column.setCellFactory(col->new TextFieldTableCell<>(new StringConverter<Double>() {

            @Override
            public String toString(Double object) {
                if(object!=null){
                   return  String.format("%.2f", object);


                }
                return "";

            }

            @Override
            public Double fromString(String string) {
                return 0.0;
            }
        }));


    }

    public static <T>  void formatting_timestamp(TableColumn<T, Timestamp> t_column){

        t_column.setCellFactory(col->new TextFieldTableCell<>(new StringConverter<Timestamp>() {
            @Override
            public String toString(Timestamp object) {
                if(object!=null) {
                    LocalDateTime localDateTime = object.toLocalDateTime();
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d-MM-yyyy-H:m:s");
                    return "" + dateTimeFormatter.format(localDateTime);
                }
                return "";
            }

            @Override
            public Timestamp fromString(String string) {
                return null;
            }
        }));

    }



}
