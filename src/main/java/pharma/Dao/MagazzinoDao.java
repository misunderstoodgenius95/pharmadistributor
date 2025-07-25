package pharma.dao;

import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MagazzinoDao extends GenericJDBCDao<FieldData,Integer> {
    private final String table ="warehouse";
    public MagazzinoDao( Database database) {
        super("warehouse", database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return FieldData.FieldDataBuilder.getbuilder().
                setId(resultSet.getInt("id")).
                setNome(resultSet.getString("nome")).
                setStreet(resultSet.getString("address")).
                setComune(resultSet.getString("comune")).
                setLocation((PGgeometry) resultSet.getObject("location"))
                .build();

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
    protected void setInsertParameter(PreparedStatement statement, FieldData entity) throws Exception {
        Point point = new Point(entity.getLongitude(), entity.getLatitude());
        PGgeometry geom = new PGgeometry(point);
        statement.setString(1,entity.getNome());
        statement.setObject(2, geom);
        statement.setString(3,entity.getStreet());
        statement.setString(4,entity.getComune());
        statement.setString(5, entity.getProvince());

    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
