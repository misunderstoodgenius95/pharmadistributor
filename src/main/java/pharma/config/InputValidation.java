package pharma.config;

import com.sun.jdi.connect.Connector;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class InputValidation {
    private static final String latitude_regex="^-?([0-8]?[0-9](\\.[0-9]{1,15})?|90(\\.0{1,15})?)$";
    private  static final String longitude_regex="^-?(1[0-7][0-9](\\.[0-9]{1,15})?|180(\\.0{1,15})?|[0-9]{1,2}(\\.[0-9]{1,15})?)$";
    private  static  final String p_iva_regex ="^IT[0-9]{11}$";
    private static final String audience_regex="^https:\\/\\/[a-z]+.[a-z]+[\\/]*[a-z]*$";
    private static final String password_regex="(?=.*[A-Z])*(?=.*[a-z])(?=.*\\d)(?=.*\\W).{11,}";
   // private static  final  String email_regex="^[\\w\\.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static  final String double_digit_regex="^[\\d]*\\.[\\d]*$";
   private static  final  String input_regex="^[\\w]+$";
    private static  final String cap="^[\\d]{5}$";
     private  final static   String email_regex="^[\\w]+[\\.]*[-]*[\\w]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
     private final static  String lotto_code="^[\\w]{3,}$";
     private  final  static  String digit="^[0-9]*$";
     private final  static  String stytch_project_id="^project-[a-z]+-[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$";
     private final  static  String stytch_secret_key="^secret-(test|live)-[ \\w \\- =]{24,40}$";
     private final static  String stytch_user_id="^user-(test|live)-[a-f\\d]{8}-[a-f\\d]{4}-[a-f\\d]{4}-[a-f\\d]{4}-[a-f\\d]{12}$";
        private  InputValidation(){


        }

        private static  boolean generic_validation(String regex, String input_regex){
        Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(input_regex).find();

        }
    public static   boolean filled_text(String text) {

        return generic_validation(input_regex,text);





    }

        public  static boolean validate_p_iva(String input){
            System.out.println("execute vat");
            return generic_validation(p_iva_regex,input);
        }
        public static  boolean validate_email(String input){

            if(input ==null){
                throw  new IllegalArgumentException("Input is null");
            }
            return generic_validation(email_regex,input);
        }
        public static  boolean validate_password(String input){

            if(input ==null){
                throw  new IllegalArgumentException("Input is null");
            }
        return generic_validation(password_regex,input);
    }

    public static boolean validate_audience(String input){
            if(input ==null){
                throw  new IllegalArgumentException("Input is null");
            }
            return generic_validation(audience_regex,input);


    }
    public static boolean validate_lotto_code(String input){
        System.out.println("execute lotto code");
        if(input ==null){
            throw  new IllegalArgumentException("Input is null");
        }
        return generic_validation(lotto_code,input);


    }
    public static boolean validate_digit(String input){
        System.out.println("execute digit");
        if(input ==null){
            throw  new IllegalArgumentException("Input is null");
        }
        return generic_validation(digit,input);


    }
    public static boolean validate_double_digit(String input) {
        System.out.println("execute double digit");
        if (input == null) {
            throw new IllegalArgumentException("Input is null");
        }
        return generic_validation(double_digit_regex,input);
    }
    public static  boolean validate_cap(String input){
        System.out.println("execute double digit");
        if (input == null) {
            throw new IllegalArgumentException("Input is null");
        }
        return generic_validation(cap,input);
    }
    public  static boolean validate_stytch_project_id(String input){
            if(input==null){
                throw new IllegalArgumentException("Input is null");
            }
            return generic_validation(stytch_project_id,input);
    }
    public  static boolean validate_stytch_secret_key(String input){
        if(input==null){
            throw new IllegalArgumentException("Input is null");
        }
        return generic_validation(stytch_secret_key,input);
    }
    public static  boolean validate_stytch_user_id(String input){
            if(input==null){
                throw new IllegalArgumentException("Input is null");
            }
            return generic_validation(stytch_user_id,input);
    }



    public static  boolean get_validation(String id,String text){
        
        return switch (id) {
            case "Email" -> validate_email(text);
            case "Password" -> validate_password(text);
            case "Vat" -> validate_p_iva(text);
            case "Double_Digit"->validate_double_digit(text);
            case "Lotto_code"->validate_lotto_code(text);
            case"Cap"->validate_cap(text);
            case "lat"->validate_lat(text);
            case "lng"->validate_lng(text);
            default -> false;


        };
    }

    public static boolean validate_lng(String text) {
        if(text==null){
            throw new IllegalArgumentException("Input is null");
        }
        return generic_validation(longitude_regex,text);
    }



   public static boolean validate_lat(String text) {

        if(text==null){
            throw new IllegalArgumentException("Input is null");
        }
        return generic_validation(latitude_regex,text);
    }



}
