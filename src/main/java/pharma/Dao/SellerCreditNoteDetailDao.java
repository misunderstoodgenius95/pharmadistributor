package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerCreditNoteDetailDao extends GenericJDBCDao<FieldData,Integer> {
       private String table_name="seller_credit_note_detail";

    public SellerCreditNoteDetailDao( Database database) {
        super("seller_credit_note_detail", database);
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return null;
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO "+table_name+" (credit_note_id,price,vat_percent,quantity,farmaco) VALUES(?,?,?,?,?)";
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
        statement.setInt(1,entity.getInvoice_id());
        statement.setDouble(2,entity.getPrice());
        statement.setDouble(3,entity.getVat_percent());
        statement.setInt(4,entity.getQuantity());
        statement.setInt(5,entity.getFarmaco_id());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }



    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}
