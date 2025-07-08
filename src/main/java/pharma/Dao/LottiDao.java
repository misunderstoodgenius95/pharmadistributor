package pharma.dao;

import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LottiDao extends GenericJDBCDao<FieldData,Integer> {
    private final  String table;
    private  Database postegresql;
    public LottiDao( Database database, String tableName) {
        super(tableName, database);
        this.table = tableName;
        this.postegresql=database;
    }

    @Override
    protected FieldData mapRow(ResultSet resultSet) throws Exception {
       return FieldData.FieldDataBuilder.getbuilder().
               setcode(resultSet.getString("id")).
               setProduction_date(resultSet.getDate("production_date")).
               setElapsed_date(resultSet.getDate("elapsed_date")).
               setNome(resultSet.getString("nome")).
               setNome_categoria(resultSet.getString("categoria")).
               setNome_tipologia(resultSet.getString("tipologia")).
               setUnit_misure(resultSet.getString("misura")).
               setNome_casa_farmaceutica(resultSet.getString("casa_farmaceutica")).
               setFarmaco_id(resultSet.getInt("farmaco")).
               setNome_principio_attivo(resultSet.getString("principio_attivo")).
               setCasa_Farmaceutica(resultSet.getInt("pharma_id")).
               setQuantity(resultSet.getInt("qty")).build();
    }

    @Override
    protected String getFindQueryAll() {
        return " select * from lotto\n " +
                " inner join farmaco_all on farmaco_all.id=Lotto.farmaco ";
    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, Integer integer) {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "INSERT INTO "+table+" (id,farmaco,production_date,elapsed_date) VALUES(?,?,?,?)";
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
    statement.setString(1,entity.getCode());
    statement.setInt(2,entity.getFarmaco_id());
    statement.setDate(3,entity.getProduction_date());
    statement.setDate(4,entity.getElapsed_date());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, FieldData entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, FieldData entity) {

    }

    public List<String> FindByFarmacoNameHaveLot()  {
     ResultSet resultSet=postegresql.executeQuery("select distinct(farmaco_all.nome)from lotto\n" +
             "inner join farmaco_all on farmaco_all.id=lotto.farmaco");
     List<String> list=new ArrayList<>();
       try {
           while (resultSet.next()) {
                list.add(resultSet.getString(1));

           }
       }catch (SQLException e){

           e.printStackTrace();
       }
       return list;





    }
    public List<FieldData> findLotsbyPharma(int pharma){
       List<FieldData> list=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=
                    postegresql.execute_prepared_query(" select  * from lotto\n " +
                    " inner join farmaco_all on farmaco_all.id=lotto.farmaco where pharma_id = ? ");
            preparedStatement.setInt(1,pharma);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
              list.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;

    }


    public FieldData findByIds(int farmaco_id,String id_code) {

        FieldData fieldData = null;
        try {
             PreparedStatement preparedStatement=postegresql.execute_prepared_query("select * from lotto\n" +
                    "                 inner join farmaco_all on farmaco_all.id=Lotto.farmaco where lotto.id=? and farmaco= ? ");
            preparedStatement.setString(1,id_code);
            preparedStatement.setInt(2,farmaco_id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                 fieldData=mapRow(resultSet);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fieldData;


    }
    public List<FieldData> findByLottoCode(String id_code) {

        List<FieldData> list=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=postegresql.execute_prepared_query("select * from lotto\n" +
                    "                 inner join farmaco_all on farmaco_all.id=Lotto.farmaco where lotto.id=? ");
            preparedStatement.setString(1,id_code);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                list.add(mapRow(resultSet));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;


    }

    public String buildQueryasParameterSearch(String parameter){
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter is null");
        }




        Map<String, String> paramToColumn = Map.of(
                "Tipologia", "tipologia",
                "Categoria", "categoria",
                "principio_attivo", "principio_attivo",
                "Nome", "nome",
                "Data_di_scadenza", "elapsed_date"
        );

        String column = paramToColumn.get(parameter);

        if (column == null) {
            throw new IllegalArgumentException("Argument not present: " + parameter);
        }

        return getFindQueryAll() + " WHERE " + column + " ILIKE ?";
    }



    public List<FieldData> findBySearch(String parameter,String value) {
         value=value+"%";
        return super.findByParameter(buildQueryasParameterSearch(parameter), value);
    }

    public List<String> findLotsByFarmacoName(String name){
        List<String> list=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=postegresql.execute_prepared_query("select  lotto.id from lotto\n" +
                    " inner join farmaco_all on farmaco_all.id=lotto.farmaco\n" +
                    "where farmaco_all.nome = ?");
            preparedStatement.setString(1,name);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(resultSet.getString(1));

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }
}
