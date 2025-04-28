package pharma.Controller.subpanel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import pharma.Handler.DialogHandler;
import pharma.Handler.FarmacoDialogHandler;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.*;
import pharma.javafxlib.Search.FilterSearch;
import pharma.dao.DetailDao;
import pharma.dao.FarmacoDao;
import pharma.dao.PharmaDao;
import pharma.config.database.Database;
import pharma.javafxlib.CustomTableView.RadioButtonTableColumn;


import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.util.*;

public class Farmaco implements Initializable {
    @FXML
    public TableView<FieldData> table_id;
    public ChoiceBox<String> choicebox_search;
    public TextField serch_value;
    @FXML
    private Button btn_id_add;
    @FXML
    public  AnchorPane anchor;
    @FXML
    private Button edit_btn;
    private SimpleObjectProperty<FieldData> fieldDataSimpleObjectProperty;
private  FarmacoDialogHandler farmacoDialogHandler;

    private ObservableList<FieldData> obs_fieldData= FXCollections.observableArrayList();
    FarmacoDao farmacoDao;
    DetailDao detailDao;
    PharmaDao pharmaDao;
    public Farmaco(){
        try {
            Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            farmacoDao=new FarmacoDao( Database.getInstance(properties));
            detailDao=new DetailDao(Database.getInstance(properties));
            pharmaDao=new PharmaDao(Database.getInstance(properties));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void btn_action_add(ActionEvent event) throws AccessException {
        farmacoDialogHandler.setOperation(DialogHandler.Mode.Insert,null);
        farmacoDialogHandler.execute();
        table_id.getItems().clear();
        table_id.getItems().setAll(obs_fieldData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utility.create_btn(btn_id_add, "add.png");
        farmacoDialogHandler=new FarmacoDialogHandler("Aggiungi Farmaco",obs_fieldData, farmacoDao,detailDao,pharmaDao);
        fieldDataSimpleObjectProperty=new SimpleObjectProperty<>();
        RadioButtonTableColumn<FieldData> actioncolumn=new RadioButtonTableColumn<>(){
            @Override
            protected void onButtonClick(FieldData rowData) {
                edit_btn.setVisible(true);
                fieldDataSimpleObjectProperty.set(rowData);
            }
        };
        table_id.getColumns().addAll(TableUtility.generate_column_string("Nome","nome"),
        TableUtility.generate_column_string("Descrizione","description"),
        TableUtility.generate_column_string("Categoria","nome_categoria"),
        TableUtility.generate_column_string("Tipologia","nome_tipologia"),
        TableUtility.generate_column_string("Misura","unit_misure"),
        TableUtility.generate_column_string("Principio Attivo","nome_principio_attivo"),
        TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),
                TableUtility.generate_column_int("Quantità","quantity"),actioncolumn);
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
       obs_fieldData.setAll(farmacoDao.findAll());
        table_id.getItems().addAll(obs_fieldData);
        table_id.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: #636165;");
        edit_btn.setVisible(false);
        Utility.add_iconButton(edit_btn, FontAwesomeSolid.EDIT);
        serch_value.setDisable(true);
        choicebox_search.getItems().setAll(FXCollections.observableArrayList("Tipologia","Categoria","Principio_Attivo","Misura","Casa_Farmaceutica"));


    }

    public void edit_btn_action(ActionEvent actionEvent) {
        try {
            farmacoDialogHandler.setOperation(DialogHandler.Mode.Update,fieldDataSimpleObjectProperty.get());
            farmacoDialogHandler.execute();
            table_id.refresh();
        /*   table_id.setItems(obs_fieldData);*/

        } catch (AccessException e) {
            throw new RuntimeException(e);
        }

    }

    public void choicebox_search_action(ActionEvent actionEvent) {
        serch_value.setDisable(false);


        /* choicebox_search.getValue()*/
        FilterSearch filterSearch = new FilterSearch(table_id, serch_value) {
            // newvalue->text fieldData -> Tabella
            @Override
            protected boolean condition(String newValue, FieldData fieldData) {

                return FilterSearch.choice_value(choicebox_search.getValue(),
                        new AbstractMap.SimpleEntry<>("Tipologia", fieldData.getNome_tipologia()),
                        new AbstractMap.SimpleEntry<>("Categoria", fieldData.getNome_categoria()),
                        new AbstractMap.SimpleEntry<>("Principio_Attivo", fieldData.getNome_principio_attivo()),
                        new AbstractMap.SimpleEntry<>("Misura", fieldData.getUnit_misure()),
                        new AbstractMap.SimpleEntry<>("Casa_Farmaceutica", fieldData.getNome_casa_farmaceutica())).toLowerCase().
                        contains(newValue.toLowerCase());


            }
        };
    }






}
