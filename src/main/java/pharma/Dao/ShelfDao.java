package pharma.dao;

import com.auth0.json.mgmt.users.ProfileData;
import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShelfDao extends GenericJDBCDao<FieldData,String> {
    private final  String table="warehouse_shelf";
    public ShelfDao( Database database) {
        super("warehouse_shelf", database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return FieldData.FieldDataBuilder.getbuilder().
                setcode(resultSet.getString(1)).
                setId(resultSet.getInt(2)).
                setLunghezza(resultSet.getDouble(3)).
                setAltezza(resultSet.getDouble(4)).
                setProfondita(resultSet.getDouble(5)).
                setSpessore(resultSet.getDouble(5)).
                setNum_rip(resultSet.getInt(6)).build();
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, String string) {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return " INSERT INTO "+table+" (code,warehouse_id,length,height,depth,shelf_thickness,num_level,capacity) " +
                " VALUES( ?,?,?,?,?,?,?,?) ";
    }

    @Override
    protected String getUpdatequery() {
        return " ";
    }

    @Override
    protected String getDeletequery() {
        return " ";
    }

    @Override
    protected void setInsertParameter(PreparedStatement statement, FieldData entity) throws Exception {
        statement.setString(1,entity.getCode());
        statement.setInt(2,entity.getId());
        statement.setDouble(3,entity.getLunghezza());
        statement.setDouble(4,entity.getAltezza());
        statement.setDouble(5,entity.getProfondita());
        statement.setDouble(6,entity.getSpessore());
        statement.setInt(7,entity.getNum_rip());
        statement.setInt(8,entity.getCapacity());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
