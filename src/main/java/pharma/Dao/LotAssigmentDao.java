package pharma.dao;

import algo.LotAssigment;
import pharma.config.database.Database;

import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LotAssigmentDao extends GenericJDBCDao<LotAssigment,Integer>{
    private String table="lot_assigment";
    public LotAssigmentDao(String table_name, Database database) {
        super(table_name, database);
    }

    @Override
    protected LotAssigment mapRow(ResultSet resultSet) throws Exception {

        return new LotAssigment(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getInt(3)
        );
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {
        preparedStatement.setInt(1, integer);
    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO "+table+" (farmaco_id,lot_code,request_quantity) VALUES(?,?,?);  " ;
    }
    @Override
    protected void setInsertParameter(PreparedStatement statement, LotAssigment entity) throws Exception {
        statement.setInt(1,entity.getFarmaco_id());
        statement.setString(2,entity.getLot_code());
        statement.setInt(3,entity.getQuantity_request());
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
    protected void setUpdateParameter(PreparedStatement statement, LotAssigment entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, LotAssigment entity) {

    }


}
