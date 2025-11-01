package pharma.dao;

import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
            PreparedStatement preparedStatement=database.execute_prepared_query(" SELECT * FROM " + table_name + " WHERE id =? ");


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
            System.out.println(e.getMessage());
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



   protected    String  getFindQueryAll(){

        return " SELECT * FROM "+table_name;
    }
    protected  abstract void setFindByIdParameters(PreparedStatement preparedStatement, ID id) throws SQLException;
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


    public void commit(){
        database.commit();
        setTransaction(false);

    }
    public void rollback(){

        database.rollback();
        setTransaction(false);
    }
    public  void setTransaction(boolean value){

        database.setTransaction(value);
    }

    public List<T> findByParameter(String query,int id){
        List<T> resultList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(query );
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                resultList.add(mapRow(resultSet));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    return resultList;
    }

    public List<T> findByParameter(String query,String parameter){
        List<T> resultList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(query );
            preparedStatement.setString(1,parameter);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                resultList.add(mapRow(resultSet));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultList;
    }
    public boolean findByParametersExists(String query,Object... objects ){

        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(query );
            for(int i=0;i<objects.length;i++) {
                switch_type(preparedStatement, objects[i], i);
            }
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                  return resultSet.getBoolean(1);
            }




        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    public List<T> findByParameters(String query,Object... objects ){
        List<T> resultList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=database.execute_prepared_query(query );
            for(int i=0;i<objects.length;i++) {
                switch_type(preparedStatement, objects[i], i);
            }

            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                resultList.add(mapRow(resultSet));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultList;
    }



    private void switch_type( PreparedStatement p_statement,Object object, int index) throws SQLException {
        index++;
        switch (object){

            case String s->p_statement.setString(index,s);
            case Integer i->p_statement.setInt(index,i);
            case Double d->p_statement.setDouble(index,d);
            default -> throw new IllegalStateException("Unexpected value: " + object);
        }

    }




    public  int insertAndReturnID(T entity){


        if(entity==null){
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        try {

            PreparedStatement preparedStatement=database.execute_prepared_query(getInsertQuery());
            setInsertParameter(preparedStatement,entity);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return  resultSet.getInt(1);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return  -1;
    }



}
