package dao.hibernate;

import dao.GenericDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E,K> {

    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    protected Class<? extends E> daoType;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void add(E entity) {
        currentSession().save(entity);
    }


    public void save(E entity) {
        currentSession().saveOrUpdate(entity);
    }


    public void update(E entity) {
        currentSession().saveOrUpdate(entity);
    }


    public void remove(E entity) {
        currentSession().delete(entity);
    }


    public E findById(K key) {
        return (E) currentSession().get(daoType, key);
    }


    public List<E> getAll() {
        return currentSession().createCriteria(daoType).list();
    }
}
