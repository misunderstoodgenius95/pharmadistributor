package pharma.dao;

import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;
import pharma.Model.FieldData;
import pharma.config.Database;
import pharma.config.Utility;

import java.rmi.AccessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DetailDao extends GenericJDBCDao<FieldData,Integer> {
    private  String table_name="";

    public DetailDao( Database database) {
        super("", database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        if(table_name.isEmpty()){
            throw new AccessException("Table");
        }
        if(table_name.equals(Utility.Misura)) {
        return FieldData.FieldDataBuilder.getbuilder().setId(resultSet.getInt(1)).setUnit_misure(resultSet.getString(3))
                .setQuantity(resultSet.getInt(2)).build();
        }else{
            return FieldData.FieldDataBuilder.getbuilder().setId(resultSet.getInt(1)).setNome(resultSet.getString(2)).build();
        }


    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    @Override
    protected String getInsertQuery() throws  Exception  {
        if(table_name.isEmpty()){
            throw new AccessException("Table");

        }
        if(table_name.equals(Utility.Misura)) {
            return " INSERT INTO " + table_name+" (quantity,unit) VALUES(?,?); ";
        }
        else{
            return " INSERT INTO "+table_name+" (nome) VALUES(?); ";
        }
    }


    @Override
    protected void setInsertParameter(PreparedStatement statement, FieldData entity) throws AccessException {
        if(table_name.isEmpty()){
            throw  new AccessException("table_name is empty");
        }
        if(table_name.equals(Utility.Misura)){
            try {
                statement.setInt(1,entity.getQuantity());
                statement.setString(2,entity.getUnit_misure());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                statement.setString(1,entity.getNome());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }












    @Override
    public FieldData findById(Integer integer) {
        return  FieldData.FieldDataBuilder.getbuilder().build();
    }

    @Override
    protected String getFindQueryAll() {
        return " SELECT * FROM "+ table_name ;
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {

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
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }


}
