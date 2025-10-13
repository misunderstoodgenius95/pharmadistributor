package pharma.dao;

import algoWarehouse.LotAssigment;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LotAssigmentDao extends GenericJDBCDao<LotAssigment,Integer>{
    private String table="lot_assignment";
    public LotAssigmentDao( Database database) {
        super("lot_assignment", database);
    }

    public LotAssigmentDao(String table_name, Database database) {
        super(table_name, database);
    }

    @Override
    protected LotAssigment mapRow(ResultSet resultSet) throws Exception {

        LotAssigment lotAssigment=new LotAssigment();
        lotAssigment.setId(resultSet.getInt(1));
        lotAssigment.setFarmaco_id(resultSet.getInt(2));
        lotAssigment.setLot_code(resultSet.getString(3));
        lotAssigment.setQuantity_request(resultSet.getInt(4));
        return lotAssigment;
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) throws SQLException {
        preparedStatement.setInt(1, integer);
    }


    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO " + table + " (farmaco_id,lot_code,request_quantity) VALUES(?,?,?) " +
                "  RETURNING id; ";
    }
    @Override
    protected void setInsertParameter(PreparedStatement statement, LotAssigment entity) throws Exception {
        statement.setInt(1,entity.getFarmaco_id());
        statement.setString(2,entity.getLot_code());
        statement.setInt(3,entity.getQuantity_request());
    }


    public boolean findExistsAssigment( int  farmaco_id,String lot_code) {
        String query="select  exists(\n" +
                "    select  1\n" +
                "    from lot_assignment\n" +
                "    where  lot_code=?\n"+
                "    and farmaco_id=?\n" +
                "); ";

         return super.findByParametersExists(query,lot_code,farmaco_id);
    }

    @Override
    protected String getUpdatequery() {
        return " UPDATE "+ table + " SET request_quantity=request_quantity + ? WHERE lot_code =? and farmaco_id =? ";
    }
    @Override
    protected void setUpdateParameter(PreparedStatement statement, LotAssigment entity) {
        try {
            statement.setInt(1,entity.getQuantity_request());
            statement.setString(2,entity.getLot_code());
            statement.setInt(3,entity.getFarmaco_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * if
     * @param farmaco_id
     * @param lot_code
     * @return -1 if cannot find the value  and insert the value, otherwise  if not equal -1 the value exists and update
     */
    public int findExistAndReturnId ( int farmaco_id,String lot_code) {
        String query=" SELECT * FROM "+table+ " WHERE farmaco_id = ? and lot_code= ? ";
        List<LotAssigment> list=findByParameters(query, farmaco_id,lot_code);
        if(findByParameters(query, farmaco_id,lot_code).isEmpty()){
             return -1;
         }else{
             return  list.getFirst().getId();
         }
    }

    @Override
    protected String getDeletequery() {
        return "";
    }





    @Override
    protected void setDeleteParameter(PreparedStatement statement, LotAssigment entity) {

    }


}
