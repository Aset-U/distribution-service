package dao.mysql;

import dao.DaoFactory;
import dao.OrderDao;
import dao.PersistException;
import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class MySqlOrderDao extends AbstractJDBCDao<Order, Integer> implements OrderDao{

    public MySqlOrderDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
     //   addRelation(Order.class, "shop");
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, status, client_id, shop_id FROM distribution.order ";// дописать имя БД к таблице
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO distribution.order (status, client_id, shop_id) \n" +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return  "UPDATE distribution.order " +
                "SET status = ?, client_id = ?, shop_id = ? " +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM distribution.order WHERE id= ?;";
    }

  public Order getByPK(Integer orderId, boolean items) throws PersistException {
       Order order = getByPK(orderId);

       if (items)
       {
           List<OrderItem> orderItems = null;
           DaoFactory factory = MySqlDaoFactory.getInstance();
           try (Connection connection = (Connection) factory.getContext()) {
               MySqlOrderItemDao orderItemDao = (MySqlOrderItemDao) factory.getDao(connection, OrderItem.class);
               orderItems = orderItemDao.getByOrderId(orderId);
               order.setItems(orderItems);
           } catch (Exception e) {
               throw new PersistException(e);
           }
       }
       return order;
    }

    @Override
    public List<Order> getByClientId(Integer clientId) throws PersistException {
        List<Order> list;
        String sql = getSelectQuery();
        sql += " WHERE client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientId);
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
    public Order getByStatus(Status status) throws PersistException {
        List<Order> list;
        String sql = getSelectQuery();
        sql += " WHERE status = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, status.ordinal()+1);
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
    public List<Order> getOrdersByProductId(Integer productId) throws PersistException {
        List<Order> list;
        String sql = getSelectQuery();
        sql += " INNER JOIN product_order ON product.id = product_order.Product_id " +
                "INNER JOIN distribution.order ON product_order.Order_id = distribution.order.id" +
                " WHERE distribution.order.id = " + productId;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    protected List<Order> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Order> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistOrder order = new PersistOrder();
                order.setId(rs.getInt("id"));
                order.setStatus(Status.valueOf(rs.getString(2)));
                order.setClient((Client) getDependence(Client.class, rs.getInt("client_id")));
                order.setShop((Shop) getDependence(Shop.class, rs.getInt("shop_id")));
                result.add(order);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Order order) throws PersistException {
        try {
            int clientId = (order.getClient() == null || order.getClient().getId() == null) ? -1
                    : order.getClient().getId();
            int shopId = (order.getShop() == null || order.getShop().getId() == null) ? -1
                    : order.getShop().getId();
            statement.setInt(1, 1);
            statement.setInt(2, clientId);
            statement.setInt(3, shopId);
        }catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Order order) throws PersistException {
        try {
            int clientId = (order.getClient() == null || order.getClient().getId() == null) ? -1
                    : order.getClient().getId();
            int shopId = (order.getShop() == null || order.getShop().getId() == null) ? -1
                    : order.getShop().getId();
            statement.setInt(1, order.getStatus().ordinal()+1);
            statement.setInt(2, clientId);
            statement.setInt(3, shopId);
            statement.setInt(4, order.getId());
        }catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Order create() throws PersistException {
        Order o = new Order();
        return persist(o);
    }


    private class PersistOrder extends Order{
        public void setId(int id) {
            super.setId(id);
        }
    }
}
