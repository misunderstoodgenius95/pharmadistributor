package pharma.config;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import pharma.Model.FieldData;

import java.util.List;

public class Utility {

    public static void create_alert(Alert.AlertType alert_type, String title_header,String body){

        Alert alert = new Alert(alert_type);
        alert.setTitle(title_header);
        alert.setHeaderText(body);
        alert.getDialogPane().setId("alert");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();

    }
    public static  void create_btn(Button button, String path){
        Image image=new Image(path);
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        button.setGraphic(imageView);

        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
    public static void search_item(TableView<FieldData> table_id,TextField search_id){

        FilteredList<FieldData> filteredData = new FilteredList<>(table_id.getItems(), p -> true);
        table_id.setItems(filteredData);
        search_id.textProperty().addListener((observable,oldValue,newValue )-> {
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


}
