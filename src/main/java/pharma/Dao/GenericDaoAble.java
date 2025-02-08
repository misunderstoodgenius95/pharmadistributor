package pharma.dao;

import java.util.List;

public interface GenericDaoAble<T,ID> {
public boolean insert(T entity);
public boolean update(T entity);
public T findById(ID id);
public boolean delete(T entity);
public List<T> findAll();
}
