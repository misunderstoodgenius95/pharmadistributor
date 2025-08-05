package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import pharma.Handler.Table.TableCustom;
import pharma.HttpExpireItem;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.config.database.Database;
import pharma.config.net.ClientHttp;
import pharma.dao.LottiDao;
import pharma.javafxlib.CustomTableView.CheckBoxTableColumn;
import pharma.javafxlib.DoubleClick_Menu;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Expireproduct implements Initializable {
    public TableView<FieldData> table_id;
    public Button btn_product_add_id;
    public AnchorPane anchor;
    public Spinner spinner_id_days;
    public Button btn_send;
    private TableCustom tableCustom;
    private ObservableList<FieldData> observableList;
    private LottiDao lottiDao;
    private CheckBoxTableColumn<FieldData> checkBoxTableColumn;
    private Database database;
    private Properties properties;
    private  ObservableList<FieldData> obs_table;
    private  ClientHttp clientHttp;
    private    HttpExpireItem httpExpireItem;
    private SimpleIntegerProperty simpleIntegerProperty;
    public Expireproduct() {
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            database=Database.getInstance(properties);
            lottiDao=new LottiDao(database,"lotto");
            clientHttp=new ClientHttp();
            httpExpireItem=new HttpExpireItem(clientHttp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void btn_product_add_action(ActionEvent actionEvent) {
        tableCustom.show();




    };
    public void listener_delete(MenuItem menu_delete) {
        menu_delete.setOnAction(event -> {

            System.out.println("listener delete");
            FieldData fieldData = table_id.getSelectionModel().getSelectedItem();

            if (fieldData != null) {
                Platform.runLater(()-> {
                    ObservableList<FieldData> obs=table_id.getItems();




                    obs.remove(fieldData);
                    table_id.refresh();
                    System.out.println("after delete" +obs.size());

                });


            }else{
                System.out.println("Fieldata not present");
            }



        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utility.add_iconButton(btn_send, FontAwesomeSolid.PLUS_CIRCLE);
        tableCustom=new TableCustom("Scegli Lotti",lottiDao);
        obs_table=FXCollections.observableArrayList();
        observableList=FXCollections.observableArrayList();
        spinner_id_days.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,20000,1));
        spinner_id_days.setEditable(true);
        spinner_id_days.setPrefWidth(100);
        spinner_id_days.setPrefHeight(60);
        spinner_id_days.getEditor().setFont(new Font(20));
        simpleIntegerProperty=new SimpleIntegerProperty(-1);
        btn_send.setUserData(simpleIntegerProperty);




        table_id.getColumns().addAll(
                TableUtility.generate_column_string("lotto Code","lotto_id"),
                TableUtility.generate_column_string("Nome","nome"),
                TableUtility.generate_column_string("Categoria","nome_categoria"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),
                TableUtility.generate_column_string("Principio Attivo","nome_principio_attivo"),
                TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),
                TableUtility.generate_column_int( "Pezzi","quantity"),
                TableUtility.generate_column_string("Data di produzione","production_date"),
                TableUtility.generate_column_string("Data di scadenza","elapsed_date"),
                TableUtility.generate_column_string("Disponibilità","availability"));
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        DoubleClick_Menu<FieldData> dataDoubleClickMenu=new DoubleClick_Menu<FieldData>(table_id);
        MenuItem menuItem=dataDoubleClickMenu.create_menu_item("Elimina");


        listener_delete(menuItem);




        tableCustom.getButtonOK().setOnAction(event -> {
            obs_table.setAll(tableCustom.getSelectedRows());
           table_id.setItems(obs_table);
            btn_product_add_id.setText("Lotti selezionati");
        });


        //


    }

    public void btn_send_action(ActionEvent actionEvent) {
       String uri="";
        try {
            uri=FileStorage.getProperty("expire_post",new FileReader("expire_item.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        int status=httpExpireItem.post_expire(uri,table_id.getItems(),(int)spinner_id_days.getValue());
        System.out.println(status);
        Utility.network_status(status);
        simpleIntegerProperty.set(status);





    }
}
