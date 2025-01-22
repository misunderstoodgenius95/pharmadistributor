package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PharmaDao  extends GenericJDBCDao<FieldData,Integer>{
    public PharmaDao(Database database ) {
        super("add_pharma", database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws SQLException {
        FieldData fieldData= FieldData.FieldDataBuilder.getbuilder().
                setAnagrafia_cliente(resultSet.getString("anagrafia_cliente")).
                setPartita_iva(resultSet.getString("sigla")).
                setSigla(resultSet.getString("partita_iva")).setId(resultSet.getInt("id")).build();
        return fieldData;
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {
        try {
            preparedStatement.setInt(1,integer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
