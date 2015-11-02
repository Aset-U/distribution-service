package service;


import models.Order;
import models.Status;

import java.util.List;

public interface OrderService extends GenericService<Order,Integer> {
    public List<Order> getAllByClient(Integer clientId);
    public Order getByStatus(Status status);
}
