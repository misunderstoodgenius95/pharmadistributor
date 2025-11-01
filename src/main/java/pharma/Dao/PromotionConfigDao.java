package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PromotionConfigDao extends GenericJDBCDao<FieldData,Integer> {


    public PromotionConfigDao( Database database) {
        super("suggest_promotion_config", database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return FieldData.FieldDataBuilder.getbuilder().
                setMedium_stock_item(resultSet.getInt(1)).
                setMin_day_expire(resultSet.getInt(2)).build();
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {

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
