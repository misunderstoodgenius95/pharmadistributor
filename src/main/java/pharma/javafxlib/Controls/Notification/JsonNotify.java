package pharma.javafxlib.Controls.Notification;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;
import pharma.javafxlib.Controls.Notify;

import java.util.List;

/**
 * Give me a String contain a JsoObject or JSon Array  be execute a Notify
 */
public class JsonNotify {
    private String json;
    private  List<String> selected_filed;
    private  String message;
    private  String title;

    public JsonNotify(String json, List<String> selected_field, String message,String title) {
        this.json = json;
        this.selected_filed=selected_field;
        this.message=message;
        this.title=title;




    }

    public void execute(){
        if(json.startsWith("{")){
            create_single_notify();

        }else if(json.startsWith("[")){
            create_multi_notify();

        }



    }

   protected  String  create_body(JSONObject input){

        StringBuilder body= new StringBuilder(message + " ");
        for(String field:selected_filed){
            if(input.has(field)){
                body.append(input.get(field));
            }

        }
        return  body.toString();
    }

    private  void create_single_notify(){

        String body=create_body(new JSONObject(json));
        Notify.create(title,body);
    }

    private  void create_multi_notify(){
        JSONArray jsonArray=new JSONArray(json);


        for(int  i=0;i<jsonArray.length(); i++){
            int delaySeconds = i * 3;
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            PauseTransition delay = new PauseTransition(Duration.seconds(delaySeconds));

                delay.setOnFinished(event -> {
                    Notify.createWithButtonClick(title, create_body(jsonObject));

                });
                delay.play();



        }





    }




}
