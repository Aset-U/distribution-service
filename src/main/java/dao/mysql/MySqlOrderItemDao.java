package dao.mysql;


import dao.DaoFactory;
import dao.PersistException;
import entity.Order;
import entity.OrderItem;
import entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlOrderItemDao extends AbstractJDBCDao<OrderItem, Integer>{

    public MySqlOrderItemDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
     //   addRelation(OrderItem.class, "order");
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, product_id, quantity, order_id FROM order_item ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO order_item (product_id, quantity, order_id) \n" +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return  "UPDATE order_item " +
                "SET product_id = ?, quantity  = ?, order_id = ? " +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM order_item WHERE id= ?;";
    }

    public List<OrderItem> getByOrderId(int orderId) throws PersistException{
        List<OrderItem> list;
        String sql = getSelectQuery();
        sql += " WHERE order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        return list;
    }

    @Override
    protected List<OrderItem> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<OrderItem> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistOrderItem orderItem = new PersistOrderItem();
                orderItem.setId(rs.getInt("id"));
                orderItem.setProduct((Product) getDependence(Product.class, rs.getInt("product_id")));
                orderItem.setQuantity(rs.getShort("quantity"));
                orderItem.setOrder((Order) getDependence(Order.class, rs.getInt("order_id")));
                result.add(orderItem);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, OrderItem object) throws PersistException {
        try {
            int orderId = (object.getOrder() == null || object.getOrder().getId() == null) ? -1
                    : object.getOrder().getId();
            int productId = (object.getProduct() == null || object.getProduct().getId() == null) ? -1
                    : object.getProduct().getId();
            statement.setInt(1, productId);
            statement.setShort(2, object.getQuantity());
            statement.setInt(3, orderId);
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, OrderItem object) throws PersistException {
        try {
            int orderId = (object.getOrder() == null || object.getOrder().getId() == null) ? -1
                    : object.getOrder().getId();
            int productId = (object.getProduct() == null || object.getProduct().getId() == null) ? -1
                    : object.getProduct().getId();
            statement.setInt(1, productId);
            statement.setShort(2, object.getQuantity());
            statement.setInt(3, orderId);
            statement.setInt(4, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public OrderItem create() throws PersistException {
        OrderItem orderItem = new OrderItem();
        return persist(orderItem);
    }


    private class PersistOrderItem extends OrderItem {
        public void setId(int id) {
            super.setId(id);
        }
    }
}
