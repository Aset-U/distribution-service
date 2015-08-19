package dao;


import entity.Order;
import entity.Status;

import java.util.List;

public interface OrderDao {

    public Order getByPK(Integer orderId, boolean items) throws PersistException;

    public List<Order> getByClientId(Integer clientId) throws PersistException;

    public Order getByStatus(Status status) throws PersistException;

    List<Order> getOrdersByProductId(Integer productId) throws PersistException;
}
