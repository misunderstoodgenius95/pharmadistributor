package pharma.oldest;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class FileConfig {



    private Properties properties;
    private  FileReader fileReader;
    public FileConfig(FileReader fileReader) {
      this.fileReader = fileReader;
        this.properties = new Properties();

    }
    private void loadProperties(){
        try {
            properties.load(fileReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public String getProperty(String key) {
        loadProperties();
            return properties.getProperty(key);

    }
    public  HashMap<String,String> getProperties(List<String> keys) {
        loadProperties();
        HashMap<String, String> map = new HashMap<>();

        for (String key : keys) {
            map.put(key, properties.getProperty(key));
        }
        return map;
    }
    /*
    public void setProperty(String key, String value, FileWriter fileWriter) {
        properties.setProperty(key, value);
        saveProperties(fileWriter);

    }

    private static void saveProperties(FileWriter writer) {
        try {
            FileConfig.properties.store(writer, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public  void setProperties(HashMap<String, String> map, FileWriter fileWriter) {
        properties.putAll(map);
        saveProperties(fileWriter);
    }



     */

}
