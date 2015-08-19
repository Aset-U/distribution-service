package dao.mysql;

import dao.DaoFactory;
import dao.PersistException;
import dao.ProductDao;
import entity.Category;
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
        return "SELECT id, name, price, weight, category_id FROM products";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO products (name, price, weight, category_id) " +
                "VALUES (?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return  "UPDATE products " +
                "SET name = ?, price = ?, weight = ?," +
                " category_id = ? " +
                "WHERE id = ?;";
}

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM products WHERE id= ?;";
    }

    public Product getByPK(Integer productId, boolean withOrders) throws PersistException {
        Product product = getByPK(productId);

        if (withOrders)
        {
            List<Order> orders = null;
            DaoFactory factory = MySqlDaoFactory.getInstance();
            Connection connection = (Connection) factory.getContext();
            MySqlOrderDao orderDao = (MySqlOrderDao) factory.getDao(connection, Order.class);

            String sql = "SELECT orders.id, client_id, status_id " +
                    "FROM orders join product_orders " +
                    "where product_orders.Order_id = orders.id " +
                    "and product_orders.Product_id = " + productId + ";";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet rs = statement.executeQuery();
                orders = orderDao.parseResultSet(rs);
                product.setOrders(orders);
            } catch (Exception e) {
                throw new PersistException(e);
            }
        }
        return product;
    }

    @Override
    public  List<Product> getProductByCategory(Integer categoryId) throws PersistException {
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
    public List<Product> getProductsByOrder(Integer orderId) throws PersistException {
        List<Product> list;
        String sql = getSelectQuery();
        sql += " INNER JOIN products_orders ON product.id = products_orders.Product_id " +
                "INNER JOIN orders ON product_orders.Order_id = orders.id" +
                " WHERE orders.id = " + orderId;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    public List<Product> getAllProduct() throws PersistException {
        return super.getAll();
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
                product.setWeight(rs.getString("weight"));
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
            statement.setString(3, object.getWeight());
            statement.setInt(4, categoryId);
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
            statement.setString(3, object.getWeight());
            statement.setInt(4, categoryId);
            statement.setInt(5, object.getId());
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
