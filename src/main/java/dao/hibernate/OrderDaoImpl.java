package dao.hibernate;

import dao.OrderDao;
import models.Order;
import models.Status;

import java.util.List;

public class OrderDaoImpl extends GenericDaoImpl<Order, Integer> implements OrderDao{

    @Override
    public List<Order> getAllByClient(Integer clientId) {
        return null;
    }

    @Override
    public Order findByStatus(Status status) {
        return null;
    }
}
