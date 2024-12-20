package pharma.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
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

    String add_pharma=" CREATE table add_pharma " +
        " ( anagrafia_cliente  VARCHAR(255) UNIQUE , " +
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



}