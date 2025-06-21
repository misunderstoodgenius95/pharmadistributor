package pharma.chat;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ChatGui {



    public enum User_type{Seller,Pharmacist};

    public static GridPane add_chat_container(String message,String username, User_type userType){
        GridPane gridPane=new GridPane(4,4);

        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setPrefHeight(10);
        gridPane.setPrefWidth(10);
        Label label_username=new Label(username);
        Label label_date=new Label(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        Label label_msg=new Label(message);
        label_msg.setStyle("-fx-padding: 8; -fx-background-radius: 10;-fx-font-size: 17px;");

        if (userType.equals(User_type.Seller)) {
            label_username.setStyle("-fx-text-fill: rgba(0,50,249,0.69);-fx-font-size: 14px;");
            label_msg.setStyle(label_msg.getStyle() + "-fx-background-color: rgba(123,123,123,0.96); -fx-text-fill: white;");

        } else {
            label_msg.setStyle(label_msg.getStyle() + "-fx-background-color: rgba(0,178,255,0.28); -fx-text-fill: rgb(2,24,24);");
            label_username.setStyle("-fx-text-fill: rgb(2,24,24);-fx-font-size: 14px;");
        }


        gridPane.add(label_username,0,0);
        gridPane.add(label_date,1,0);
        gridPane.add(label_msg,0,1);
        return  gridPane;












    }




}
