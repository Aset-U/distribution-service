package service;

import dao.GenericDao;

import java.util.List;

public abstract class GenericServiceImpl<E, K> implements GenericService<E, K> {

    private GenericDao<E, K> genericDao;

    public GenericServiceImpl(GenericDao<E,K> genericDao) {
        this.genericDao=genericDao;
    }

    public GenericServiceImpl() {}


    public void add(E entity) {
        genericDao.openCurrentSessionwithTransaction();
        genericDao.save(entity);
        genericDao.closeCurrentSessionwithTransaction();
    }

    public void update(E entity) {
        genericDao.openCurrentSessionwithTransaction();
        genericDao.update(entity);
        genericDao.closeCurrentSessionwithTransaction();
    }

    public void remove(E entity) {
        genericDao.openCurrentSessionwithTransaction();
        genericDao.delete(entity);
        genericDao.closeCurrentSessionwithTransaction();
    }

    public E getById(K key) {
        genericDao.openCurrentSession();
        E object = genericDao.findById(key);
        genericDao.closeCurrentSession();
        return object;
    }

    public List<E> getAll() {
        genericDao.openCurrentSession();
        List<E> objects = genericDao.getAll();
        genericDao.closeCurrentSession();
        return objects;
    }
}
