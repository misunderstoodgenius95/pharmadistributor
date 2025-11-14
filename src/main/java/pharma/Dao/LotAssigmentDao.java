package pharma.dao;

import algoWarehouse.LotAssigment;
import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LotAssigmentDao extends GenericJDBCDao<LotAssigment,Integer>{
    private String table="lot_assignment";
    private Database database;
    public LotAssigmentDao( Database database) {
        super("lot_assignment", database);
        this.database=database;
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
                "  RETURNING id ; ";
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

    public List<FieldData> findPharmaHousebyLotCode(String lot_code){
        List<FieldData> resultList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(" select lot_code,casa_farmaceutica  from lot_assignment \n" +
                            " inner join farmaco_all on farmaco_id=farmaco_all.id where lot_code= ? ; ");
            preparedStatement.setString(1, lot_code);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                resultList.add(FieldData.FieldDataBuilder.getbuilder().

                        setcode(resultSet.getString("lot_code")).
                        setNome_casa_farmaceutica(resultSet.getString("casa_farmaceutica")).build());


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultList;

    }
    public List<FieldData> findByFarmacoAll(){
        List<FieldData> resultList=new ArrayList<>();
        try {
            ResultSet resultSet=database.executeQuery("select * from lot_assignment\n" +
                    "inner join  lotto on lot_assignment.lot_code=lotto.id\n" +
                    "inner join  farmaco_all on farmaco_all.id=lotto.farmaco; ");

            while(resultSet.next()){
                resultList.add(FieldData.FieldDataBuilder.getbuilder().
                        setFarmaco_id(resultSet.getInt("farmaco_id")).
                        setNome(resultSet.getString("nome")).
                        setDescription(resultSet.getString("descrizione")).
                        setNome_categoria(resultSet.getString("categoria")).
                        setNome_tipologia(resultSet.getString("tipologia")).
                        setElapsed_date(resultSet.getDate("elapsed_date")).
                        setUnit_misure(resultSet.getString("misura")).
                        setNome_principio_attivo(resultSet.getString("principio_attivo")).
                        setcode(resultSet.getString("lot_code")).
                        setNome_casa_farmaceutica(resultSet.getString("casa_farmaceutica")).
                        setQuantity(resultSet.getInt("qty"))
                        .build());


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultList;

    }











    public List<FieldData> findQuantitybyFarmacoId(int farmaco_id){
        List<FieldData> resultList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query("select * from lot_assignment\n" +
                    "inner join farmaco_all on lot_assignment.farmaco_id=farmaco_all.id\n" +
                    "inner join lotto on lot_assignment.lot_code=lotto.id\n" +
                    " where farmaco_id= ? ");
            preparedStatement.setInt(1,farmaco_id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                resultList.add(FieldData.FieldDataBuilder.getbuilder().
                                setFarmaco_id(resultSet.getInt("farmaco_id")).
                                setcode(resultSet.getString("lot_code")).
                                setElapsed_date(resultSet.getDate("elapsed_date")).
                        setAvailability(resultSet.getInt("request_quantity")).build());
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultList;

    }
    public List<FieldData> findFarmacoQuantity(int farmaco_id){
        List<FieldData> resultList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query("select request_quantity from lot_assigment_shelves\n " +
                    "inner join lot_assignment la on la.id = lot_assigment_shelves.lot_assigment_id\n " +
                    "where farmaco_id=? ");
            preparedStatement.setInt(1,farmaco_id);

            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                resultList.add(FieldData.FieldDataBuilder.getbuilder().setQuantity(resultSet.getInt(1)).build());
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultList;

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
