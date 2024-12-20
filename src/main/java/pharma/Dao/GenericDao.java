package pharma.dao;

public interface GenericDao<T,ID> {
public void insert(T t);
public void update(T t);
public T findById(ID id);
public void delete(T t);
}
