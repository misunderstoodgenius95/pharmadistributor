package pharma.config;



import java.sql.*;
import java.util.Properties;

public class Database {

    private Connection conn;
    private static Database instance;


    private Database(Properties properties) {
    try{


     conn = DriverManager.getConnection(properties.getProperty("host"),
             properties.getProperty("username"), properties.getProperty("password"));

    }catch (SQLException e) {

        throw  new RuntimeException(e);
    }
    }

     public static synchronized  Database getInstance(Properties properties) {
        if(properties==null){
            throw new IllegalArgumentException("Error! Propriety cannot be null!");
        }
         if (instance == null) {
             instance = new Database(properties);
         }
         return instance;
     }

    public Boolean execute(String sql)  {
    Boolean result = false;
        try {
        result=conn.createStatement().execute(sql);
        return result;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
    public Connection get_connection(){
        return conn;
    }
    public ResultSet executeQuery(String sql) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Use PreparedStatement for safer query execution (parameterized queries are better)
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            // Log the exception using a proper logging framework
            System.err.println("SQL execution failed: " + e.getMessage());
            throw new RuntimeException("Error executing SQL query", e);
        }
    }
    public void me(){
        System.out.println("me");

    }
    public PreparedStatement execute_prepared_query(String sql)throws SQLException  {

        if(conn==null){
            System.out.println("conn is "+conn);
        throw  new IllegalArgumentException("Connection is null!");
         }
        return conn.prepareStatement(sql);



    }

    /*private Properties getPopertiesDatabase() {
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

     */

    public DatabaseMetaData get_MetaData() throws SQLException {

        try {
           return conn.getMetaData();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean check_exist_table(String table_name) {
        try {
            ResultSet resultSet=conn.getMetaData().getTables(null,null,table_name,null);
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void close(){
    if (conn != null) {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    }





}
