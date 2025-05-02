package pharma.javafxlib.CustomTableView;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.effect.SepiaTone;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.javafxlib.test.SimulateEvents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class CheckBoxTableColumnTest {
    private HashSet<FieldData> hashSet;
    private  VBox vBox;
    private  TableView<FieldData> tableView;
    private  CheckBoxTableColumn<FieldData> checkBoxTableColumn;
    @Start
    public void  start(Stage stage){
        vBox=new VBox();
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        vBox.setPrefHeight(1000);
        vBox.setPrefWidth(1000);
        stage.show();
        tableView = new TableView<>();
        hashSet = new HashSet<>();
         checkBoxTableColumn = new CheckBoxTableColumn<>("Seleziona") {
            @Override
            protected void selectedRow(FieldData data) {
                System.out.println(data);
                hashSet.add(data);
            }
        };
        tableView.getColumns().addAll(TableUtility.generate_column_string("Nome", "nome"),
                TableUtility.generate_column_string("Sigla", "sigla"), checkBoxTableColumn


        );

        tableView.getItems().addAll(FieldData.FieldDataBuilder.getbuilder().setNome("a").setSigla("a").build(),
                FieldData.FieldDataBuilder.getbuilder().setNome("b").setSigla("b").build());

        vBox.getChildren().addAll(tableView);


    }
    @BeforeEach
    void setUp(){



    }
    @Test
     public void ValidObteinChoiceboxRow(FxRobot robot){
        Platform.runLater(()->{
            Set<FieldData> set =checkBoxTableColumn.getCheckBoxMap().keySet();
            for(FieldData fieldData:set){
                CheckBox checkBox=checkBoxTableColumn.getCheckBoxMap().get(fieldData);
                checkBox.setSelected(true);
            }

              // SimulateEvents.setCheckBox(checkBoxTableColumn.getCheckBoxMap(),tableView.getItems().getFirst());





        });
        robot.sleep(400000);


    }




}