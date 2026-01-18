package pharma.dao;

import pharma.Model.ShelvesAssigment;
import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LotAssigmentShelvesDao extends GenericJDBCDao<ShelvesAssigment, Integer> {
    private  Database database;
    public LotAssigmentShelvesDao( Database database) {
        super("lot_assigment_shelves", database);
        this.database=database;
    }

    @Override
    protected ShelvesAssigment mapRow(ResultSet resultSet) throws Exception {

     return new ShelvesAssigment(
          resultSet.getInt("id"),
              resultSet.getString("shelf_code"),
              resultSet.getInt("quantity"),
              resultSet.getInt("shelves_level"),
              resultSet.getInt("magazzino_id")
      );
    }

    public List<ShelvesAssigment> findByWarehouse( int warehouse_id) {
        String query="SELECT * FROM lot_assigment_shelves WHERE warehouse_id = ? ";
        return super.findByParameter(query, warehouse_id);
    }

    public List<ShelvesAssigment> findByLots(String lots){
       String query=" select * from lot_assigment_shelves\n " +
               " inner join lot_assignment on lot_assignment.id=lot_assigment_shelves.id\n" +
               " inner join farmaco_all on lot_assigment "+
               " where lot_code = ? " +
               "   ";
       return  super.findByParameter(query,lots);


    }


    public List<FieldData> findbyLotCode(String lot_code){
        List<FieldData> list=new ArrayList<>();
        try {
            PreparedStatement statement=database.execute_prepared_query("select lot_code,w.nome,shelf_code,shelves_level,quantity,w.id as warehouse_id,lot_assigment_id ,las.id as shelves_id " +
                    " from lot_assignment\n " +
                    "inner join lot_assigment_shelves las on lot_assignment.id = las.lot_assigment_id\n" +
                    "inner join warehouse w on las.warehouse_id = w.id\n" +
                    "where  lot_code =? ");
            statement.setString(1,lot_code);
             ResultSet resultSet=statement.executeQuery();
            while(resultSet.next()){
                list.add(mapRowFind(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }

    private FieldData mapRowFind(ResultSet resultSet){
        try {
             return FieldData.FieldDataBuilder.getbuilder().
                     setcode(resultSet.getString("lot_code")).
                    setNome(resultSet.getString("nome")).
                    setShelf_code(resultSet.getString("shelf_code")).
                    setShelves_code(resultSet.getInt("shelves_level")).
                    setQuantity(resultSet.getInt("quantity")).
                     setForeign_id(resultSet.getInt("warehouse_id")).
                     setCapacity(resultSet.getInt("lot_assigment_id")).
                     setId(resultSet.getInt("shelves_level"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
    public List<FieldData> findbyShelfCode(String shelf_code){
        List<FieldData> list=new ArrayList<>();
        try {
            PreparedStatement statement=database.execute_prepared_query("select *  from lot_assigment_shelves\n" +
                    "inner join lot_assignment la on la.id = lot_assigment_shelves.lot_assigment_id\n" +
                    "inner join warehouse w on w.id = lot_assigment_shelves.warehouse_id\n" +
                    "where shelf_code= ? ");
            statement.setString(1,shelf_code);
            ResultSet resultSet=statement.executeQuery();
            while(resultSet.next()){
                list.add(mapRowFind(resultSet));
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
        return "INSERT INTO lot_assigment_shelves (lot_assigment_id,shelf_code,shelves_level,warehouse_id,quantity)" +
                " VALUES( ?,?,?,?,?)";
    }

    @Override
    protected String getUpdatequery() {
        return " UPDATE lot_assigment_shelves SET quantity = quantity - ? WHERE id=?   ";
    }

    @Override
    protected String getDeletequery() {
        return "";
    }

    @Override
    protected void setInsertParameter(PreparedStatement statement, ShelvesAssigment entity) throws Exception {
        statement.setInt(1,entity.getLot_assigment());
        statement.setString(2,entity.getShelf_code());
        statement.setInt(3,entity.getShelf_level());
        statement.setInt(4,entity.getMagazzino_id());
        statement.setInt(5,entity.getQuantity());
    }

    @Override
    protected void setUpdateParameter(PreparedStatement statement, ShelvesAssigment entity) {
        try {
            statement.setInt(1,entity.getQuantity());
            statement.setInt(2,entity.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, ShelvesAssigment entity) {

    }
}
