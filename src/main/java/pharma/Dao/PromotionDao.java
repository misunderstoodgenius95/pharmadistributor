package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionDao  extends GenericJDBCDao<FieldData,Integer> {
    private String table;
    private Database database;
    public PromotionDao( Database database) {
        super("promotion", database);
        this.table="promotion";
        this.database=database;
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return FieldData.FieldDataBuilder.getbuilder().
                setNome(resultSet.getString("nome")).
                setNome_tipologia(resultSet.getString("tipologia")).
                setUnit_misure(resultSet.getString("misura")).
                setQuantity(resultSet.getInt("qty")).
                setPrice(resultSet.getDouble("price")).
                setProduction_date(resultSet.getDate("range_time_start")).
                setElapsed_date(resultSet.getDate("range_time_end")).
                setDiscount_value(resultSet.getInt("discount_value")).
                build();
    }
    @Override
    protected    String  getFindQueryAll() {
     return " select * from promotion \n " +
             " inner join farmaco_all \n "  +
             "    on farmaco_all.id=farmaco\n  " +
             " inner join seller_price sp on promotion.farmaco = sp.farmaco;";
    }
    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {

    }

    public List<FieldData> getProductNoPromotionActive(){

        String query="select * from seller_price " +
                "inner join  farmaco_all on seller_price.farmaco=farmaco_all.id " +
                "where not exists(select 1 from promotion where seller_price.farmaco=promotion.farmaco);";

        ResultSet resultSet=database.executeQuery(query);
        List<FieldData> resultList=new ArrayList<>();
        try {
            while(resultSet.next()){
                resultList.add(  FieldData.FieldDataBuilder.getbuilder().
                        setId(resultSet.getInt(1))
                        .setPrice(resultSet.getDouble(2))
                        .setElapsed_date(resultSet.getDate(3))
                        .setForeign_id(resultSet.getInt(4))
                        .setNome(resultSet.getString("nome")).
                        setNome_tipologia(resultSet.getString("tipologia")).
                        setUnit_misure(resultSet.getString("misura")).
                        setQuantity(resultSet.getInt("qty")).build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  resultList;

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return " INSERT INTO " + table + " (discount_value, range_time_end, farmaco)  VALUES(?, ?, ?);   ";
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
        statement.setInt(1, entity.getDiscount_value());
        statement.setDate(2, entity.getElapsed_date());
        statement.setInt(3, entity.getForeign_id());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
