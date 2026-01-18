package pharma.DialogController.Report;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import pharma.DialogController.Table.ProductTableCustom;
import pharma.Model.Acquisto;
import pharma.Model.FieldData;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.dao.FarmacoDao;
import pharma.Service.Picco;
import pharma.javafxlib.Dialog.CustomDialog;


import java.util.List;
import java.util.Optional;


public class PiccoDialog extends CustomDialog<FieldData> {
    private  Picco picco;
    private Button button_choice;
    private ProductTableCustom productTableCustom;
    private FarmacoDao farmacoDao;
    private TextField tf_text_name;
    private Button btn_send;
    private TextField tf_create_farmaco;
    private  TextField tf_create_tipologia;
    private  TextField tf_create_misura;
    private TableView<Acquisto> tableResult;
    private List<Acquisto> acquistos;
    public PiccoDialog(String content, List<Acquisto> acquistos, FarmacoDao farmacoDao) {
        super(content);
        getDialogPane().setPrefHeight(800);
        getDialogPane().setPrefWidth(800);
        tf_text_name=add_text_field("Inserisci Nome del farmaco");
        button_choice = addButton("Seleziona Farmaco");
        btn_send=addButton("Invia");
        picco = new Picco();
        this.acquistos=acquistos;
        setting_header_farmaco();
        tableResult=add_tableCustom();
        this.farmacoDao=farmacoDao;
        productTableCustom=new ProductTableCustom("Visualizza Prodotto");
        productTableCustom.add_radio();
        listenerBtnChoice();
        listenerBtnSend();
        config_table();



    }
    private void config_table() {
        tableResult.getColumns().addAll(TableUtility.generate_column_date("Data", "data_acquisto"),
                TableUtility.generate_column_double("Prezzo","price"),
                TableUtility.generate_column_int("Quantita","quantity"));




    }


    private void listenerBtnChoice(){

            button_choice.setOnAction(event -> {
            List<FieldData> list=farmacoDao.findByName(tf_text_name.getText());
            productTableCustom.getTableView().setItems(FXCollections.observableArrayList(list));
            productTableCustom.show();
            button_choice.setText("Farmaco Selezionato");

        });
    }
    private void listenerBtnSend(){
        btn_send.setOnAction(event -> {
            if(productTableCustom.getRadio_value().getValue()!=null) {
                FieldData fd_value = productTableCustom.getRadio_value().getValue();
                tf_create_farmaco.setText(fd_value.getNome());
                tf_create_misura.setText(fd_value.getUnit_misure());
                tf_create_tipologia.setText(fd_value.getNome_tipologia());
                Optional<List<Acquisto>> list=picco.calculate_analisi_picco(acquistos,fd_value.getId());
                if(list.isEmpty()){
                    Utility.create_alert(Alert.AlertType.WARNING,"","Nessun Dato Presente");
                }else {
                    if(list.get().isEmpty()){
                        Utility.create_alert(Alert.AlertType.WARNING,"","Nessun picco presente");
                    }else{
                       tableResult.getItems().setAll(list.get());
                    }


                }
            }
        });
    }


    private void setting_header_farmaco(){
        GridPane gridPane = add_gridpane(1);
        tf_create_farmaco=createLabeledTextField("Farmaco", "", gridPane, 0, 0, 0, 1);
        tf_create_tipologia=createLabeledTextField("Tipologia", "", gridPane, 1, 0, 1, 1);
        tf_create_misura=createLabeledTextField("Misura", "", gridPane, 2, 0, 2, 1);

    }





}






