package service;


import dao.OrderItemDao;
import dao.hibernate.OrderItemDaoImpl;
import models.OrderItem;

import java.util.List;

public class OrderItemServiceImpl extends GenericServiceImpl<OrderItem, Integer> implements OrderItemService{

    private OrderItemDao orderItemDao;

    public OrderItemServiceImpl() {
        this.orderItemDao = new OrderItemDaoImpl();
    }

    public List<OrderItem> getAllByOrder(int orderId) {
        orderItemDao.openCurrentSession();
        List<OrderItem> orderItems = orderItemDao.getAllByOrder(orderId);
        orderItemDao.closeCurrentSession();
        return orderItems;
    }
}
