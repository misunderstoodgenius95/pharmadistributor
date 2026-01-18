package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerOrderDetails  extends GenericJDBCDao<FieldData,Integer> {
private String table_name="seller_order_detail";
private Database database;
    public SellerOrderDetails( Database database) {
        super("seller_order_detail",database);
        this.database=database;
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return FieldData.FieldDataBuilder.getbuilder().
                setId(resultSet.getInt(1)).
                setOrder_id(resultSet.getInt(2)).
                setPrice(resultSet.getDouble(3))
                .setQuantity(resultSet.getInt(4))
                .setVat_percent(resultSet.getInt(5))
                .setFarmaco_id(resultSet.getInt(6)).build();
    }
    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {
        preparedStatement.setInt(1,integer);
    }

    public List<FieldData> findDetailbySellerOrderId(int id){
        List<FieldData> list=new ArrayList<>();
        try {

            PreparedStatement preparedStatement=database.execute_prepared_query("select * from seller_order_detail\n" +
                    "inner join farmaco_all on seller_order_detail.farmaco=farmaco_all.id\n" +
                    " where  seller_order= ? ");
            preparedStatement.setInt(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(  FieldData.FieldDataBuilder.getbuilder().
                        setId(resultSet.getInt(1)).
                        setcode(resultSet.getString(2)).
                        setPrice(resultSet.getDouble("price")).
                        setFarmaco_id(resultSet.getInt("farmaco")).
                        setNome_farmaco(resultSet.getString("nome")).
                        setOrder_id(resultSet.getInt("seller_order")).
                        setQuantity(resultSet.getInt("quantity")).
                        setVat_percent(resultSet.getInt("vat_percent")).
                        setNome_tipologia(resultSet.getString("tipologia")).
                        setUnit_misure(resultSet.getString("misura")).
                        build());
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return  list;


    }
    @Override
    protected String getInsertQuery() throws Exception {
        return "";
    }

    @Override
    protected String getUpdatequery() {
        return "";
    }

    @Override
    protected String getDeletequery() {
        return "";
    }

    /**
     * Find an orders details that contains the current lots
     * @param farmaco_id
     * @return
     */
    public List<FieldData> findbyProduct(int farmaco_id){
         return findByParameter("SELECT * FROM "+table_name+" WHERE farmaco = ?  ",farmaco_id);
    }
    public List<FieldData> findbyOrderId( int order_id){
        List<FieldData> resultList=new ArrayList<>();
        String query="select seller_order_detail.id,(nome,tipologia,misura,qty) as nome, price,seller_order_detail.vat_percent,quantity,seller_order,farmaco from seller_order_detail\n" +
                " inner join farmaco_all on seller_order_detail.farmaco=farmaco_all.id where seller_order= ? ";
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(query );
            preparedStatement.setInt(1,order_id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                resultList.add(
                   FieldData.FieldDataBuilder.getbuilder().
                           setOrder_id(resultSet.getInt(1)).
                           setNome(resultSet.getString(2)).
                           setPrice(resultSet.getDouble(3)).
                           setVat_percent(resultSet.getInt(4)).
                           setQuantity(resultSet.getInt(5)).
                           setForeign_id(resultSet.getInt(6)).
                           setFarmaco_id(resultSet.getInt(7)).
                           build());
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultList;
    }
    @Override
    protected void setInsertParameter(PreparedStatement statement, FieldData entity) throws Exception {

    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
