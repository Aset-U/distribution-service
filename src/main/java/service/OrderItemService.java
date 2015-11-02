package service;


import models.OrderItem;

import java.util.List;

public interface OrderItemService extends GenericService<OrderItem,Integer>{
     public List<OrderItem> getAllByOrder(int orderId);
}
