package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderDetailDao extends GenericJDBCDao<FieldData,Integer> {
    private Database database;
   private  final String table="purchase_order_detail";
    public PurchaseOrderDetailDao( Database database) {
        super("purchase_order_detail", database);
        this.database=database;
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return  FieldData.FieldDataBuilder.getbuilder().
                setId(resultSet.getInt(1)).
                setcode(resultSet.getString(2)).
                setPrice(resultSet.getDouble("price")).
                setFarmaco_id(resultSet.getInt("farmaco_id")).
                setNome_casa_farmaceutica(resultSet.getString("nome_farmaco")).
                setOrder_id(resultSet.getInt("purchase_order")).
                setQuantity(resultSet.getInt("quantity")).
                setVat_percent(resultSet.getInt("vat_percent")).
                setNome_tipologia(resultSet.getString("tipologia")).
                setUnit_misure(resultSet.getString("misura")).
                build();
    }

    @Override
    protected String getFindQueryAll() {
        return " SELECT * FROM "+table;
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO "+table+" (lotto,farmaco,purchase_order,price,quantity,vat_percent)VALUES(?,?,?,?,?,?); ";
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
        statement.setString(1,entity.getCode());
        statement.setInt(2,entity.getFarmaco_id());
        statement.setInt(3,entity.getOrder_id());
        statement.setDouble(4,entity.getPrice());
        statement.setInt(5,entity.getQuantity());
        statement.setInt(6,entity.getVat_percent());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }



    public List<FieldData> findDetailbyPurchaseOrderId(int id){
        List<FieldData> list=new ArrayList<>();
        try {

            PreparedStatement preparedStatement=database.execute_prepared_query("select * from purchase_order_detail\n" +
                    "inner join farmaco_all on purchase_order_detail.farmaco=farmaco_all.id\n" +
                    " where  purchase_order= ? ");
            preparedStatement.setInt(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(  FieldData.FieldDataBuilder.getbuilder().
                        setId(resultSet.getInt(1)).
                        setcode(resultSet.getString(2)).
                        setPrice(resultSet.getDouble("price")).
                        setFarmaco_id(resultSet.getInt("farmaco")).
                        setOrder_id(resultSet.getInt("purchase_order")).
                        setQuantity(resultSet.getInt("quantity")).
                        setVat_percent(resultSet.getInt("vat_percent")).
                        setNome_farmaco(resultSet.getString("nome_farmaco")).
                        setNome_tipologia(resultSet.getString("tipologia")).
                        setUnit_misure(resultSet.getString("misura")).
                        build());
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  list;


    }


}
