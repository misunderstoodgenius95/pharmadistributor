package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuggestPriceDao extends GenericJDBCDao<FieldData,Integer> {
    private Database database;
    private String table_name;
    public SuggestPriceDao( Database database) {
        super("seller_price", database);
        this.database=database;
        table_name="seller_price";
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return  FieldData.FieldDataBuilder.getbuilder().
                setId(resultSet.getInt(1))
                .setPrice(resultSet.getDouble(2))
                .setElapsed_date(resultSet.getDate(3)).
                setForeign_id(resultSet.getInt(4)).
                setNome(resultSet.getString("nome")).
                setNome_tipologia(resultSet.getString("tipologia")).
                setUnit_misure(resultSet.getString("misura")).
                setQuantity(resultSet.getInt("qty"))


        .build();
    }


    @Override
    protected String getFindQueryAll() {
        return "select * from seller_price\n" +
                "inner join farmaco_all on farmaco=farmaco_all.id;";
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {

    }
    /*
        return " " + object.getNome() + " " + object.getNome_tipologia() + " " + object.getUnit_misure() + " " + object.getQuantity();
     */
    public List<FieldData> findNotExistPrice(){
        List<FieldData> list=new ArrayList<>();
        String query="select nome,tipologia,misura,qty,id from farmaco_all\n" +
                " where not exists(\n " +
                "    select 1 from  seller_price\n " +
                "             where farmaco_all.id=seller_price.farmaco\n" +
                " ) ";
         ResultSet resultSet=database.executeQuery(query);
        try {
            while(resultSet.next()) {
                list.add(FieldData.FieldDataBuilder.getbuilder().
                   setNome(resultSet.getString(1)).
                                setNome_tipologia(resultSet.getString(2)).
                                setUnit_misure(resultSet.getString(3)).
                        setQuantity(resultSet.getInt(4)).
                        setId(resultSet.getInt(5)).build());


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;


    }






    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO "+table_name+" (price,farmaco) VALUES(?,?); ";
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
        statement.setDouble(1,entity.getPrice());
        statement.setInt(2,entity.getForeign_id());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
