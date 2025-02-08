package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LottiDao extends GenericJDBCDao<FieldData,Integer> {
    private final  String table;
    public LottiDao( Database database, String tableName) {
        super(tableName, database);
        this.table = tableName;
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return null;
    }

    @Override
    protected String getFindQueryAll() {
        return "";
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO "+table+" (farmaco,production_date,elapsed_date,price,quantity) VALUES(?,?,?,?,?)  ";
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
    statement.setInt(1,entity.getTipologia());
    statement.setDate(2,entity.getProduction_date());
    statement.setDate(3,entity.getElapsed_date());
    statement.setDouble(4,entity.getPrice());
    statement.setInt(5,entity.getQuantity());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
