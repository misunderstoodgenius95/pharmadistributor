package pharma.dao;

import algoWarehouse.ShelvesCapacity;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShelvesDao extends GenericJDBCDao<ShelvesCapacity,Integer> {

    private String table="warehouse_shelves";
    public ShelvesDao( Database database) {
        super("warehouse_shelves", database);
    }

    @Override
    protected ShelvesCapacity mapRow(ResultSet resultSet) throws Exception {
        return null;
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return " INSERT INTO "+table+" (warehouse_shelf,num_shelve,length_occupied,deep_occupied) VALUES( ?,?,?,?) ";
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
    protected void setInsertParameter(PreparedStatement statement, ShelvesCapacity entity) throws Exception {
        statement.setString(1,entity.getWarehouse_shelf());
        statement.setInt(2,entity.getNum_shelf());
        statement.setDouble(3,entity.getOccupied_length());
        statement.setDouble(4,entity.getOccupied_deep());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, ShelvesCapacity entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, ShelvesCapacity entity) {

    }
}
