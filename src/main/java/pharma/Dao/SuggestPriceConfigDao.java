package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuggestPriceConfigDao extends GenericJDBCDao<FieldData,Integer> {
    private Database database;
    private  String table_name;
    public SuggestPriceConfigDao(Database database) {
        super("suggest_price_config", database);
        this.database=database;
        table_name="suggest_price_config";
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return FieldData.FieldDataBuilder.getbuilder().
                setFarmaco_id(resultSet.getInt(1))
                .setMedium_day(resultSet.getDouble(2)).
                setMedium_lots(resultSet.getDouble(3)).build();

    }





    @Override
    public FieldData findById(Integer integer) {
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(" SELECT * FROM " + table_name + " WHERE farmaco_id =? ");


            setFindByIdParameters(preparedStatement,integer);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){

                return mapRow(resultSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {
        preparedStatement.setInt(1,integer);
    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "";
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

    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
