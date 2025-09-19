package pharma.dao;

import net.postgis.jdbc.PGgeometry;
import org.postgresql.util.PGobject;
import pharma.Model.WarehouseModel;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MagazzinoDao extends GenericJDBCDao<WarehouseModel,Integer> {
    private String table="warehouse";
    public MagazzinoDao( Database database) {
        super("warehouse", database);
    }

    @Override
    protected WarehouseModel mapRow(ResultSet resultSet) throws Exception {


        WarehouseModel warehouseModel =new WarehouseModel();
        warehouseModel.setId(resultSet.getInt(1));
        warehouseModel.setNome(resultSet.getString("nome"));
        warehouseModel.setAddress(resultSet.getString("address"));
         PGobject pGobject=(PGobject) resultSet.getObject("location");
         warehouseModel.setpGgeometry(new PGgeometry(pGobject.getValue()));
        warehouseModel.setComune(resultSet.getString("comune"));
        return warehouseModel;
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
    protected String getInsertQuery() throws Exception {
        return " INSERT INTO "+ table+"(nome, location, address, comune, province ) " +
                " VALUES(?,?,?,?,?); ";
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
    protected void setInsertParameter(PreparedStatement statement, WarehouseModel entity) throws Exception {


        statement.setString(1,entity.getNome());
        statement.setObject(2, entity.getpGgeometry());
        statement.setString(3,entity.getAddress());
        statement.setString(4,entity.getComune());
        statement.setString(5, entity.getProvince());


    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, WarehouseModel entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, WarehouseModel entity) {

    }
}
