package pharma.dao;

import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;
import pharma.Model.FieldData;
import pharma.config.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericJDBCDao<T,ID>  implements GenericDaoAble<T,ID> {
    private Database database;


    private  final String table_name;

    public GenericJDBCDao(String table_name, Database database)  {
        this.table_name = table_name;
        this.database = database;

    }

    /**
     * It can be associate to result that retrieve to find method.
     * @param   resultSet is the instance that contains the respective  attribute of query find
     * @return
     * @throws Exception
     */
    protected abstract  T mapRow(ResultSet resultSet) throws Exception;

    @Override
    public T findById(ID id) {

        try {
            PreparedStatement preparedStatement=database.execute_prepared_query("SELECT * FROM " + table_name + " WHERE id = " + id);
            setFindByIdParameters(preparedStatement,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){

                return mapRow(resultSet);
            }
        } catch (Exception e) {
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
        } catch (Exception e) {
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

        } catch (Exception e) {
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

          throw  new RuntimeException(e.getMessage());
        }

    }



    @Override
    public boolean delete(T entity) {
        try{
            PreparedStatement preparedStatement=database.execute_prepared_query(getDeletequery());
            setDeleteParameter(preparedStatement,entity);
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    protected abstract  String  getFindQueryAll();
    protected  abstract void setFindByIdParameters(PreparedStatement preparedStatement, ID id);
    protected abstract String  getInsertQuery() throws Exception;
    protected abstract  String getUpdatequery();
    protected  abstract  String getDeletequery();

    /**
     *
     * The Date are obtein by controls and insert into database;
     * @param statement
     * @param entity
     * @throws Exception
     */
    protected abstract void setInsertParameter(PreparedStatement statement,T entity) throws  Exception;
    protected  abstract void setUpdateParameter(PreparedStatement statement,T entity);
    protected  abstract void setDeleteParameter(PreparedStatement statement,T entity);
}
