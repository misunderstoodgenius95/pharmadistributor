package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LottiDao extends GenericJDBCDao<FieldData,Integer> {
    private final  String table;
    public LottiDao( Database database, String tableName) {
        super(tableName, database);
        this.table = tableName;
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
       return FieldData.FieldDataBuilder.getbuilder().
               setLotto_id(resultSet.getString("id")).
               setProduction_date(resultSet.getDate("production_date")).
               setElapsed_date(resultSet.getDate("elapsed_date")).
               setQuantity(resultSet.getInt("quantity")).
               setNome(resultSet.getString("nome")).
               setNome_tipologia(resultSet.getString("tipologia")).
               setUnit_misure(resultSet.getString("misura")).
               setPrice(resultSet.getDouble("price")).
               setVat_percent(resultSet.getInt("vat_percent")).
               setNome_casa_farmaceutica(resultSet.getString("casa_farmaceutica"))
        .build();
    }

    @Override
    protected String getFindQueryAll() {
        return " select * from lotto\n " +
                " inner join farmaco_all on farmaco_all.id=Lotto.farmaco; ";
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO "+table+" (id,farmaco,production_date,elapsed_date,price,vat_percent,quantity) VALUES(?,?,?,?,?,?,?)  ";
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
    statement.setString(1,entity.getLotto_id());
    statement.setInt(2,entity.getTipologia());
    statement.setDate(3,entity.getProduction_date());
    statement.setDate(4,entity.getElapsed_date());
    statement.setDouble(5,entity.getPrice());
    statement.setInt(6,entity.getVat_percent());
    statement.setInt(7,entity.getQuantity());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
