package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PurchaseInvoiceDao extends GenericJDBCDao<FieldData,Integer> {
    private final  String table_name="purchase_invoice";


    public PurchaseInvoiceDao( Database database) {
        super("purchase_invoice", database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return FieldData.FieldDataBuilder.getbuilder().
                setId(resultSet.getInt(1)).
                setInvoice_number(resultSet.getString(2)).
                setProduction_date(resultSet.getDate(3)).
                setPayment_mode(resultSet.getString(4)).
                setSubtotal(resultSet.getDouble(5)).
                setVat_amount(resultSet.getDouble(6)).
                setTotal(resultSet.getDouble(7)).
                setCreated_at(resultSet.getTimestamp(8)).
                setCasa_Farmaceutica(resultSet.getInt(9)).
                setPurchase_order_id(resultSet.getInt(10)).
                build();
    }

    @Override
    protected String getFindQueryAll() {
        return " SELECT * FROM "+table_name;
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO "+table_name+" (invoice_number,issue_date,payment_type,subtotal,vat_amount,total,pharma_id,purchase_order_id) VALUES( ?,?,?,?,?,?,?,?) RETURNING id ";
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
        statement.setString(1,entity.getInvoice_number());
        statement.setDate(2,entity.getProduction_date());
        statement.setString(3,entity.getPayment_mode());
        statement.setDouble(4,entity.getSubtotal());
        statement.setDouble(5,entity.getVat_amount());
        statement.setDouble(6,entity.getTotal());
        statement.setInt(7,entity.getCasa_farmaceutica());
        statement.setInt(8,entity.getPurchase_order_id());



    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
