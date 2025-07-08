package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LotDimension extends LottiDao {
     private String table="lot_dimension";
     private Database database;
    public LotDimension(Database database) {
        super(database, "lot_dimension");
        this.database=database;
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return null;
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return " INSERT INTO "+table+" (lot_code, farmaco_id, length, height, deep, weight)Values(?,?,?,?,?,?); ";
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
    protected void setInsertParameter(PreparedStatement statement, FieldData entity) throws Exception {
        statement.setString(1,entity.getCode());
        statement.setInt(2,entity.getFarmaco_id());
        statement.setDouble(3,entity.getLunghezza());
        statement.setDouble(4,entity.getAltezza());
        statement.setDouble(5,entity.getProfondita());
        statement.setInt(6,entity.getCapacity());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
