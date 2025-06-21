package pharma.Handler;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import pharma.Storage.FileStorage;
import pharma.config.auth.ExpireToken;
import pharma.config.auth.UserService;
import pharma.config.auth.UserServiceResponse;
import pharma.security.Stytch.StytchClient;
import pharma.security.TokenUtility;
import pharma.test2.ActiveClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

public class ChatHandler {
    //                     Pharmacist     List<Msg>
    private ObservableMap<String, ObservableList<String>> current_chat;
     private VBox vBox_active_user;
     private TextArea textArea;
     private SimpleObjectProperty<Button> property_btn_active;
    public ChatHandler(VBox vBox_active_user, TextArea textArea) {

        if(vBox_active_user==null || textArea==null){
            throw new IllegalArgumentException("vbox or textArea are null");
        }
        current_chat= FXCollections.observableHashMap();
        this.vBox_active_user=vBox_active_user;
        this.textArea=textArea;
        property_btn_active=new SimpleObjectProperty<>(null);



            vBox_active_user.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
                if (e.getTarget() instanceof Node) {
                    Node targetNode = (Node) e.getTarget();

                    // Check if target is directly a button
                    if (targetNode instanceof Button) {
                        Button button = (Button) targetNode;
                        body_handler(button,e);

                    }
                    // Check if target's parent is a button
                    else if (targetNode.getParent() instanceof Button) {
                        Button button = (Button) targetNode.getParent();
                        body_handler(button,e);
                    }
                }
            });







    }

    private void body_handler(Button button,MouseEvent e){

        String pharmacist_email = (String) button.getUserData();
        textArea.clear();
        for(String msg:current_chat.get(pharmacist_email)){
            textArea.appendText(msg);
        }
        property_btn_active.set(button);

    }

    public SimpleObjectProperty<Button> getProperty_btn_active() {
        return property_btn_active;
    }

    public SimpleObjectProperty<Button> property_btn_activeProperty() {
        return property_btn_active;
    }

    public ObservableMap<String, ObservableList<String>> getCurrent_chat() {
        return current_chat;
    }

    /**
     * Se il primo
     * @param email_pharmacist
     */
    public void add_chat_msg(String email_pharmacist,String msg){
        if(!ActiveClient.getPharmacist_map().containsKey(email_pharmacist)){
            throw new IllegalArgumentException("Phramacist not present!");
        }
        if(current_chat.containsKey(email_pharmacist)){

            ObservableList<String>list=current_chat.get(email_pharmacist);
            list.add(msg);

        }else{
            ObservableList<String> obs_list=FXCollections.observableArrayList();
            obs_list.add(msg);
            current_chat.putIfAbsent(email_pharmacist,obs_list);
            add_btn_user(email_pharmacist);

        }











    }

    public String get_role(){

       String role;
        HashMap<String,String> hashMap_json = null;
        String jwt=null;
        try {
            hashMap_json = FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader("stytch.properties"));
            jwt= FileStorage.getProperty("jwt",new FileReader("config.properties"));
            StytchClient stytchClient =new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url"));
            UserService userService=new UserService(stytchClient);
            UserServiceResponse ur=userService.authenticate_jwt(jwt);
            return  TokenUtility.extractRole(ur.getBody());

        } catch (FileNotFoundException | ExpireToken e) {
            throw new RuntimeException(e);
        }


    }

    private   void  add_btn_user(String user) {
        Button button=new Button(user);
        button.setUserData(user);
        button.setPrefHeight(50);
        button.setPrefWidth(300);
        button.setCursor(Cursor.HAND);
        button.setFont(new Font(16.0));
        vBox_active_user.getChildren().add(button);
    }



}
