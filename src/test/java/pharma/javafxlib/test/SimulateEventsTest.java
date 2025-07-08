package pharma.javafxlib.test;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;
import pharma.config.TableUtility;
import pharma.javafxlib.DoubleClick_Menu;

import static pharma.javafxlib.test.SimulateEvents.*;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class SimulateEventsTest {
    VBox vBox;

    @Start
    public void start(Stage stage){
       vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();

    }

    @Test
    public void testPrimaryButton(){
        Platform.runLater(()->{
            Button button=new Button("Clicca");
            vBox.getChildren().add(button);
            button.setOnMouseClicked(event -> {
                if(event.getButton()== MouseButton.PRIMARY){

                    System.out.println("Primary");
                } else if (event.getButton()==MouseButton.SECONDARY) {
                    System.out.println("secondary");

                }

            });
            SimulateEvents.RightMouseClickMouse(button);





        });



    }
    @Test
    public void testRightButton(){
        Platform.runLater(()->{
            Button button=new Button("Clicca");
            vBox.getChildren().add(button);
            button.setOnMouseClicked(event -> {
                if(event.getButton()== MouseButton.PRIMARY){

                    System.out.println("Primary");
                } else if (event.getButton()==MouseButton.SECONDARY) {
                    System.out.println("secondary");

                }

            });
         SimulateEvents.RightMouseClickMouse(button);





        });



    }

    @Test
    public void testRightButtononTable(){
        Platform.runLater(()->{
            Button button=new Button("Clicca");
            vBox.getChildren().add(button);
            button.setOnMouseClicked(event -> {
                if(event.getButton()== MouseButton.PRIMARY){

                    System.out.println("Primary");
                } else if (event.getButton()==MouseButton.SECONDARY) {
                    System.out.println("secondary");

                }

            });
            SimulateEvents.RightMouseClickMouse(button);





        });



    }

    @Test
    public void ValidRightclickOnTableRow(FxRobot robot){

        Platform.runLater(()->{
            TableView<FieldData> tableView=settingTable();
            DoubleClick_Menu<FieldData> doubleClickMenu=new DoubleClick_Menu<>(tableView);
            doubleClickMenu.create_menu_item("Voice");
            vBox.getChildren().add(tableView);
            tableView.setOnMouseClicked(event -> {
                if(event.getButton()== MouseButton.PRIMARY){
                System.out.println("Primary");
            } else if (event.getButton()==MouseButton.SECONDARY) {
                System.out.println("secondary");

            }



            });
            keyPress(tableView, KeyCode.ENTER);


            RightMouseClickMouse(tableView);












        });
        robot.sleep(40000);



    }

    public TableView<FieldData> settingTable(){

                TableView<FieldData> tableView=new TableView<>();

                tableView.getColumns().addAll(
                        TableUtility.generate_column_string("lotto Code","lotto_id"),
                        TableUtility.generate_column_string("Nome","nome"),
                        TableUtility.generate_column_string("Categoria","nome_categoria"),
                        TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                        TableUtility.generate_column_string("Principio Attivo","nome_principio_attivo"),
                        TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),
                        TableUtility.generate_column_int( "Pezzi","quantity"),
                        TableUtility.generate_column_string("Data di produzione","production_date"),
                        TableUtility.generate_column_string("Data di scadenza","elapsed_date"),
                        TableUtility.generate_column_string("Disponibilità","availability"));
                tableView.getItems().addAll(FieldData.FieldDataBuilder.getbuilder().setcode("b9188j").
                                setProduction_date(Date.valueOf(LocalDate.of(2024, 10, 10))).
                                setElapsed_date(Date.valueOf(LocalDate.of(2025, 10, 01))).setNome("Amuchina").build(),
                        FieldData.FieldDataBuilder.getbuilder().setcode("b9188j").setTipologia(1).
                                setProduction_date(Date.valueOf(LocalDate.of(2024, 10, 10))).setNome("Almareex").
                                setElapsed_date(Date.valueOf(LocalDate.of(2025, 10, 01))).build()
                );
                return  tableView;


    }

    @Test
    public void testPrimaryClick(FxRobot robot) {
        Button button=new Button("Clicca");
        CheckEvent checkEvent=new CheckEvent();
        checkEvent.check_mouse_event(button);
 SimulateEvents.PrimaryMouseClickMouse(button);

        Assertions.assertEquals(MouseType.PRIMARY,checkEvent.getLast_type());

    }







}