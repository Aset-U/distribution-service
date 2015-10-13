package dao;


import models.OrderItem;

import java.util.List;

public interface OrderItemDao extends GenericDao<OrderItem, Integer> {
    public List<OrderItem> getAllByOrder(int orderId);
}
