package dao.mysql;


import dao.CarDao;
import dao.DaoFactory;
import dao.PersistException;
import entity.Car;
import entity.Forwarder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlCarDao extends AbstractJDBCDao<Car, Integer>  implements CarDao{

    public MySqlCarDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, number, model FROM car ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO car (number, model) " +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE car " +
                "SET name = ?, model = ? " +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM car WHERE id= ?;";
    }

    @Override
    protected List<Car> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Car> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistCar car = new PersistCar();
                car.setId(rs.getInt("id"));
                car.setNumber(rs.getString("number"));
                car.setNumber(rs.getString("model"));
                result.add(car);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Car object) throws PersistException {
        try {
            statement.setString(1, object.getNumber());
            statement.setString(2, object.getModel());
         } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Car object) throws PersistException {
        try {
            statement.setString(1, object.getNumber());
            statement.setString(2, object.getModel());
            statement.setInt(3, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Car create() throws PersistException {
        Car c = new Car();
        return persist(c);
    }

    @Override
    public Car getCarByNumber(String number) throws PersistException {
        List<Car> list;
        String sql = getSelectQuery();
        sql += " WHERE number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, number);
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
    public Car getCarByModel(String model) throws PersistException {
        List<Car> list;
        String sql = getSelectQuery();
        sql += " WHERE model = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, model);
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
    public List<Car> getCarsByForwarders(int forwarderId) throws PersistException {
        List<Car> list;
        String sql = getSelectQuery();
        sql += " INNER JOIN forwarder_car ON car.id = forwarder_car.car_id " +
                "INNER JOIN forwarder ON forwarder_car.forwarder_id = forwarder.id" +
                " WHERE forwarder.id = " + forwarderId;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    private class PersistCar extends Car {
        public void setId(int id) {
            super.setId(id);
        }
    }
}
