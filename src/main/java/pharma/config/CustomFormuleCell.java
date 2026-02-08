package pharma.config;

import javafx.geometry.Insets;
import javafx.scene.AccessibleAttribute;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pharma.Service.Report.UserFormula;

public class CustomFormuleCell extends ListCell<UserFormula> {
    @Override
    protected void updateItem(UserFormula item, boolean empty) {
        super.updateItem(item, empty);
        if(empty|| item==null){
            setText(null);
            setGraphic(null);
        }else{
            VBox vBox=new VBox();
           vBox.setPadding(new Insets(5));
           Label formula_name=new Label(item.getNome());
           formula_name.setFont(Font.font("Palatino", FontWeight.BOLD, 18));
           Label formulaLabel=new Label(item.getFormula());
            formulaLabel.setFont(Font.font("Courier New", 16));
            formulaLabel.setStyle("-fx-text-fill: #555555;");
           vBox.getChildren().addAll(formula_name,formulaLabel);
           setGraphic(vBox);
        }
    }
}
