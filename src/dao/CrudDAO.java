package dao;

import dao.SuperDAO;
import entity.SuperEntity;

import java.io.Serializable;
import java.util.List;

public interface CrudDAO<T extends SuperEntity,ID extends Serializable> extends SuperDAO {
    List<T> findAll() throws Exception;
    T find(ID pk) throws Exception;
    boolean save(T entity) throws Exception;
    boolean update(T entity) throws Exception;
    boolean delete (ID pk) throws Exception;
}
