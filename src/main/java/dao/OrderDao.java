package dao;


import models.Order;
import models.Status;

import java.util.List;

public interface OrderDao extends GenericDao<Order, Integer> {
    public List<Order> getAllByClient(Integer clientId);
    public Order findByStatus(Status status);
}
