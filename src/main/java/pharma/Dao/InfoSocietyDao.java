package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoSocietyDao  extends GenericJDBCDao<FieldData,Integer> {
    public InfoSocietyDao( Database database) {
        super("info_society", database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return FieldData.FieldDataBuilder.getbuilder().
               setId(resultSet.getInt(1))
               .setAnagrafica_cliente(resultSet.getString(2))
               .setPartita_iva(resultSet.getString(3)).
                setStreet(resultSet.getString(4)).
               setCap(resultSet.getInt(5)).
               setComune(resultSet.getString(6)).
               setProvince(resultSet.getString(7)).
               setUnit_misure(resultSet.getString(8)).
               build();
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO info_society(anagrafia_cliente,p_iva,street,cap,comune,province,image_path) VALUES(?,?,?,?,?,?,?)";
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
        statement.setString(1,entity.getAnagrafica_cliente());
        statement.setString(2,entity.getPartita_iva());
        statement.setString(3,entity.getStreet());
        statement.setInt(4,entity.getCap());
        statement.setString(5,entity.getProvince());
        statement.setString(6, entity.getUnit_misure());

    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
