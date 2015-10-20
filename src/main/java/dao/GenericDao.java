package dao;


import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;


public interface GenericDao<E,K>{

    public Session openCurrentSession();

    public Session openCurrentSessionwithTransaction();

    public void closeCurrentSession();

    public void closeCurrentSessionwithTransaction();

    public void save(E entity);

    public void update(E entity);

    public void delete(E entity);

    public E findById(K key);

    public List<E> getAll();

}