package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerCreditNoteDao extends GenericJDBCDao<FieldData,Integer> {
    String table="seller_credit_note";
    public SellerCreditNoteDao( Database database) {
        super("seller_credit_note", database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
         return FieldData.FieldDataBuilder.getbuilder().
                setId(resultSet.getInt(1)).
                setForeign_id(resultSet.getInt(2)).
                setVat_amount(resultSet.getInt(3)).
                setSubtotal(resultSet.getInt(4)).
                setTotal(resultSet.getInt(5)).
                setProduction_date(resultSet.getDate(6)).build();
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return " INSERT INTO "+table+" (invoice_number,vat,subtotal,total) VALUES(?,?,?,?) RETURNING id ; ";
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
        statement.setInt(1,entity.getForeign_id());
        statement.setDouble(2,entity.getVat_amount());
        statement.setDouble(3,entity.getSubtotal());
        statement.setDouble(4,entity.getTotal());

    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
