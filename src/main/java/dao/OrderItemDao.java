package dao;


import entity.OrderItem;

import java.util.List;

public interface OrderItemDao extends GenericDao<OrderItem, Integer> {
    public List<OrderItem> findByOrderId(int orderId) throws PersistException;
}
