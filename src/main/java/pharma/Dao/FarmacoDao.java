package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FarmacoDao  extends GenericJDBCDao<FieldData,Integer> {
    private final  String table;
    private  Database database;
    public FarmacoDao(String table_name, Database database) {
        super(table_name, database);
        this.table=table_name;
        this.database=database;
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return  FieldData.FieldDataBuilder.getbuilder().
                setId(resultSet.getInt(1)).
                setNome(resultSet.getString(2)).
                setDescription(resultSet.getString(3)).
                setNome_categoria((resultSet.getString(4))).
                setNome_tipologia(resultSet.getString(5)).
                setUnit_misure(resultSet.getString(6)).
                setNome_principio_attivo(resultSet.getString(7)).
                setNome_casa_farmaceutica(resultSet.getString(8)).build();

    }

    @Override
    protected String getFindQueryAll() {
        return " SELECT * FROM farmaco_all";
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
        return " INSERT INTO "+table+" (nome,descrizione,categoria,tipologia,misura,principio_attivo,casa_farmaceutica) " +
                " VALUES( ?,?,?,?,?,?,?) ; ";
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
        statement.setString(1,entity.getNome());
        statement.setString(2,entity.getDescription());
        statement.setInt(3,entity.getCategoria());
        statement.setInt(4,entity.getTipologia());
        statement.setInt(5,entity.getMisure());
        statement.setInt(6,entity.getPrincipio_attivo());
        statement.setInt(7,entity.getCasa_farmaceutica());
    }



    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }


}
