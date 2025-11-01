package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerInvoiceDao extends GenericJDBCDao<FieldData,Integer> {
    private Database database;
    public SellerInvoiceDao( Database database) {
        super("seller_invoice", database);
        this.database=database;
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return  FieldData.FieldDataBuilder.getbuilder().
                setId(resultSet.getInt(1)).
                setCasa_Farmaceutica(resultSet.getInt(2)).
                setOrder_id(resultSet.getInt(3)).
                setPayment_mode(resultSet.getString(4)).
                setVat_amount(resultSet.getInt(5)).
                setSubtotal(resultSet.getDouble(6)).
                setTotal(resultSet.getDouble(7)).
                setElapsed_date(resultSet.getDate(8)).
                setNome_casa_farmaceutica(resultSet.getString(10)).
                build();
    }

    @Override
    protected String getFindQueryAll() {
        return  "select * from seller_invoice\n" +
                "inner join farmacia on farmacia.id=seller_invoice.pharma_house ";
    }

    public FieldData findByOrderID(int id) {
        String query="SELECT * FROM seller_invoice " +
                " inner join farmacia on farmacia.id=seller_invoice.pharma_house WHERE  order_id= ? ";
        return super.findByParameter(query, id).getFirst();
    }


    public List<FieldData> findByRangeBetweenAndRagioneSociale(Date range_start, Date range_end, String ragione_sociale) {
        List<FieldData> list=new ArrayList<>();
     String query=" select * from seller_invoice  " +
             " inner join farmacia on farmacia.id=seller_invoice.pharma_house " +
             " where create_at between   ? and ? " +
             "  and ragione_sociale like ? ";
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(query);
            preparedStatement.setDate(1,range_start);
            preparedStatement.setDate(2,range_end);
            preparedStatement.setString(3,ragione_sociale);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(FieldData.FieldDataBuilder.getbuilder().
                        setId(resultSet.getInt(1)).
                        setCasa_Farmaceutica(resultSet.getInt(2)).
                        setOrder_id(resultSet.getInt(3)).
                        setPayment_mode(resultSet.getString(4)).
                        setVat_amount(resultSet.getInt(5)).
                        setSubtotal(resultSet.getDouble(6)).
                        setTotal(resultSet.getDouble(7)).
                        setElapsed_date(resultSet.getDate(8)).
                        setNome_casa_farmaceutica(resultSet.getString(10)).
                        build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }









    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {

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
