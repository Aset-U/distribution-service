package dao;


import java.io.Serializable;
import java.util.List;


public interface GenericDao<E,K>{

    public void add(E entity);

    public void save(E entity);

    public void update(E entity);

    public void remove(E entity);

    public E findById(K key);

    public List<E> getAll();

}