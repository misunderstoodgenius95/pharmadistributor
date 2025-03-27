package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PurchaseCreditNoteOrder extends  GenericJDBCDao<FieldData,Integer> {
    private  final  String table_name="purchase_credit_note_order";
    public PurchaseCreditNoteOrder( Database database) {
        super("purchase_credit_note_order", database);
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
/*
      id int PRIMARY KEY generated always as identity,
    credit_note_id int  not null REFERENCES purchase_credit_note(id),
    order_id int not null  references  purchase_order(id),
    subtotal double precision not null,
    vat_amount double precision not null,
    total double precision not null

 */
    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO "+table_name +" (credit_note_id,order_id,subtotal,vat_amount,total) VALUES( ?,?,?,?,?); ";
    }

    @Override
    protected String getUpdatequery() {
        return "";
    }

    @Override
    protected String getDeletequery() {
        return "";
    }
    /*



    credit_note_id int  not null REFERENCES purchase_credit_note(id),
    order_id int not null  references  purchase_order(id),
    subtotal double precision not null,
    vat_amount double precision not null,
    total double precision not null
);

     */
    @Override
    protected void setInsertParameter(PreparedStatement statement, FieldData entity) throws Exception {
        statement.setInt(1,entity.getInvoice_id());
        statement.setInt(2,entity.getPurchase_order_id());
        statement.setDouble(3,entity.getSubtotal());
        statement.setDouble(4,entity.getVat_amount());
        statement.setDouble(5,entity.getTotal());



    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
