package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseCreditNoteDao extends  GenericJDBCDao<FieldData,Integer> {
    private  final  String table_name="purchase_credit_note";
    private  Database database;
    public PurchaseCreditNoteDao(Database database) {
        super("purchase_credit_note", database);
        this.database=database;
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
      return FieldData.FieldDataBuilder.getbuilder().
              setId(resultSet.getInt(1)).
              setCredit_note_number(resultSet.getString(2)).
              setProduction_date(resultSet.getDate(4))
              .setMotive(resultSet.getString(5))
              .setSubtotal(resultSet.getInt("subtotal")).
              setVat_amount(resultSet.getDouble("vat_amount")).
              setTotal(resultSet.getDouble("total")).build();


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
    credit_note_number text NOT NULL,
    invoice_id int NOT NULL REFERENCES purchase_invoice(id),
    issue_data DATE NOT NULL,
    motivo text NOT NULL ,
    pharma_id  int references pharma(id),
    subtotal double precision not null,
    vat_amount double precision not null,
    total double precision not null,
    created_at TIMESTAMP DEFAULT current_timestamp

            statement.setString(1,entity.getCredit_note_number());
        statement.setInt(2,entity.getInvoice_id());
        statement.setDate(3,entity.getProduction_date());
        statement.setString(4,entity.getMotive());
        statement.setInt(5,entity.getCasa_farmaceutica());
        statement.setDouble(6,entity.getSubtotal());
        statement.setDouble(7,entity.getVat_amount());
        statement.setDouble(8,entity.getTotal());

 */
    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO "+table_name +" (credit_note_number,invoice_id,issue_data,motivo,pharma_id,subtotal,vat_amount,total) VALUES(?,?,?,?,?,?,?,?) RETURNING id;  ";
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


create table purchase_credit_note
(
    id int PRIMARY KEY generated always as identity,
    credit_note_number text NOT NULL,
    invoice_id int NOT NULL REFERENCES purchase_invoice(id),
    issue_data DATE NOT NULL,
    motivo text NOT NULL ,
    pharma_id  int references pharma(id),
    subtotal double precision not null,
    vat_amount double precision not null,
    total double precision not null,
    created_at TIMESTAMP DEFAULT current_timestamp
);

);

     */
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

    public boolean exist_credit_note(int invoice_id){
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query("select count(*) from purchase_credit_note where invoice_id = ?");
            preparedStatement.setInt(1,invoice_id);
            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()){
                int count=resultSet.getInt(1);
                return  count ==1;
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  false;


    }


    public List<FieldData> findDetailbyInvoiceId(int id){
        List<FieldData> list=new ArrayList<>();
        try {

            PreparedStatement preparedStatement=database.execute_prepared_query( "   select * from purchase_credit_note\n" +
                    "                          inner join purchase_credit_note_details on purchase_credit_note.id = purchase_credit_note_details.credit_note_id\n" +
                    "                     inner join purchase_order_detail on purchase_order_detail.id= purchase_credit_note_details.order_details\n" +
                    "                     inner join farmaco_all on purchase_order_detail.farmaco=farmaco_all.id  " +
                    "  where invoice_id= ? ");
            preparedStatement.setInt(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(FieldData.FieldDataBuilder.getbuilder().
                        setId(resultSet.getInt(1)).
                        setcode(resultSet.getString(2)).
                        setPrice(resultSet.getDouble("price")).
                        setFarmaco_id(resultSet.getInt("farmaco")).
                        setOrder_id(resultSet.getInt("purchase_order")).
                        setQuantity(resultSet.getInt("quantity")).
                        setVat_percent(resultSet.getInt("vat_percent")).
                        setNome_farmaco(resultSet.getString("nome_farmaco")).
                        setNome_tipologia(resultSet.getString("tipologia")).
                        setUnit_misure(resultSet.getString("misura")).
                        build());



            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  list;


    }

    public FieldData findbyInvoiceid(int id){

        String query="SELECT * FROM "+table_name+ " WHERE invoice_id= ? ";
      return findByParameter(query,id).getFirst();

    }


    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }
}
