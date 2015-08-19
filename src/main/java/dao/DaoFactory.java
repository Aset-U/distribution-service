package dao;

/** Фабрика объектов для работы с базой данных */
public interface DaoFactory<Context> {

    public interface DaoCreator<Context> {
        public GenericDao create(Context context);
    }

    /** Возвращает подключение к базе данных */
    public Context getContext() throws PersistException;

    /** Возвращает объект для управления персистентным состоянием объекта */
    public GenericDao getDao(Context context, Class dtoClass) throws PersistException;

    public void openTransaction() throws PersistException;

    public void closeTransaction() throws PersistException;

    public void commit() throws PersistException;

    public void rollBack() throws PersistException;

    public void close() throws PersistException;

}