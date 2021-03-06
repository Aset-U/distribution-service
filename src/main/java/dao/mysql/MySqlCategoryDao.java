package dao.mysql;


import dao.CategoryDao;
import dao.DaoFactory;
import dao.PersistException;
import entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlCategoryDao  extends AbstractJDBCDao<Category, Integer> implements CategoryDao {

    public MySqlCategoryDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, name FROM category";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO category (name) \n" +
                "VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return  "UPDATE category \n" +
                "SET name = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM category WHERE id= ?;";
    }

    @Override
    protected List<Category> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Category> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistCategory category = new PersistCategory();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                result.add(category);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Category object) throws PersistException {
        try {
            statement.setString(1, object.getName());
         } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Category object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Category create() throws PersistException {
        Category c = new Category();
        return persist(c);
    }

    @Override
    public Category getByName(String name) throws PersistException {
        List<Category> list;
        String sql = getSelectQuery();
        sql += " WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
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

    private class PersistCategory extends Category {
        public void setId(int id) {
            super.setId(id);
        }
    }
}
