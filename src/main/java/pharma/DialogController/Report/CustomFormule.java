package pharma.DialogController.Report;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pharma.DialogController.DialogControllerBase;
import pharma.Model.FieldData;
import pharma.Service.Report.BuildFormula;
import pharma.Service.Report.FormuleEngine;
import pharma.Service.Report.UserFormula;
import pharma.config.InvalidFormulaException;
import pharma.config.PopulateChoice;
import pharma.config.Utility;
import pharma.dao.CustomFormulaDao;
import pharma.dao.GenericJDBCDao;
import pharma.dao.PurchaseOrderDao;
import pharma.javafxlib.Controls.TextFieldComboBox;
import pharma.javafxlib.Status;

import java.util.List;
import java.util.Optional;

public class CustomFormule extends DialogControllerBase<UserFormula> {
    private TextFieldComboBox<String> formuleSearchable;
    private TextField  formulaNameTextFiled;
    private  TextFieldComboBox<String> operazioniSearchable;
    private TextFieldComboBox<String> optionSearchable;
    private TextArea textArea;
    private TextField textField_testValue;
    private Button btnTest;
    private PurchaseOrderDao purchaseOrderDao;
    private CustomFormulaDao customFormulaDao;
    public CustomFormule(String content,PurchaseOrderDao purchaseOrderDao,CustomFormulaDao customFormulaDao) {
        super(content);
        this.purchaseOrderDao=purchaseOrderDao;
        this.customFormulaDao=customFormulaDao;
    }


    @Override
    protected boolean condition_event(UserFormula type)  {
        BuildFormula buildFormula=new BuildFormula(type,purchaseOrderDao);
        try {
            buildFormula.buildFormula();
        } catch (InvalidFormulaException e) {
            return false;
        }
         return customFormulaDao.insert(type);

    }

    @Override
    protected Status condition_event_status(UserFormula type) throws Exception {
        return null;
    }


    private void listener_opzioni() {
        optionSearchable.getChoiceBox().valueProperty().addListener(
                (observable, oldValue, newValue) -> {
            if(newValue!=null){
                textArea.setText(textArea.getText()+newValue);

            }
        });
    }


    @Override
    protected void initialize() {
        getDialogPane().setPrefWidth(800);
        getDialogPane().setPrefHeight(900);
        this.add_label("Crea la tua formula");
        formulaNameTextFiled=add_text_field("Inserisci Titolo Formula");
        operazioniSearchable=add_combox_search_with_textfield(FXCollections.observableArrayList());
        operazioniSearchable.getChoiceBox().setValue("Scegli Operazioni");
        operazioniSearchable.getChoiceBox().getItems().setAll(FormuleEngine.operazioni);
        formuleSearchable=this.add_combox_search_with_textfield(FXCollections.observableArrayList());
        formuleSearchable.getChoiceBox().setValue("Scegli Attributo");
        formuleSearchable.getChoiceBox().getItems().addAll(FormuleEngine.dati_map.keySet());
        optionSearchable=this.add_combox_search_with_textfield(FXCollections.observableArrayList());
        optionSearchable.getChoiceBox().setValue("Scegli Opzione");
        optionSearchable.getChoiceBox().getItems().addAll(")",",");
        add_label("Ex: formula somma(moltiplicazione(valore1,valore2),valore3)");
        add_label("Ex: somma(valore1,valore2)");
        textArea=addTextArea();
        btnTest=addButton("Test");
        textField_testValue=add_text_field("");
        getControlList().removeAll(operazioniSearchable,operazioniSearchable,formuleSearchable);
        listener_attributi();
        listener_operazioni();
        listener_opzioni();
        listenerTest();


    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {

    }
    private void listenerTest(){
        btnTest.setOnAction(action->{
            UserFormula userFormula=new UserFormula(formulaNameTextFiled.getText(),textArea.getText());
            BuildFormula buildFormula=new BuildFormula(userFormula,purchaseOrderDao);
            try {
                textField_testValue.setText(String.valueOf(buildFormula.buildFormula()));
            } catch (InvalidFormulaException e) {
                Utility.create_alert(Alert.AlertType.INFORMATION,"","Formato Formula non corretto!");
            }


        });


    }

    @Override
    protected UserFormula get_return_data() {
     return new UserFormula(formulaNameTextFiled.getText(),textArea.getText());
    }
    public void listener_operazioni() {
        operazioniSearchable.getChoiceBox().valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue!=null){
                    textArea.setText(textArea.getText()+newValue+"(");
                }
            }
        });
    }

        public void listener_attributi() {
        formuleSearchable.getChoiceBox().valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue!=null){
                    System.out.println("attributi");
                    textArea.setText(textArea.getText()+newValue);
                }
            }
        });

        }










}
