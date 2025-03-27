package pharma.config;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import org.controlsfx.control.SearchableComboBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import pharma.Model.FieldData;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class Utility {
public  static  final String Principio_attivo="Principio_Attivo";
public static  final  String Tipologia="Tipologia";
public static final  String Categoria="Categoria";
public static final String Misura="Misura";


    public static void create_alert(Alert.AlertType alert_type, String title_header, String body) {

        Alert alert = new Alert(alert_type);
        alert.setTitle(title_header);
        alert.setHeaderText(body);
        alert.getDialogPane().setId("alert");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();

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
                return fieldData.getAnagrafica_cliente().toLowerCase().contains(newValue.toLowerCase());

            });
        });
    }

    public static void set_fieldText(String mode, TextField detailField1, TextField detailField2) {

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
        icon.setIconSize(20);
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






}
