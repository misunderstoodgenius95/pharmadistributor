package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerOrderDao  extends GenericJDBCDao<FieldData,Integer> {
    private String table="seller_order";
    public SellerOrderDao( Database database) {
        super("seller_order", database);
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
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {
        try {
            preparedStatement.setInt(1,integer);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return " INSERT INTO "+table+" (farmacia_id,subtotal,vat,total) VALUES(?,?,?,?); ";
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
