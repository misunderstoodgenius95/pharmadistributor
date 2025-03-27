package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PurchaseCreditNoteDao extends  GenericJDBCDao<FieldData,Integer> {
    private  final String  table_name="purchase_credit_note";
    public PurchaseCreditNoteDao(Database database) {
        super("purchase_credit_note", database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return null;
    }

    @Override
    protected String getFindQueryAll() {
        return "";
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO " + table_name+ " (credit_note_number,invoice_id, issue_data,motivo,pharma_id,subtotal,vat_amount,total) VALUES (?,?,?,?,?,?,?,?)  RETURNING id ";
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
        statement.setString(1,entity.getCredit_note_number());
        statement.setInt(2,entity.getInvoice_id());
        statement.setDate(3,entity.getProduction_date());
        statement.setString(4,entity.getMotive());
        statement.setInt(5,entity.getCasa_farmaceutica());
        statement.setDouble(6,entity.getSubtotal());
        statement.setDouble(7,entity.getVat_amount());
        statement.setDouble(8,entity.getTotal());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
