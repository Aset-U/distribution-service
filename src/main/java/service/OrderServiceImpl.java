package service;


import dao.OrderDao;
import dao.hibernate.OrderDaoImpl;
import models.Order;
import models.Status;

import java.util.List;

public class OrderServiceImpl extends GenericServiceImpl<Order, Integer> implements OrderService{

    private OrderDao orderDao;

    public OrderServiceImpl() {
        this.orderDao = new OrderDaoImpl();
    }

    public List<Order> getAllByClient(Integer clientId) {
        orderDao.openCurrentSession();
        List<Order> orders = orderDao.getAllByClient(clientId);
        orderDao.closeCurrentSession();
        return orders;
    }

    public Order getByStatus(Status status) {
        orderDao.openCurrentSession();
        Order order = orderDao.findByStatus(status);
        orderDao.closeCurrentSession();
        return order;
    }
}
