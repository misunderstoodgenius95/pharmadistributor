package pharma.dao;

import algoWarehouse.ShelvesAssigment;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LotAssigmentShelvesDao extends GenericJDBCDao<ShelvesAssigment, Integer> {
    public LotAssigmentShelvesDao(String table_name, Database database) {
        super(table_name, database);
    }

    @Override
    protected ShelvesAssigment mapRow(ResultSet resultSet) throws Exception {

     return new ShelvesAssigment(
          resultSet.getInt("id"),
              resultSet.getString("shelf_code"),
              resultSet.getInt("quantity"),
              resultSet.getInt("shelves_level"),
              resultSet.getInt("magazzino_id")
      );
    }

    public List<ShelvesAssigment> findByWarehouse( int warehouse_id) {
        String query="SELECT * FROM lot_assigment_shelves WHERE warehouse_id = ? ";
        return super.findByParameter(query, warehouse_id);
    }

    public List<ShelvesAssigment> findByLots(String lots){
       String query=" select * from lot_assigment_shelves\n " +
               " inner join lot_assignment on lot_assignment.id=lot_assigment_shelves.id\n" +
               " inner join farmaco_all on lot_assigment "+
               " where lot_code = ? " +
               "   ";
       return  super.findByParameter(query,lots);


    }
    public List<ShelvesAssigment> findByShelf(String shelf_code){
        String query=" select * from lot_assigment_shelves\n " +
                " inner join lot_assignment on lot_assignment.id=lot_assigment_shelves.id\n" +
                " where shelf_code = ? ";
        return  super.findByParameter(query,shelf_code);


    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO lot_assigment_shelves (shelf_code,shelves_level,warehouse_id,quantity)" +
                " VALUES( ?,?,?,?)";
    }

    @Override
    protected String getUpdatequery() {
        return "";
    }

    @Override
    protected String getDeletequery() {
        return "";
    }

    @Override
    protected void setInsertParameter(PreparedStatement statement, ShelvesAssigment entity) throws Exception {

    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, ShelvesAssigment entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, ShelvesAssigment entity) {

    }
}
