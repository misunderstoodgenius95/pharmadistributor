package pharma.config.CatConf;

import com.configcat.ConfigCatClient;
import com.configcat.LogLevel;

import java.lang.classfile.constantpool.DoubleEntry;

public class CatConf {
    private  ConfigCatClient client;
    public CatConf(String config) {
        client = ConfigCatClient.get(config, options -> {
            options.logLevel(LogLevel.INFO);
        });
    }


    public int get_value_integer(String key){
        if(key.isEmpty()){
            throw  new IllegalArgumentException("Key it is null");
        }
        return client.getValue(Integer.class,key,0);


    }
    public double get_value_double(String key){
        if(key.isEmpty()){
            throw  new IllegalArgumentException("Key it is null");
        }
        return client.getValue(Double.class,key,0.0);


    }









}
