package pharma.config;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import org.controlsfx.control.SearchableComboBox;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import pharma.Model.FieldData;

import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Utility {
public  static  final String Principio_attivo="Principio_Attivo";
public static  final  String Tipologia="Tipologia";
public static final  String Categoria="Categoria";
public static final String Misura="Misura";
    public static void  create_alert(Alert.AlertType alert_type, String title_header, String body) {

        Alert alert = new Alert(alert_type);
        alert.setTitle(title_header);  
        alert.setHeaderText(body);
        alert.getDialogPane().setId("alert");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();


    }
    public static Alert create_alert_confirm(Alert.AlertType alert_type, String title_header, String body) {

        Alert alert = new Alert(alert_type);
        alert.setTitle(title_header);
        alert.setHeaderText(body);
        alert.getDialogPane().setId("alert");
        alert.initModality(Modality.APPLICATION_MODAL);;
        return alert;

    }
    public  static  ButtonType accept() {
    return new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
    }
    public static  ButtonType refuse(){

   return new ButtonType("Refuse", ButtonBar.ButtonData.CANCEL_CLOSE);
    }

    public  static void network_status(int status){
        switch (status){
            case 201 -> {
                create_alert(Alert.AlertType.CONFIRMATION,"Alert Status","Creato con successo");
            }
            case 422->{
                create_alert(Alert.AlertType.WARNING,"Alert Status","Elemento già presente!");

            }
            case  400->{
                create_alert(Alert.AlertType.WARNING,"Alert Status","Bad request!");
            }
            default -> create_alert(Alert.AlertType.WARNING,"",status+" ");

        }

    }

    public static double getRequiredDoubleFromMap(Map<String, String> map, String key) {
        String value = map.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing required config key: " + key);
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format for key '" + key + "': " + value, e);
        }
    }


    public static void create_btn(Button button, String path) {

        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        button.setGraphic(imageView);

        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    public static void search_item(TableView<FieldData> table_id, TextField search_id) {

        FilteredList<FieldData> filteredData = new FilteredList<>(table_id.getItems(), p -> true);
        table_id.setItems(filteredData);
        search_id.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(fieldData -> {
                System.out.println("Filtering...");
/*
The reason for returning true when the filter text is null or empty is that the predicate in a FilteredList
 determines whether a specific item in the list should be displayed.
 If the filter text is empty, it means the user hasn't applied any filtering yet,
 so all items should remain visible in the TableView.
 */
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                /*
    setPredicate() determines which items should remain visible in the TableView.
    If the predicate returns true, the item stays in the filtered list.
    If the predicate returns false, the item disappears from the TableView
    */
                return fieldData.getNome().toLowerCase().contains(newValue.toLowerCase());

               // return fieldData.getNome_casa_farmaceutica().toLowerCase().contains(newValue.toLowerCase());

            });
        });
    }

    public static void set_fieldText(String mode, TextField detailField1, TextField detailField2,TextField detailField_3) {

        if (mode.equalsIgnoreCase(Utility.Misura)) {
            detailField1.setPromptText("Misura");
            detailField2.setVisible(true);
            detailField2.setPromptText("Quantità");
        } else {

            detailField1.setPromptText("Nome");
            detailField2.setVisible(false);
        }
    }

    public static void add_iconButton(Button button, Ikon font){
        FontIcon icon = new FontIcon(font);
        icon.setIconSize(22);
        button.setGraphic(icon);
        button.setText("");
    }
    public static   <T,K>  List<T>  extract_value_from_list(List<K> list,  Class<T> class_type){

         return list.stream().filter(class_type::isInstance).map(class_type::cast).toList();


    }
   public  static  void resetLabelText(Label... labels) {
        for (Label label : labels) {
            label.setText(label.getText().split(":")[0]);
        }
    }

    public  static List<FieldData> extract_province(String json_string){

        JSONArray jsonArray=new JSONArray(json_string);

       return  IntStream.range(0,jsonArray.length()).mapToObj(jsonArray::getJSONObject).
                map(jsonObject -> FieldData.FieldDataBuilder.getbuilder().
                        setProvince(jsonObject.getString("nome")).setSigla(jsonObject.getString("sigla")).build()).toList();


    }











}
