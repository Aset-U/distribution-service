package dao.hibernate;


import dao.OrderItemDao;
import models.OrderItem;

import java.util.List;

public class OrderItemDaoImpl extends GenericDaoImpl<OrderItem, Integer> implements OrderItemDao{

    @Override
    public List<OrderItem> getAllByOrder(int orderId) {
        return null;
    }
}
