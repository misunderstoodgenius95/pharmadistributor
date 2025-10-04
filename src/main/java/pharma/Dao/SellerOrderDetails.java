package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SellerOrderDetails  extends GenericJDBCDao<FieldData,Integer> {
private String table_name="seller_order_detail";
    public SellerOrderDetails( Database database) {
        super("seller_order_detail",database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return FieldData.FieldDataBuilder.getbuilder().
                setId(resultSet.getInt(1)).
                setOrder_id(resultSet.getInt(2)).
                setPrice(resultSet.getDouble(3))
                .setQuantity(resultSet.getInt(4))
                .setVat_percent(resultSet.getInt(5))
                .setFarmaco_id(resultSet.getInt(6))
                .setcode(resultSet.getString(7)).build();
    }
    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {

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
     * @param lotto_id
     * @return
     */
    public List<FieldData> findbyProduct(int farmaco_id, String lotto_id){
         return findByParameters("SELECT * FROM "+table_name+" WHERE farmaco = ? and lotto =?  ",farmaco_id,lotto_id);

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
