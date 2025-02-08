package pharma.oldest;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import pharma.Controller.subpanel.Pharma;
import pharma.Model.FieldData;
import pharma.config.*;
import pharma.dao.DetailDao;
import pharma.dao.PharmaDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


class CustomDialogTest extends ApplicationTest {
    CustomDialog<FieldData> customDialog;
    private TextField anagrafia;
    private TextField vat;
    private TextField sigla;
    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        customDialog = new CustomDialog<>("Pharma");
        anagrafia=customDialog.add_text_field("Inserisci Anagrafia Utente");
        vat=customDialog.add_text_field_with_validation("Inserisci Partita Iva", CustomDialog.Validation.Vat);
        sigla=customDialog.add_text_field("Sigla");

    }




    @Test
    public void  INValid_Test() throws InterruptedException {
        Platform.runLater(()->{
            customDialog.show();
            ButtonType button_type_ok = customDialog.getDialogPane().getButtonTypes().get(0);
            Button button_ok = (Button) customDialog.getDialogPane().lookupButton(button_type_ok);


            SimulateEvents.writeOn(vat,"IT12555555");
            SimulateEvents.writeOn(sigla,"af");
            SimulateEvents.writeOn(anagrafia,"AF");
            SimulateEvents.clickOn(button_ok);



            Assertions.assertDoesNotThrow(()->lookup("#alert").queryAs(DialogPane.class));
// Non si verifica l'exception perchè l'alert viene trovato, perchè esiste.
        });
        sleep(40000);
    }

    @Test
    public void  Valid_Test() throws InterruptedException {

        Platform.runLater(()->{

            ButtonType button_type_ok = customDialog.getDialogPane().getButtonTypes().get(0);
            Button button_ok = (Button) customDialog.getDialogPane().lookupButton(button_type_ok);


            SimulateEvents.writeOn(vat,"IT12345555555");
            SimulateEvents.writeOn(sigla,"af");
            SimulateEvents.writeOn(anagrafia,"AF");
            SimulateEvents.clickOn(button_ok);
            Assertions.assertThrows(RuntimeException.class,()->lookup("#alert").queryAs(DialogPane.class));
            System.out.println(Stage.getWindows().size());
            sleep(5000);
// Si verifica l'exception se lui non trova l'alert in stato di running.
        });

    }
    @Test void ValidTest_get_Data(){


        Platform.runLater(()->{

            ButtonType button_type_ok = customDialog.getDialogPane().getButtonTypes().get(0);
            Button button_ok = (Button) customDialog.getDialogPane().lookupButton(button_type_ok);
            customDialog.setResultConverter( result->{
                if(result==button_type_ok){
                    return FieldData.FieldDataBuilder.getbuilder().setSigla(sigla.getText()).setPartita_iva(vat.getText()).
                            setAnagrafica_cliente(anagrafia.getText()).build();
                }
                return null;
            });



            SimulateEvents.writeOn(vat,"IT12345555555");
            SimulateEvents.writeOn(sigla,"af");
            SimulateEvents.writeOn(anagrafia,"AF");
            SimulateEvents.clickOn(button_ok);
            Assertions.assertThrows(RuntimeException.class,()->lookup("#alert").queryAs(DialogPane.class));
           customDialog.showAndWait().ifPresent(result->{
               System.out.println(result.getAnagrafica_cliente());
                Assertions.assertEquals("IT12345555555",result.getPartita_iva());
                Assertions.assertEquals("af",result.getSigla());
                Assertions.assertEquals("AF",result.getAnagrafica_cliente());
               try {
                   Thread.sleep(4000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           });
// Si verifica l'exception se lui non trova l'alert in stato di running.
        });
    }
    @Test void ValidTest_get_DatawithChoice(){


        Platform.runLater(()->{
            ChoiceBox<FieldData> choice_categoria=customDialog.add_choiceBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Valore").build());

            ChoiceBox<FieldData> choice_tipologia=customDialog.add_choiceBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Valore").
                    build());

            FieldData fieldData_categoria=FieldData.FieldDataBuilder.getbuilder().setNome("Antibiotico").setId(1).build();
            FieldData fieldData_tipologia=FieldData.FieldDataBuilder.getbuilder().setNome("Compresse").setId(2).build();


            choice_categoria.setValue(fieldData_categoria);
            choice_tipologia.setValue(fieldData_tipologia);


            ButtonType button_type_ok = customDialog.getDialogPane().getButtonTypes().get(0);
            Button button_ok = (Button) customDialog.getDialogPane().lookupButton(button_type_ok);
            customDialog.setResultConverter( result->{
                if(result==button_type_ok){
                    System.out.println("id: "+fieldData_categoria.getId());
                    return FieldData.FieldDataBuilder.getbuilder().setSigla(sigla.getText()).setPartita_iva(vat.getText()).
                            setAnagrafica_cliente(anagrafia.getText()).setCategoria(fieldData_categoria.getId()).setTipologia(fieldData_tipologia.getId()).
                            build();
                }
                return null;
            });



            SimulateEvents.writeOn(vat,"IT12345555555");
            SimulateEvents.writeOn(sigla,"af");
            SimulateEvents.writeOn(anagrafia,"AF");
            SimulateEvents.clickOn(button_ok);


           // SimulateEvents.simulate_selected_items(choice,fieldData_simulate);
            Assertions.assertThrows(RuntimeException.class,()->lookup("#alert").queryAs(DialogPane.class));
            customDialog.showAndWait().ifPresent(result->{


                Assertions.assertEquals("IT12345555555",result.getPartita_iva());
                Assertions.assertEquals("af",result.getSigla());
                Assertions.assertEquals("AF",result.getAnagrafica_cliente());
               Assertions.assertEquals(1,result.getCategoria());
                Assertions.assertEquals(2,result.getTipologia());

            });


// Si verifica l'exception se lui non trova l'alert in stato di running.

        });

    }
    @Test
    public void ValidChoice() {
        Database database=Mockito.mock(Database.class);
        ResultSet resultSet=Mockito.mock(ResultSet.class);
        DetailDao detailDao=new DetailDao(database);
        PharmaDao pharmaDao=new PharmaDao(database);
        PopulateChoice populateChoice=new PopulateChoice(detailDao,pharmaDao);
        Platform.runLater(() -> {


            Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
            try {
                Mockito.when(resultSet.next()).thenReturn(true,false);
                Mockito.when(resultSet.getInt(1)).thenReturn(1);
                Mockito.when(resultSet.getString(2)).thenReturn("Melarini");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            ChoiceBox<FieldData> categoria = customDialog.add_choiceBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Valore").build());

            List<FieldData> fieldData_pop=populateChoice.populate("pharma");
            Assertions.assertEquals(1,fieldData_pop.get(0).getId());
            categoria.getItems().addAll(fieldData_pop);
            SimulateEvents.writeOn(vat,"IT12345555555");
            SimulateEvents.writeOn(sigla,"af");
            SimulateEvents.writeOn(anagrafia,"AF");




            ButtonType button_type_ok = customDialog.getDialogPane().getButtonTypes().get(0);
            Button button_ok = (Button) customDialog.getDialogPane().lookupButton(button_type_ok);
            customDialog.setResultConverter( result->{
                if(result==button_type_ok){

                    return FieldData.FieldDataBuilder.getbuilder().setSigla(sigla.getText()).setPartita_iva(vat.getText()).
                            setAnagrafica_cliente(anagrafia.getText()).setCategoria(categoria.getValue().getId()).
                            build();
                }
                return null;
            });
        customDialog.showAndWait().ifPresent(fieldData -> {

           Assertions.assertEquals(1,fieldData.getCategoria());

        });

        SimulateEvents.clickOn(button_ok);
        });

        sleep(5000);

    }




}