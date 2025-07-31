package pharma.dao;

import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import pharma.Model.FieldData;
import pharma.Model.Warehouse;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MagazzinoDao extends GenericJDBCDao<Warehouse,Integer> {
    private String table="warehouse";
    public MagazzinoDao( Database database) {
        super("warehouse", database);
    }

    @Override
    protected Warehouse mapRow(ResultSet resultSet) throws Exception {


        Warehouse warehouse=new Warehouse();
        warehouse.setId(resultSet.getInt(1));
        warehouse.setNome(resultSet.getString("nome"));
        warehouse.setAddress(resultSet.getString("address"));
        warehouse.setComune(resultSet.getString("comune"));
        warehouse.setpGgeometry((PGgeometry) resultSet.getObject("location"));
        return warehouse;
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
    protected void setInsertParameter(PreparedStatement statement, Warehouse entity) throws Exception {


        statement.setString(1,entity.getNome());
        statement.setObject(2, entity.getpGgeometry());
        statement.setString(3,entity.getAddress());
        statement.setString(4,entity.getComune());
        statement.setString(5, entity.getProvince());


    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, Warehouse entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, Warehouse entity) {

    }
}
