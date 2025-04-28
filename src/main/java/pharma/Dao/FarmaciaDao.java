package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FarmaciaDao  extends  GenericJDBCDao<FieldData,Integer> {
     private  final String  table="farmacia";
    public FarmaciaDao( Database database) {
        super("farmacia", database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
       return   FieldData.FieldDataBuilder.getbuilder().
               setId(resultSet.getInt(1))
               .setAnagrafica_cliente(resultSet.getString(2))
               .setPartita_iva(resultSet.getString(3))
               .setStreet(resultSet.getString(4))
               .setCap(resultSet.getInt(5))
               .setComune(resultSet.getString(6))
               .setProvince(resultSet.getString(7)).build();
    }



    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return " INSERT INTO "+ table+ " (ragione_sociale,p_iva, street, cap, comune,province) VALUES( ?,?, ?,?,?,?) ;";
    }
    @Override
    protected void setInsertParameter(PreparedStatement statement, FieldData entity) throws Exception {
        statement.setString(1,entity.getAnagrafica_cliente());
        statement.setString(2,entity.getPartita_iva());
        statement.setString(3,entity.getStreet());
        statement.setInt(4,entity.getCap());
        statement.setString(5,entity.getComune());
        statement.setString(6,entity.getProvince());
    }

    @Override
    protected String getUpdatequery() {
        return "";
    }

    @Override
    protected String getDeletequery() {
        return "";
    }



    public String   buildQueryasParameter(String parameter){
        String query=" SELECT * FROM  "+ table+" WHERE  ";
        switch(parameter){
            case "comune"-> query= query+" comune = ?  ";
            case "partita_iva"-> query=query+ " partita_iva = ? ";
            case "ragione_sociale"-> query=query+ " ragione_sociale = ? ";
            default-> throw new IllegalArgumentException("Argument not present!");
        }
        return  query;

    }


    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
