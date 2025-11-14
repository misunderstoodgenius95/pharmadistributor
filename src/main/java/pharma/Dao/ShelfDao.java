package pharma.dao;

import algoWarehouse.ShelfInfo;

import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ShelfDao extends GenericJDBCDao<ShelfInfo,String> {
    private final  String table="warehouse_shelf";
    private Database database;
    public ShelfDao( Database database) {
        super("warehouse_shelf", database);
        this.database=database;
    }

    @Override
    protected ShelfInfo mapRow(ResultSet resultSet) throws Exception {
        return ShelfInfo.ShelfInfoBuilder.get_builder().
                setShelf_code(resultSet.getString(1)).
                setMagazzino_id(resultSet.getInt(2)).
                setLenght(resultSet.getDouble(3)).
                setHeight(resultSet.getDouble(4)).
                setDeep(resultSet.getDouble(5)).
                setShelf_thickness(resultSet.getDouble(6)).
                setNum_rip(resultSet.getInt(7)).setWeight(resultSet.getInt(8))
                .build();
    }

    public  List<ShelfInfo> findShelfByMagazzinoId(int magazzino_id){
        return findByParameter("SELECT * FROM "+table+" WHERE warehouse_id= ? ",magazzino_id);




    }

    @Override
    protected void setFindByIdParameters(PreparedStatement preparedStatement, String string) {

    }

    @Override
    protected String getInsertQuery() throws Exception {
        return "   INSERT INTO "+table+" (code,warehouse_id,length,height,depth,shelf_thickness,num_level,capacity) " +
                "  VALUES( ?,?,?,?,?,?,?,?) ";
    }

    @Override
    protected String getUpdatequery() {
        return " ";
    }

    @Override
    protected String getDeletequery() {
        return " ";
    }



    @Override
    protected void setUpdateParameter(PreparedStatement statement, ShelfInfo entity) {

    }

    @Override
    protected void setDeleteParameter(PreparedStatement statement, ShelfInfo entity) {

    }

    @Override
    protected void setInsertParameter(PreparedStatement statement, ShelfInfo  entity) throws Exception {
        statement.setString(1,entity.getShelf_code());
        statement.setInt(2,entity.getMagazzino_id());
        statement.setDouble(3,entity.getLenght());
        statement.setDouble(4,entity.getHeight());
        statement.setDouble(5,entity.getDeep());
        statement.setDouble(6,entity.getShelf_thickness());
        statement.setInt(7,entity.getNum_rip());
        statement.setInt(8,entity.getWeight());
    }


}
