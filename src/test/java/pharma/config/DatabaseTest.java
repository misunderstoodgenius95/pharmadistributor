package pharma.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private static Database db;
@BeforeAll
static void setupDatabase() {
    db=Database.getInstance();

}

@Test
    void create_table(){

    String add_pharma=" CREATE table pharma " +
            " ( id integer, " +
            "anagrafia_cliente  VARCHAR(255) UNIQUE NOT NULL , " +
        "  sigla  VARCHAR(255) PRIMARY KEY , " +
        "  partita_iva  VARCHAR(255) NOT NULL UNIQUE   " +
        " ); ";
db.execute(add_pharma);
    }


    @Test
    public void testTableCreation() throws Exception {
        // Check if the table exists

        String query = " SELECT table_name " +
                 " FROM information_schema.tables" +
                " WHERE table_schema = 'public'" +
                "and table_name = 'add_pharma' ";
         ResultSet rs=db.execute_query(query);
while(rs.next()){
    System.out.println(rs.getString(1));
}
            //assertTrue(rs.next());
        }
        @Test
    public void find() throws SQLException {

    String query="SELECT * FROM pharma";
    ResultSet rs=db.execute_query(query);

if(rs.next()){
        System.out.println(rs.getString(1));
        System.out.println(rs.getString(2));
        System.out.println(rs.getString(3));


    }else{
    System.out.println("No add pharma");
    }
        }
        @Test
    public void delete() throws SQLException {
    String query="DROP TABLE pharma";
db.execute(query);

Assertions.assertFalse(db.check_exist_table("pharma"));
        }
        @Test
    public void show_table_structure() throws SQLException {

            try (ResultSet columns = db.get_MetaData().getColumns(null, null, "pharma", null)) {
                System.out.println("Structure of table:");
                System.out.println("----------------------------------------");
                System.out.printf("%-20s %-20s %-10s %n", "COLUMN_NAME", "DATA_TYPE", "IS_NULLABLE");
                System.out.println("----------------------------------------");

                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String dataType = columns.getString("TYPE_NAME");
                    String isNullable = columns.getString("IS_NULLABLE");

                    System.out.printf("%-20s %-20s %-10s %n", columnName, dataType, isNullable);
                }
        }catch(Exception e){
            e.printStackTrace();}
        }



}