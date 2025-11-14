package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SellerOrderDao  extends GenericJDBCDao<FieldData,Integer> {
    private String table="seller_order";
    private Database database;
    public SellerOrderDao( Database database) {
        super("seller_order", database);
        this.database=database;
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
         return FieldData.FieldDataBuilder.getbuilder().
                setId(resultSet.getInt(1)).
                setForeign_id(resultSet.getInt(2))
                .setSubtotal(resultSet.getDouble(3)).
                 setVat_amount(resultSet.getDouble(4))
                .setTotal(resultSet.getDouble(5)).
                 setElapsed_date(resultSet.getDate(6)).build();
    }

    @Override
    protected String getFindQueryAll() {
       return  "select seller_order.id,farmacia_id,subtotal,vat,total,ragione_sociale,order_date from seller_order\n" +
               "inner join farmacia on farmacia.id=seller_order.farmacia_id; ";
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
    public FieldData findById(Integer integer) {
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(" SELECT * FROM " + table + " WHERE id =? ");


            setFindByIdParameters(preparedStatement,integer);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return mapRow(resultSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected String getInsertQuery() throws Exception {
        return " INSERT INTO "+table+" (farmacia_id,subtotal,vat,total) VALUES(?,?,?,?); ";
    }

    public FieldData addRowSearch(ResultSet resultSet){
        try {
            return FieldData.FieldDataBuilder.getbuilder().setId(
                            resultSet.getInt("id")).
                    setProduction_date(resultSet.getDate("order_date")).
                    setNome_casa_farmaceutica(resultSet.getString("ragione_sociale")).
                    setTotal(resultSet.getDouble("total")).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public FieldData addOrderSearch(ResultSet resultSet) throws SQLException {
        return  FieldData.FieldDataBuilder.getbuilder().setFarmaco_id(resultSet.getInt(1)).setQuantity(resultSet.getInt(2)).setElapsed_date(resultSet.getDate(3)).build();

    }


    public List<FieldData> findByOrders(int id) {
        List<FieldData> list=new ArrayList<>();
        String query="select farmaco as id,quantity,order_date from seller_order\n" +
                "inner join  seller_order_detail sod on seller_order.id = sod.seller_order where farmaco= ? ";

        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(query);
                preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(addOrderSearch(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public List<FieldData> findByRangeBetweenAndRagioneSociale(Date  range_start, Date range_end, String ragione_sociale) {
        List<FieldData> list=new ArrayList<>();
        String query="select * from seller_order " +
                "inner join  farmacia on farmacia.id=farmacia_id " +
                "where order_date between ?  and  ? " +
                "and ragione_sociale like ?;\n";
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(query);
            preparedStatement.setDate(1,range_start);
            preparedStatement.setDate(2,range_end);
            preparedStatement.setString(3,ragione_sociale);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(addRowSearch(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
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
        statement.setInt(1,entity.getId());
        statement.setDouble(2,entity.getSubtotal());
        statement.setDouble(3,entity.getVat_amount());
        statement.setDouble(4,entity.getTotal());


    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
