package pharma.dao;

import pharma.Model.LotDimensionModel;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LotDimensionDao extends GenericJDBCDao<LotDimensionModel,String> {
     private String table="lot_dimension";
     private Database database;

    public LotDimensionDao(String table_name, Database database) {
        super(table_name, database);
    }


    @Override
    protected LotDimensionModel mapRow(ResultSet resultSet) throws Exception {
        LotDimensionModel lotDimensionModel=new LotDimensionModel();
        lotDimensionModel.setLot_id(resultSet.getString(1));
        lotDimensionModel.setFarmaco_id(resultSet.getInt(2));
        lotDimensionModel.setLength(resultSet.getDouble(3));
        lotDimensionModel.setHeight(resultSet.getDouble(4));
        lotDimensionModel.setDeep(resultSet.getDouble(5));
        lotDimensionModel.setWeight(resultSet.getDouble(6));
        return lotDimensionModel;
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, String s) throws SQLException {

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
    protected void setUpdateParameter(PreparedStatement statement, LotDimensionModel entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, LotDimensionModel entity) {

    }
    @Override
    protected void setInsertParameter(PreparedStatement statement, LotDimensionModel entity) throws Exception {
        statement.setString(1,entity.getLot_id());
        statement.setInt(2,entity.getFarmaco_id());
        statement.setDouble(3,entity.getLength());
        statement.setDouble(4,entity.getHeight());
        statement.setDouble(5,entity.getDeep());
        statement.setDouble(6,entity.getWeight());
    }


    public Optional<LotDimensionModel> findByLots(String lotto_code, int farmaco_id) {
        String sql=" SELECT * FROM  "+table + " WHERE lot_code = ? and farmaco_id=? ";
        return Optional.ofNullable(super.findByParameters(sql, lotto_code, farmaco_id).getFirst());
    }
}
