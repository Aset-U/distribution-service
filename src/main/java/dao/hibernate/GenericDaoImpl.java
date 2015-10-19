package dao.hibernate;

import dao.GenericDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E,K> {

    private Session currentSession;
    private Transaction currentTransaction;

    protected Class<? extends E> daoType;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        return HibernateUtil.getSessionFactory();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void add(E entity) {
        getCurrentSession().save(entity);
    }


    public void save(E entity) {
        getCurrentSession().saveOrUpdate(entity);
    }


    public void update(E entity) {
        getCurrentSession().saveOrUpdate(entity);
    }


    public void remove(E entity) {
        getCurrentSession().delete(entity);
    }


    public E findById(K key) {
        return (E) getCurrentSession().get(daoType, key);
    }


    public List<E> getAll() {
        return getCurrentSession().createCriteria(daoType).list();
    }
}
