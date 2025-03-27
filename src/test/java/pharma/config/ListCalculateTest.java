package pharma.config;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class ListCalculateTest {
    private VBox vBox;
    private TableView<FieldData> tableView;
    @Start
public void start(Stage stage){
    vBox = new VBox();
    Scene scene = new Scene(vBox);
    stage.setScene(scene);

    tableView=new TableView<>();

    TableColumn<FieldData,Integer> qty=TableUtility.generate_column_int("Quantità","quantity");
    TableColumn<FieldData,Integer> vat=TableUtility.generate_column_int("Iva","vat_percent");
    TableColumn<FieldData,Double> price=TableUtility.generate_column_double("Prezzo","price");
    tableView.getColumns().addAll(qty,vat,price);

    vBox.getChildren().add(tableView);

    stage.show();

}
    @Test
    void amount_subtotal(FxRobot robot) {
        Platform.runLater(()->{
            tableView.getItems().addAll(FieldData.FieldDataBuilder.getbuilder().setQuantity(2).setPrice(2.50).setVat_percent(10).build(),
                    FieldData.FieldDataBuilder.getbuilder().setQuantity(2).setPrice(2.50).setVat_percent(10).build());
            double result=ListCalculate.amount_subtotal(tableView.getItems());
            Assertions.assertEquals(10,result);
            tableView.getItems().clear();
            tableView.getItems().addAll(
                    FieldData.FieldDataBuilder.getbuilder().setQuantity(3).setPrice(5.50).setVat_percent(22).build(),
                    FieldData.FieldDataBuilder.getbuilder().setQuantity(2).setPrice(2.50).setVat_percent(22).build());
            result=ListCalculate.amount_subtotal(tableView.getItems());
            Assertions.assertEquals(21.5,result);



        });
        robot.sleep(1000);


    }

    @Test
    void amount_vat() {
        Platform.runLater(()->{
            tableView.getItems().addAll(FieldData.FieldDataBuilder.getbuilder().setQuantity(4).setPrice(2.50).setVat_percent(10).build(),
                    FieldData.FieldDataBuilder.getbuilder().setQuantity(2).setPrice(2.50).setVat_percent(10).build());
            double subtotal_result=ListCalculate.amount_subtotal(tableView.getItems());
            double amount_vat=ListCalculate.amount_vat(tableView.getItems());
            Assertions.assertEquals(15.0,subtotal_result);
            Assertions.assertEquals(1.5,amount_vat);
            tableView.getItems().clear();
            tableView.getItems().addAll(
                    FieldData.FieldDataBuilder.getbuilder().setQuantity(3).setPrice(5.50).setVat_percent(22).build(),
                    FieldData.FieldDataBuilder.getbuilder().setQuantity(2).setPrice(2.50).setVat_percent(22).build());
            subtotal_result=ListCalculate.amount_subtotal(tableView.getItems());
          amount_vat=ListCalculate.amount_vat(tableView.getItems());
            Assertions.assertEquals(21.5,subtotal_result);
            Assertions.assertEquals(4.73,amount_vat);



        });



    }

    @Test
    void total() {
        double result=ListCalculate.total(15.0,5.0);
        Assertions.assertEquals(20.0,result);
    }
}