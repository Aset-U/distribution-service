package dao.hibernate;


import dao.OrderItemDao;
import models.OrderItem;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class OrderItemDaoImpl extends GenericDaoImpl<OrderItem, Integer> implements OrderItemDao{

    @Override
    public List<OrderItem> getAllByOrder(int orderId) {

        List<OrderItem> orderItems = null;

        Criteria criteria = getCurrentSession().createCriteria(OrderItem.class, "item");
        criteria.createCriteria("item.order", "order")
        .add(Restrictions.eq("order.id", orderId));

        orderItems = criteria.list();

        return orderItems;
    }
}
