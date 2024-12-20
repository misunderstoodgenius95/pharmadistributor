package pharma.config;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class InputValidation {

    private  static  final String p_iva_regex ="^IT[0-9]{11}$";
    private static  final  String input_regex="^[A-Z a-z0-9-_]+$";


        private  InputValidation(){


        }

        private static  boolean generic_validation(String regex, TextField textField){
        Pattern pattern = Pattern.compile(regex);
        if (textField.getText().isEmpty() ||
                textField.getText().equalsIgnoreCase("")
                ||! pattern.matcher(textField.getText()).find()
        ) {

            return  false;
        }
        return  true;

        }

        public  static boolean validate_p_iva(TextField textField){
          return   generic_validation(p_iva_regex,textField);
/*if(!generic_validation(p_iva_regex,textField)){

    Utility.create_alert(Alert.AlertType.ERROR,"Partita Iva!","Campo Partita Iva non valido!");
}

 */



        }







}
