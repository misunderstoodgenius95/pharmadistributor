package pharma.oldest;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class FileStorage {
    private  Properties properties;
    public FileStorage(Properties properties) {
       this.properties=properties;
    }



    public  HashMap<String,String> getProperties(List<String> keys,FileReader fr)  {
        if(keys ==null || fr ==null){
            throw new IllegalArgumentException("Argument cannot be null!");
        }
        HashMap<String,String> hashMap = new HashMap<>();
        Properties properties=new Properties();
        try {
            properties.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String key : keys) {
            hashMap.put(key, properties.getProperty(key));

        }
        return hashMap;

    }
    public   String getProperty(String key, FileReader fr)  {
        if(key==null || fr ==null){
            throw new IllegalArgumentException("Value key or FileReader null  not excepted");
        }

        try {
            properties.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String propriety= properties.getProperty(key);
        if(propriety==null){
            throw new IllegalArgumentException(key+":"+"is not find!");
        }
        return propriety;
    }
    public void setProperty(String key, String value, FileWriter fileWriter) {
        if(fileWriter==null){
            throw  new IllegalArgumentException("File is null");
        }

        properties.setProperty(key, value);
        try {
            properties.store(fileWriter, "");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setProperties(HashMap<String, String> map, FileWriter fileWriter) {

        properties.putAll(map);

        try {
            properties.store(fileWriter,"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
