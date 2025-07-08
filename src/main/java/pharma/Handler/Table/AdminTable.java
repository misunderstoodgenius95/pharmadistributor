package pharma.Handler.Table;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pharma.Model.FieldData;
import pharma.Model.User;
import pharma.config.TableUtility;
import pharma.config.auth.UserService;
import pharma.javafxlib.CustomTableView.ButtonTableColumn;
import pharma.javafxlib.Dialog.CustomDialog;

public class AdminTable  extends CustomDialog<User.Results> {
    private  UserService userService;
    private ButtonTableColumn<User.Results> buttonTableColumn;
    private TableView<User.Results> table_view;
    private ObjectProperty<User> user_property;
    public AdminTable(String content) {
        super(content);
        getDialogPane().setPrefHeight(900);
        getDialogPane().setPrefWidth(1200);
        this.user_property=new SimpleObjectProperty<>();
        listener_property();
        // it can be passing object and product a objetcprorperty
        buttonTableColumn=new ButtonTableColumn<>("Stato",results ->
                new SimpleBooleanProperty(results.getTrustedMetadata().isIs_enable()),
            (result,new_value)->{
            result.getTrustedMetadata().setIs_enable(new_value.get());
        });
        create_table();




        //mail, ruolo,stato,Ultimo accesso










    }

    public void setUser_property(User user_property) {
        this.user_property.set(user_property);
    }

    private void listener_property(){

        user_property.addListener((observable, oldValue, newValue)
                -> table_view.getItems().setAll(newValue.getResults()));
    }


    public void create_table(){
        table_view=add_table();

       table_view.getColumns().addAll(
             column_role(),column_email(),
           TableUtility.generate_column_timestamp("Ultimo acesso","last_access"),buttonTableColumn
       );



    }
    TableColumn<User.Results,String> column_role(){


        TableColumn<User.Results, String> role_column = new TableColumn<>("Ruolo");
        role_column.setCellValueFactory(celldata->{
            User.Results re=celldata.getValue();
            String role=re.getTrustedMetadata().getRole();
            return new SimpleStringProperty(role);
        });
        return role_column;

    }

    TableColumn<User.Results,String> column_email(){
        TableColumn<User.Results,String> email_column=new TableColumn<>("Email");
        email_column.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getEmails().getFirst().getEmail()));
        return email_column;

    }






}
