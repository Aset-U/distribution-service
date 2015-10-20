package dao;


import entity.Order;
import entity.Status;

import java.util.List;

public interface OrderDao extends GenericDao<Order, Integer> {

    public Order getByPKWithItems(Integer orderId, boolean items) throws PersistException;

    public List<Order> findByClient(Integer clientId) throws PersistException;

    public Order findByStatus(Status status) throws PersistException;

}
