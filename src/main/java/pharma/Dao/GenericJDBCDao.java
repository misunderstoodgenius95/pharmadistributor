package pharma.dao;

import pharma.config.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericJDBCDao<T,ID>  implements GenericDao<T,ID> {
    private Database database;


    private  final String table_name;

    public GenericJDBCDao(String table_name, Database database) throws SQLException {
        this.table_name = table_name;
        this.database = database;

    }
    protected abstract  T mapRow(ResultSet resultSet) throws SQLException;

    @Override
    public T findById(ID id) {

        try {
            PreparedStatement preparedStatement=database.execute_prepared_query("SELECT * FROM " + table_name + " WHERE id = " + id);
            setFindByIdParameters(preparedStatement,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){

                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    public List<T> findAll() {

       String query=getFindQueryAll();
      ResultSet resultSet=database.executeQuery(query);
      List<T> resultList=new ArrayList<>();
        try {
            while(resultSet.next()){
               resultList.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  resultList;
    }



    @Override
    public boolean insert(T entity) {

        if(entity==null){
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        try {

            PreparedStatement preparedStatement=database.execute_prepared_query(getInsertQuery());
            setInsertParameter(preparedStatement,entity);
            return  preparedStatement.executeUpdate()>0;

        } catch (SQLException e) {
           e.printStackTrace();
        }
        return  false;


    }

    @Override
    public boolean update(T entity) {
        try{
            PreparedStatement preparedStatement=database.execute_prepared_query(getUpdatequery());
            setUpdateParameter(preparedStatement,entity);
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public boolean delete(T entity) {
        try{
            PreparedStatement preparedStatement=database.execute_prepared_query(getDeletequery());
            setDeleteParameter(preparedStatement,entity);
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected abstract  String  getFindQueryAll();
    protected  abstract void setFindByIdParameters(PreparedStatement preparedStatement, ID id);
    protected abstract String  getInsertQuery();
    protected abstract  String getUpdatequery();
    protected  abstract  String getDeletequery();
    protected abstract void setInsertParameter(PreparedStatement statement,T entity);
    protected  abstract void setUpdateParameter(PreparedStatement statement,T entity);
    protected  abstract void setDeleteParameter(PreparedStatement statement,T entity);
}
