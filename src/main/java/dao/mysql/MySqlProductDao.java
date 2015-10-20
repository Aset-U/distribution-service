package dao.mysql;

import dao.DaoFactory;
import dao.PersistException;
import dao.ProductDao;
import entity.Category;
import entity.Client;
import entity.Order;
import entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class MySqlProductDao extends AbstractJDBCDao<Product, Integer> implements ProductDao{

    public MySqlProductDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
        addRelation(Product.class, "category");
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, name, price, category_id FROM product";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO product (name, price, category_id) " +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return  "UPDATE product " +
                "SET name = ?, price = ?, category_id = ? " +
                "WHERE id = ?;";
}

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM product WHERE id= ?;";
    }

    @Override
    public  List<Product> findByCategory(Integer categoryId) throws PersistException {
        List<Product> list;
        String sql = getSelectQuery();
        sql += " WHERE category_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }

        return list;
    }

    @Override
    protected List<Product> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Product> result = new LinkedList<>();

        try {
            while (rs.next()) {
                PersistProduct product = new PersistProduct();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setCategory((Category) getDependence(Category.class, rs.getInt("category_id")));
                result.add(product);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Product object) throws PersistException {
        try {
            int categoryId = (object.getCategory() == null || object.getCategory().getId() == null) ? -1
                    : object.getCategory().getId();
            statement.setString(1, object.getName());
            statement.setDouble(2, object.getPrice());
            statement.setInt(3, categoryId);
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Product object) throws PersistException {
        try {
            int categoryId = (object.getCategory() == null || object.getCategory().getId() == null) ? -1
                    : object.getCategory().getId();
            statement.setString(1, object.getName());
            statement.setDouble(2, object.getPrice());
            statement.setInt(3, categoryId);
            statement.setInt(4, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Product create() throws PersistException {
        Product p = new Product();
        return persist(p);
    }


    private class PersistProduct extends Product {
        public void setId(int id) {
            super.setId(id);
        }
    }
}
