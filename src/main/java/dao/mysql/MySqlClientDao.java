package dao.mysql;

import dao.ClientDao;
import dao.DaoFactory;
import dao.PersistException;
import entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class MySqlClientDao extends AbstractJDBCDao<Client, Integer> implements ClientDao {

    public MySqlClientDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }
    @Override
    public String getSelectQuery() {
        return "SELECT id, username, password, first_name, last_name, phone, " +
                "email FROM client";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO client (username, password, first_name, last_name, " +
                "phone, email) \n" +
                "VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE client SET username= ?, password= ?, first_name= ?," +
                " last_name= ?, phone= ?, email= ? WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM client WHERE id= ?;";
    }

    @Override
    protected List<Client> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Client> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistClient client = new PersistClient();
                client.setId(rs.getInt("id"));
                client.setUsername(rs.getString("username"));
                client.setPassword(rs.getString("password"));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setPhoneNumber(rs.getString("phone"));
                client.setEmail(rs.getString("email"));
                result.add(client);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Client object) throws PersistException {
        try {
            statement.setString(1, object.getUsername());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getFirstName());
            statement.setString(4, object.getLastName());
            statement.setString(5, object.getPhoneNumber());
            statement.setString(6, object.getEmail());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Client object) throws PersistException {
        try {
            statement.setString(1, object.getUsername());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getFirstName());
            statement.setString(4, object.getLastName());
            statement.setString(5, object.getPhoneNumber());
            statement.setString(6, object.getEmail());
            statement.setInt(7, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Client create() throws PersistException {
        Client c = new Client();
        return persist(c);
    }


    @Override
    public Client findByUsernameAndPassword(String username, String password) throws PersistException {
        List<Client> list;
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

    private class PersistClient extends Client {
        public void setId(int id) {
            super.setId(id);
        }
    }
}
