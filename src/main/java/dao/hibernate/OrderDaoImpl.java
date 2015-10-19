package dao.hibernate;

import dao.OrderDao;
import models.Order;
import models.Status;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class OrderDaoImpl extends GenericDaoImpl<Order, Integer> implements OrderDao{

    @Override
    public List<Order> getAllByClient(Integer clientId) {
        List<Order> orders = null;

        Criteria criteria = getCurrentSession().createCriteria(Order.class, "order");
        criteria.createCriteria("order.client", "client")
        .add(Restrictions.eq("client.id", clientId));

        orders = criteria.list();

        return orders;
    }

    @Override
    public Order findByStatus(Status status) {
        Order order = null;
        Criteria criteria = getCurrentSession().createCriteria(Order.class, "order");
        criteria.add(Restrictions.eq("order.status", status));

        order = (Order) criteria.uniqueResult();

        return order;
    }
}
