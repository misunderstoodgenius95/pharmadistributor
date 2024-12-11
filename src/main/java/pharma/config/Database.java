package pharma.config;

import javax.xml.transform.Result;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Database {

private Connection conn;

public Database() {
    try{

     Properties properties=getPopertiesDatabase();
     conn = DriverManager.getConnection(properties.getProperty("host"),
             properties.getProperty("username"), properties.getProperty("password"));

    }catch (SQLException e) {

        e.printStackTrace();
    }
}


public void execute(String sql)  {
    try {
        conn.createStatement().execute(sql);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

    public ResultSet execute_query(String sql)  {
        try {
          return   conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


private Properties getPopertiesDatabase() {
    FileReader reader = null;
    try {
        reader = new FileReader("database.properties");
        Properties props = new Properties();
        props.load(reader);
        reader.close();
        return props;
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
public void close(){
    try {
        conn.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }


}





}
