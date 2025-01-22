package pharma.dao;

import pharma.config.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class GenericJDBCDao<T,ID>  implements GenericDao<T,ID> {
  private Database database;

    private  final String table_name;

    public GenericJDBCDao(String table_name, Database database) {
        this.table_name = table_name;
        this.database = database;
    }
    protected abstract  T mapRow(ResultSet resultSet) throws SQLException;
    @Override
    public void insert(T t) {


    }

    @Override
    public void update(T t) {

    }

    @Override
    public T findById(ID id) {

    PreparedStatement preparedStatement=database.execute_prepared_query("SELECT * FROM " + table_name + " WHERE id = " + id);
    setFindByIdParameters(preparedStatement,id);
        try {

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
    public void delete(T t) {

    }

   protected  abstract void setFindByIdParameters(PreparedStatement preparedStatement, ID id);
}
