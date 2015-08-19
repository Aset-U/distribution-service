package dao.mysql;

import dao.DaoFactory;
import dao.PersistException;
import dao.ShopDao;
import entity.Client;
import entity.Forwarder;
import entity.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class MySqlShopDao extends AbstractJDBCDao<Shop, Integer> implements ShopDao {

    private class PersistShop extends Shop {
        public void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public Shop create() throws PersistException {
        Shop s = new Shop();
        return persist(s);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, name, address, phone, manager_id FROM shops ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO shops (name, address, phone, manager_id) \n" +
                "VALUES (?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return  "UPDATE shops \n" +
                "SET name = ?, address = ?, phone = ?, manager_id = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM shops WHERE id= ?;";
    }

    public MySqlShopDao (DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
      //   addRelation(Shop.class, "manager");
    }
    @Override
    public Shop getShopByName(String name) throws PersistException {
        List<Shop> list;
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

    @Override
    public Shop getShopByAddress(String address) throws PersistException {
        List<Shop> list;
        String sql = getSelectQuery();
        sql += " WHERE address = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, address);
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
    public List<Shop> getShopsByManager(int clientId) throws PersistException {
        List<Shop> list;
        String sql = getSelectQuery();
        sql += " WHERE manager_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientId);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }

        return list;
    }

    @Override
    public List<Shop> getShopsByForwarder(int forwarderId) throws PersistException {
        List<Shop> list;
        String sql = getSelectQuery();
        sql += " INNER JOIN shop_forwarder ON shop.id = shop_forwarder.shop_id " +
                "INNER JOIN forwarders ON shop_forwarder.forwarder_id = forwarders.id" +
                " WHERE forwarders.id = " + forwarderId;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    protected List<Shop> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Shop> result = new LinkedList<Shop>();
        try {
            while (rs.next()) {
                PersistShop shop = new PersistShop();
                shop.setId(rs.getInt("id"));
                shop.setName(rs.getString("name"));
                shop.setAddress(rs.getString("address"));
                shop.setPhone(rs.getString("phone"));
                shop.setManager((Client) getDependence(Client.class, rs.getInt("manager_id")));
                result.add(shop);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Shop object) throws PersistException {
        try {
            int managerId = (object.getManager() == null || object.getManager().getId() == null) ? -1
                    : object.getManager().getId();
            statement.setString(1, object.getName());
            statement.setString(2, object.getAddress());
            statement.setString(3, object.getPhone());
            statement.setInt(4, managerId);
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Shop object) throws PersistException {
        try {
            int managerId = (object.getManager() == null || object.getManager().getId() == null) ? -1
                    : object.getManager().getId();
            statement.setString(1, object.getName());
            statement.setString(2, object.getAddress());
            statement.setString(3, object.getPhone());
            statement.setInt(4, managerId);
            statement.setInt(5, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }





}
