package dao.mysql;


import com.mysql.fabric.jdbc.FabricMySQLDriver;
import dao.DaoFactory;
import dao.GenericDao;
import dao.PersistException;
import entity.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import resource.DataBaseManager;

public class MySqlDaoFactory implements DaoFactory<Connection> {

    private static DaoFactory instance;


    private String user = DataBaseManager.getProperty("user");//Логин пользователя
    private String password = DataBaseManager.getProperty("password");//Пароль пользователя
    private String url = DataBaseManager.getProperty("url"); //URL адрес
    private String driver = DataBaseManager.getProperty("driver");//Имя драйвера

    private Map<Class, DaoCreator> creators;

    public Connection getContext() throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return  connection;
    }

    @Override
    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }

    public MySqlDaoFactory() {
        try {
            Class.forName(driver);
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);//Регистрируем драйвер
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        creators = new HashMap<Class, DaoFactory.DaoCreator>();

        creators.put(Client.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlClientDao(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(Client.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlClientDao(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(Admin.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlAdminDao(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(Forwarder.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlForwarderDao(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(Shop.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlShopDao(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(Product.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlProductDao(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(Order.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlOrderDao(MySqlDaoFactory.this, connection);
            }
        });

        creators.put(OrderItem.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlOrderItemDao(MySqlDaoFactory.this, connection);
            }
        });


        creators.put(Category.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlCategoryDao(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(Car.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlCarDao(MySqlDaoFactory.this, connection);
            }
        });
    }

    public static DaoFactory getInstance() {
        instance = new MySqlDaoFactory();
        return instance;
    }

    @Override
    public void openTransaction() throws PersistException {
        try {
            getContext().setAutoCommit(false);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void closeTransaction() throws PersistException {
        try {
            getContext().setAutoCommit(true);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void commit() throws PersistException {
        try {
            getContext().commit();
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void rollBack() throws PersistException {
        try {
            getContext().rollback();
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void close() throws PersistException {
        try {
            getContext().close();
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }
}