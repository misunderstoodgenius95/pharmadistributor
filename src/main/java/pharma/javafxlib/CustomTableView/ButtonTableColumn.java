package pharma.javafxlib.CustomTableView;


import javafx.application.Platform;
import javafx.scene.control.*;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import pharma.Model.User;
import pharma.config.Utility;
import pharma.config.auth.UserService;

import java.lang.foreign.PaddingLayout;
import java.nio.file.LinkOption;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Logger;

/*
public class ButtonTableColumn<S> extends TableColumn<S, Boolean> {

    public ButtonTableColumn(String header, Function<S,BooleanProperty> propertyName, BiConsumer<S,BooleanProperty> update) {
        super(header);

        // Collega la colonna alla proprietà booleana del modello
        setCellValueFactory(celldata->propertyName.apply(celldata.getValue()));

        setCellFactory(param -> new TableCell<S, Boolean>() {
            private final Button button = new Button();

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    // Imposta il testo del bottone in base al valore booleano
                    updateButtonText(item);
                    setGraphic(button);

                    button.setOnAction(event -> {
                        // Cerca la proprietà a cui la cella è legata
                        ObservableValue<Boolean> observable = getTableColumn().getCellObservableValue(getIndex());
                        if (observable instanceof BooleanProperty) {
                            BooleanProperty property = (BooleanProperty) observable;
                            // Inverti il valore della proprietà
                            property.set(!property.get());
                        }
                    });
                }
            }

            // Metodo helper per cambiare testo e stile
            private void updateButtonText(boolean isActive) {
                if (isActive) {
                    button.setText("Attivo");
                    button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                } else {
                    button.setText("Inattivo");
                    button.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                }
            }
        });
    }
}

*/
public class ButtonTableColumn<S> extends TableColumn<S,Void> {
    private  UserService userService;
    private BooleanProperty property;
    private Function<S,BooleanProperty> function_get;
    private  BiConsumer<S,BooleanProperty> biConsumer_get;
    private Logger logger= Logger.getLogger(ButtonTableColumn.class.getName());
    public  ButtonTableColumn(String header, Function<S,BooleanProperty> function_get, BiConsumer<S,BooleanProperty> biConsumer_update){
        setText(header);
        setCellFactory(createButton());
        this.function_get=function_get;
        this.biConsumer_get=biConsumer_update;

    }

    private Callback<TableColumn<S,Void>, TableCell<S,Void>> createButton(){

        return  new Callback<TableColumn<S, Void>, TableCell<S, Void>>() {
            @Override
            public TableCell<S, Void> call(TableColumn<S, Void> param) {
                return new TableCell<>() {
                    Button button = new Button();

                    {
                    button.setOnAction(event -> {
                            // Return instance S
                        S item= getTableRow().getItem();
                        if(item!=null) {
                            Platform.runLater(()-> {


                                Alert alert = Utility.create_alert_confirm(Alert.AlertType.CONFIRMATION, "Conferma Aggiornamento", "Vuoi cambiare stato?");


                                Optional<ButtonType> optional = alert.showAndWait();
                                if (optional.isPresent()) {
                                    logger.info("is present" + optional.get());

                                    if (optional.get().equals(ButtonType.OK)) {
                                        logger.info("Accept");
                                         User.Results result=(User.Results) item;
                                        System.out.println("user_id"+result.getUser_id());
                                       // userService.user_revocate("",)



                                        BooleanProperty current_value = function_get.apply(item);
                                        BooleanProperty new_value = new SimpleBooleanProperty(!current_value.get());
                                        biConsumer_get.accept(item, new_value);
                                        updateButton(new_value.get());
                                    } else {
                                        logger.warning("refuse");
                                    }
                                }

                            });


                        }


                    });
                    }


                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setGraphic(null);
                        }else{

                            S model= getTableRow().getItem();
                            BooleanProperty current_value=function_get.apply(model);
                            updateButton(current_value.getValue());
                            setGraphic(button);


                        }
                    }
                    private void updateButton(boolean currentValue){
                        if(currentValue){
                            button.setText("Attivo");
                        }else{
                            button.setText("Inattivo");
                        }
                    }



                };
            }





        };
    }


}


