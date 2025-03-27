package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderDao  extends GenericJDBCDao<FieldData,String> {
private  final  String table="purchase_order";
private  final Database database;
    public PurchaseOrderDao( Database database) {
        super("purchase_order", database);
        this.database=database;
    }


    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
        return  FieldData.FieldDataBuilder.getbuilder().
                setId(resultSet.getInt(1)).
                setProduction_date(resultSet.getDate(2))
                .setSubtotal(resultSet.getDouble(3))
                .setVat_amount(resultSet.getDouble(4))
                .setTotal(resultSet.getDouble(5)).
                setOriginal_order_id(resultSet.getString(6)).
                setInvoice_id(resultSet.getInt(7)).
                setCasa_Farmaceutica(resultSet.getInt(8)).
                build();
    }

    @Override
    protected String getFindQueryAll() {
        return " SELECT * FROM "+table;
    }



    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, String s) {

    }




    @Override
    protected String getInsertQuery() throws Exception {
        return " INSERT INTO "+table+" (data,subtotale,iva,totale,provider_order_id ,pharma_id) VALUES(?,?,?,?,?,?) RETURNING id ; ";
    }

    @Override
    protected String getUpdatequery() {
        return " UPDATE "+table+" SET invoice_id= ? where id = ?  ";
    }

    @Override
    protected String getDeletequery() {
        return "";
    }

    @Override
    protected void setInsertParameter(PreparedStatement statement, FieldData entity) throws Exception {
        statement.setDate(1,entity.getProduction_date());
        statement.setDouble(2,entity.getSubtotal());
        statement.setDouble(3,entity.getVat_amount());
        statement.setDouble(4,entity.getTotal());
        statement.setString(5,entity.getOriginal_order_id());
        statement.setInt(6,entity.getCasa_farmaceutica());

    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {
        try {
            statement.setInt(1,entity.getInvoice_id());
            statement.setInt(2,entity.getPurchase_order_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }


    public List<FieldData> findAllWithInvoiceIdNullByPharmaId(int pharma_id) {
            List<FieldData> list=new ArrayList<>();
        ResultSet resultSet= null;
        try {
           PreparedStatement preparedStatement = database.execute_prepared_query(" select   purchase_order.id,purchase_order.data,purchase_order.subtotale, " +
                    " purchase_order.iva,purchase_order.totale, purchase_order.provider_order_id,purchase_order.pharma_id,pharma.anagrafica_cliente from " +
                   table+
                    " inner join pharma on  pharma.id=purchase_order.pharma_id " +
                    " where purchase_order.invoice_id IS NULL and   purchase_order.pharma_id = ? ");
           preparedStatement.setInt(1,pharma_id);
           resultSet=preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            while (resultSet.next()) {
                 list.add(FieldData.FieldDataBuilder.getbuilder().
                        setId(resultSet.getInt(1)).
                        setProduction_date(resultSet.getDate(2))
                        .setSubtotal(resultSet.getDouble(3))
                        .setVat_amount(resultSet.getDouble(4))
                        .setTotal(resultSet.getDouble(5)).
                        setOriginal_order_id(resultSet.getString(6)).
                        setCasa_Farmaceutica(resultSet.getInt(7)).
                        setNome_casa_farmaceutica(resultSet.getString(8)).
                        build());


            }
        }catch (SQLException e){

            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    return  list;

    }
    public List<FieldData> findByInvoiceid(int id){
        String query="SELECT * FROM "+table +" WHERE invoice_id = ?";
        return findByParameter(query,id);

    }
    public String findbyPharmaHousebyInvoiceID(int id){
        String value="";
        String query="select pharma_house from purchase_invoice \n" +
                " inner join purchase_order po on purchase_invoice.id = po.invoice_id \n" +
                " where invoice_id = ? \n" +
                " group by  pharma_house";

        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
             if(resultSet.next()) {
                 value=resultSet.getString(1);
             }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return value;


    }


}
