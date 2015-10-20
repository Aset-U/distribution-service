package service;

import java.util.List;

public interface GenericService <E, K> {

    public void add(E entity);

    public void update(E entity);

    public void remove(E entity);

    public E getById(K key);

    public List<E> getAll();
}

