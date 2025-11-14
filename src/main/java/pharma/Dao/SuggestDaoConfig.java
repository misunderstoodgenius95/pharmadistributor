package pharma.dao;

import pharma.config.database.Database;
import pharma.formula.suggest.Model.SuggestConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuggestDaoConfig extends GenericJDBCDao<SuggestConfig,Integer> {
    public SuggestDaoConfig(String table_name, Database database) {
        super(table_name, database);
    }

    @Override
    protected SuggestConfig mapRow(ResultSet resultSet) throws Exception {
        return null;
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
    protected void setInsertParameter(PreparedStatement statement, SuggestConfig entity) throws Exception {

    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, SuggestConfig entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, SuggestConfig entity) {

    }
}
