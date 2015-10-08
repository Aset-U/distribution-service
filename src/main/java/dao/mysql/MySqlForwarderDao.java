package dao.mysql;

import dao.DaoFactory;
import dao.ForwarderDao;
import dao.PersistException;
import dao.UserDao;
import models.Forwarder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class MySqlForwarderDao extends AbstractJDBCDao<Forwarder, Integer> implements UserDao, ForwarderDao {

    public MySqlForwarderDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, username, password, first_name, last_name, phone, " +
                "email FROM forwarders";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO forwarders (username, password, first_name, last_name, " +
                "phone, email) \n" +
                "VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE forwarders SET username= ?, password= ?, first_name= ?," +
                " last_name= ?, phone= ?, email= ? WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM forwarders WHERE id= ?;";
    }

    @Override
    protected List<Forwarder> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Forwarder> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistForwarder forwarder = new PersistForwarder();
                forwarder.setId(rs.getInt("id"));
                forwarder.setUsername(rs.getString("username"));
                forwarder.setPassword(rs.getString("password"));
                forwarder.setFirstName(rs.getString("first_name"));
                forwarder.setLastName(rs.getString("last_name"));
                forwarder.setPhoneNumber(rs.getString("phone"));
                forwarder.setEmail(rs.getString("email"));
                result.add(forwarder);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Forwarder object) throws PersistException {
        try {
            statement.setString(1, object.getUsername());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getFirstName());
            statement.setString(4, object.getLastName());
            statement.setString(5, object.getEmail());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Forwarder object) throws PersistException {
        try {
            statement.setString(1, object.getUsername());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getFirstName());
            statement.setString(4, object.getLastName());
            statement.setString(5, object.getEmail());
            statement.setInt(6, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Forwarder create() throws PersistException {
        Forwarder f = new Forwarder();
        return persist(f);
    }

    @Override
    public Forwarder getUserByUsernameAndPassword(String username, String password) throws PersistException {
        List<Forwarder> list;
        String sql = getSelectQuery();
        sql += " WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }

    @Override
    public List<Forwarder> getForwardersByShop(int shopId) throws PersistException {
        List<Forwarder> list;
        String sql = getSelectQuery();
        sql += " INNER JOIN shop_forwarder ON forwarders.id = shop_forwarder.forwarder_id " +
                "INNER JOIN shops ON shop_forwarder.shop_id = shops.id" +
                " WHERE shops.id = " + shopId;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    public List<Forwarder> getForwardersByCar(int carId) throws PersistException {
        List<Forwarder> list;
        String sql = getSelectQuery();
        sql += " INNER JOIN forwarder_car ON forwarders.id = forwarder_car.forwarder_id " +
                "INNER JOIN cars ON forwarder_car.car_id = cars.id" +
                " WHERE cars.id = " + carId;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    private class PersistForwarder extends Forwarder {
        public void setId(int id) {
            super.setId(id);
        }
    }
}
