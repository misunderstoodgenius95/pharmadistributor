package pharma.DialogController;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import pharma.Model.FieldData;

import pharma.config.Utility;
import pharma.javafxlib.Status;
import pharma.config.auth.UserGateway;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.config.PopulateChoice;
import pharma.dao.GenericJDBCDao;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class DialogControllerBase<F> extends CustomDialog<F> {
    private  ButtonType buttonType;

    public DialogControllerBase() {
        super("");
    }
    public void addControls(Control controls){
        getControlList().add(controls);
    }
    public void removeControls(Control controls){
        getControlList().remove(controls);
    }

    protected abstract boolean condition_event(F type) throws Exception;
    protected abstract Status condition_event_status(F type) throws Exception;
    public enum Mode{Insert,Update}
    private  Mode mode;
    private BooleanProperty condition_test;
    public DialogControllerBase(String content)  {
        super(content);
        initialize();
        setResult();
        buttonType=getDialogPane().getButtonTypes().get(1);
        condition_test=new SimpleBooleanProperty();
    }
    public DialogControllerBase(String content, UserGateway userGateway)  {
        super(content);
        initialize();
        setResult();
        buttonType=getDialogPane().getButtonTypes().get(1);
        condition_test=new SimpleBooleanProperty();
    }
    public DialogControllerBase(String content, PopulateChoice populateChoice)  {

       this(content);
        initialize();
        initialize(Optional.of(populateChoice),Optional.empty(),Optional.empty());

        setResult();


    }
    public DialogControllerBase(String content, PopulateChoice populateChoice, List<GenericJDBCDao> genericJDBCDao)  {

        this(content);
        initialize(Optional.of(populateChoice),Optional.of(genericJDBCDao),Optional.empty());



    }
    public DialogControllerBase(String content, List<GenericJDBCDao> genericJDBCDao) {

        this(content);

        initialize(Optional.empty(),Optional.of(genericJDBCDao),Optional.empty());



    }
    public DialogControllerBase(String content, List<GenericJDBCDao> genericJDBCDao, FieldData fieldData) {

        this(content);

        initialize(Optional.empty(),Optional.of(genericJDBCDao),Optional.of(fieldData));


    }
    public DialogControllerBase(String content, FieldData fieldData) {

        this(content);

        initialize(Optional.empty(),Optional.empty(),Optional.of(fieldData));


    }





    protected void setMode(Mode mode){
        this.mode=mode;

    }


    protected Mode getMode() {
        if(mode==null){
            throw new IllegalArgumentException("Mode is null");
        }
        return mode;
    }





    private  void setResult(){
        setResultConverter(dialog -> {
            if (dialog == getButton_click()) {
                 return  get_return_data();
            }
            return null;
        });

    }

    /**
     * using this for verify is insert it is correct.
     * @return
     */
    public boolean isCond() {
        return condition_test.get();
    }



    protected  abstract  void initialize();
    protected abstract <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData);

    /**
     * This method can be setting with FieldData Model that contains the Corrispective date that using for Insert Dao
     * @return
     */
    protected abstract F get_return_data();

    public   void execute(){


       AtomicBoolean  success = new AtomicBoolean(false);
        while(!success.get()) {
            showAndWait().ifPresentOrElse(result -> {
                try {
                    boolean cond = condition_event(result);
                    condition_test.set(cond);
                    showAlert(cond, "Errore Inserimento!");
                    if (cond) {
                        success.set(true);// Exit the ifPresent block without marking success
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            },()->{
                System.out.println("Closing");
                success.set(true);
                    }
            );
        }
    }
    public   void executeStatus(){


        AtomicBoolean  success = new AtomicBoolean(false);
        while(!success.get()) {
            showAndWait().ifPresentOrElse(result -> {
                        try {
                            Status status = condition_event_status(result);
                            boolean cond=status.isCondition();
                            condition_test.set(cond);
                            showAlert(cond, status.getMessage());


                            if (cond) {
                                success.set(true);// Exit the ifPresent block without marking success
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    },()->{
                        System.out.println("Closing");
                        success.set(true);
                    }
            );
        }
    }
    /**
     * Method that return condition Dao
     * @param
     * @return
     * @throws Exception
     */
;

    public   void  showAlert(boolean success,String error_message) {
        Alert.AlertType alertType = success ? Alert.AlertType.CONFIRMATION : Alert.AlertType.ERROR;
        String message = success ? "Inserimento effettuato" : error_message;
        create_alert(alertType, "", message);

    }

    public void  create_alert(Alert.AlertType alert_type, String title_header, String body) {
        Utility.create_alert(alert_type, title_header, body);


    }


}
