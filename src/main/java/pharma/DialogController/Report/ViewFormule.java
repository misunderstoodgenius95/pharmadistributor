package pharma.DialogController.Report;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import pharma.Service.Report.BuildFormula;
import pharma.Service.Report.UserFormula;
import pharma.config.CustomFormuleCell;
import pharma.config.InvalidFormulaException;
import pharma.dao.CustomFormulaDao;
import pharma.dao.PurchaseOrderDao;
import pharma.javafxlib.Dialog.CustomDialog;

public class ViewFormule extends CustomDialog<UserFormula> {
   private ListView<UserFormula> listView;
   private CustomFormulaDao customFormulaDao;
   private TextField formulashowTextField;
   private Button executeButton;
   private TextField textFieldExecuteFormula;
    private Button btnTest;
    private BuildFormula buildFormula;
    private PurchaseOrderDao purchaseOrderDao;
    public ViewFormule(String content, CustomFormulaDao customFormuleDao, PurchaseOrderDao purchaseOrderDao) {
        super(content);
        setTitle("Formule Personalizzate");
        setHeaderText("Esegui la tua formula personalizzata");
        listView=addListView();
        listView.setCellFactory(value->new CustomFormuleCell());
        getDialogPane().setPrefHeight(800);
        getDialogPane().setPrefWidth(900);
        formulashowTextField=add_text_field("");
        formulashowTextField.setEditable(false);
        btnTest=addButton("Test");
        textFieldExecuteFormula=add_text_field("");
        textFieldExecuteFormula.setEditable(false);
        listView.getItems().addAll(customFormuleDao.findAll());
        if(listView.getItems().isEmpty()){
            listView.getItems().addAll(new UserFormula("Nessuna formula ancora creata",""));
        }
       this.customFormulaDao=customFormuleDao;
       listener_listView();
       listenerBtn();
       this.purchaseOrderDao=purchaseOrderDao;

    }
    private void listener_listView(){
    listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserFormula>() {
        @Override
        public void changed(ObservableValue<? extends UserFormula> observable, UserFormula oldValue, UserFormula newValue) {
            System.out.println("change");
            formulashowTextField.setText(newValue.getFormula());
        }
    });
    }
    private void listenerBtn(){
        btnTest.setOnAction(event -> {
        buildFormula=new BuildFormula(listView.getSelectionModel().getSelectedItem(),purchaseOrderDao);
            try {
                textFieldExecuteFormula.setText(String.valueOf(buildFormula.buildFormula()));
            } catch (InvalidFormulaException e) {
                throw new RuntimeException();
            }


        });




    }







}
