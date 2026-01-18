package pharma.dao;

import pharma.Model.ShelfInfo;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Warehouse_shelf extends GenericJDBCDao<ShelfInfo,String> {
    public Warehouse_shelf(String table_name, Database database) {
        super(table_name, database);
    }

    @Override
    protected ShelfInfo mapRow(ResultSet resultSet) throws Exception {
       return ShelfInfo.ShelfInfoBuilder.get_builder().build();
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, String s) throws SQLException {

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
    protected void setInsertParameter(PreparedStatement statement, ShelfInfo entity) throws Exception {

    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, ShelfInfo entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, ShelfInfo entity) {

    }
}
