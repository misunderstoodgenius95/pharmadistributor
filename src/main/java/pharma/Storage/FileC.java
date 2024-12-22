package pharma.Storage;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class FileC {
    private  FileC(){


    }
    private static Properties load_properties(FileReader fileReader) {
        Properties properties = new Properties();
        try {
            properties.load(fileReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
    public static HashMap<String,String> getProperties(List<String> keys,FileReader fr)  {

        HashMap<String,String> hashMap = new HashMap<>();
        Properties properties=load_properties(fr);
        for (String key : keys) {
                hashMap.put(key, properties.getProperty(key));

        }
      return hashMap;

    }
    public static  String getProperty(String key, FileReader fr)  {
        Properties properties=load_properties(fr);

        String propriety= properties.getProperty(key);
        if(propriety==null){
            throw new IllegalArgumentException(key+"is not find!");
        }
        return propriety;
    }



}
