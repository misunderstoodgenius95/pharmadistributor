package pharma.dao;


import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PurchaseCreditNoteDetailDao extends  GenericJDBCDao<FieldData,Integer> {
    private  final String  table_name="purchase_credit_note_details";
    private Database database;
    public PurchaseCreditNoteDetailDao(Database database) {
        super("purchase_credit_note_details", database);
        this.database=database;
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
        return "INSERT INTO "+table_name+"( credit_note_id,order_details,quantity,price,vat_percent) VALUES (?,?,?,?,?); ";
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
        statement.setInt(2,entity.getOrder_id());
        statement.setInt(3,entity.getQuantity());
        statement.setDouble(4,entity.getPrice());
        statement.setInt(5,entity.getVat_percent());

    }

    public List<FieldData> findDetailbyCreditNoteId(int id){
        List<FieldData> list=new ArrayList<>();
        try {

            PreparedStatement preparedStatement=database.execute_prepared_query("select * from purchase_credit_note inner join purchase_credit_note_details\n" +
                    "    on purchase_credit_note.id = purchase_credit_note_details.credit_note_id\n" +
                    "where purchase_credit_note.id = ?  ");
            preparedStatement.setInt(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapRow(resultSet));
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  list;


    }



    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
