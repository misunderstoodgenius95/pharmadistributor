package pharma.dao;

import pharma.Service.Report.UserFormula;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomFormulaDao extends GenericJDBCDao<UserFormula,Integer> {
    private String table="custom_formule";
    public CustomFormulaDao(String table_name, Database database) {
        super("custom_formule", database);
    }

    @Override
    protected UserFormula mapRow(ResultSet resultSet) throws Exception {
      String formula_name=resultSet.getString("formula_name");
      String content_formula=resultSet.getString("content_formula");
         return new UserFormula(formula_name,content_formula);
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {
    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO "+table+"(formula_name,content_formula) VALUES(?,?)";
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
    protected void setInsertParameter(PreparedStatement statement, UserFormula entity) throws Exception {

    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, UserFormula entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, UserFormula entity) {

    }
}
