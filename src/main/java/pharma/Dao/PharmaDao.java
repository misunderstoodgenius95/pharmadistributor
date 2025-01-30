package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.Database;

import javax.lang.model.element.NestingKind;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PharmaDao  extends GenericJDBCDao<FieldData,Integer>{
   private static final String table="pharma";
    public PharmaDao(Database database) throws SQLException {
        super(table, database);


    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws SQLException {
        System.out.println(resultSet);
        FieldData fieldData= FieldData.FieldDataBuilder.getbuilder().
                setAnagrafica_cliente(resultSet.getString(2)).
                setPartita_iva(resultSet.getString(4)).
                setSigla(resultSet.getString(3)).setId(resultSet.getInt(1)).build();
        return fieldData;
    }

    @Override
    protected String getFindQueryAll() {
        return "SELECT * FROM "+table;
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {
        try {
            preparedStatement.setInt(1,integer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO "+ table+" (anagrafica_cliente,sigla,partita_iva) VALUES (?,?,?)  ";
    }

    @Override
    protected String getUpdatequery() {
        return " UPDATE "+ table +"SET anagrafica_cliente= ? WHERE id= ? ";
    }

    @Override
    protected String getDeletequery() {
        return "";
    }

    @Override
    protected void setInsertParameter(PreparedStatement statement, FieldData entity) {
        try {
            statement.setString(1,entity.getAnagrafica_cliente());
            statement.setString(2,entity.getSigla());
            statement.setString(3,entity.getPartita_iva());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {
        try {
            statement.setString(1,entity.getAnagrafica_cliente());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }


}
