package dao.mysql;

import dao.AdminDao;
import dao.DaoFactory;
import dao.PersistException;
import entity.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlAdminDao extends AbstractJDBCDao<Admin, Integer> implements AdminDao {

    public MySqlAdminDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }
    @Override
    public String getSelectQuery() {
        return "SELECT id, username, password, first_name, last_name, phone, " +
                "email FROM admin";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO admin (username, password, first_name, last_name, " +
                "phone, email) \n" +
                "VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE admin SET username= ?, password= ?, first_name= ?," +
                " last_name= ?, phone= ?, email= ? WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM admin WHERE id= ?;";
    }

    @Override
    protected List<Admin> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Admin> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistAdmin admin = new PersistAdmin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setFirstName(rs.getString("first_name"));
                admin.setLastName(rs.getString("last_name"));
                admin.setPhoneNumber(rs.getString("phone"));
                admin.setEmail(rs.getString("email"));
                result.add(admin);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Admin object) throws PersistException {
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
    protected void prepareStatementForUpdate(PreparedStatement statement, Admin object) throws PersistException {
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
    public Admin create() throws PersistException {
        Admin a = new Admin();
        return persist(a);
    }

    @Override
    public Admin findByUsernameAndPassword(String username, String password) throws PersistException {
        List<Admin> list;
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

    private class PersistAdmin extends Admin {
        public void setId(int id) {
            super.setId(id);
        }
    }
}
