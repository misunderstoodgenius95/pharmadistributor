package Storage;

import java.io.*;
import java.util.Properties;

public class StorageToken {

    private  static final String file="config.properties";



    public  static    void  store_token(String token){
    Properties properties=new Properties();
        try {
        FileWriter fileWriter=new FileWriter(file);
            properties.setProperty("token",token);
            properties.store(fileWriter,"info properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
public  static  String get_token(){
    Properties properties=new Properties();
    try {
       FileReader fileWriter=new FileReader(file);
        properties.load(fileWriter);
        return properties.getProperty("token");


    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}

}
