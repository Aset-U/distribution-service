package servlet;


import dao.CategoryDao;
import dao.DaoFactory;
import dao.ProductDao;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlOrderDao;
import dao.mysql.MySqlOrderItemDao;
import entity.*;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@WebListener()
public class SessionListener implements HttpSessionListener {
    DaoFactory factory = MySqlDaoFactory.getInstance();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        DaoFactory factory = MySqlDaoFactory.getInstance();
        Connection connection = (Connection) factory.getContext();
        ProductDao productDao = (ProductDao) factory.getDao(connection, Product.class);
        CategoryDao categoryDao = (CategoryDao) factory.getDao(connection, Category.class);

        List<Product> products = productDao.getAll();
        List<Category> categories = categoryDao.getAllCategories();

        session.setAttribute("allProducts", products);
        session.setAttribute("categories", categories);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        initializePersistCart(session);

    }

    private void initializePersistCart(HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        Order cart = (Order) session.getAttribute("cart");
        List<OrderItem> sessionOrderItems = cart.getItems();
        List<OrderItem> persistOrderItems = null;
        try(Connection connection = (Connection) factory.getContext())
        {
            MySqlOrderDao orderDao = (MySqlOrderDao) factory.getDao(connection, Order.class);
            MySqlOrderItemDao orderItemDao = (MySqlOrderItemDao) factory.getDao(connection, OrderItem.class);
            Order persistCart = null;
            if (cart.getId() == null) {
                persistCart = orderDao.create();
                persistCart.setClient(client);
                orderDao.update(persistCart);
            }
            else {
                persistOrderItems = orderItemDao.getByOrderId(cart.getId());
            }

            if (persistOrderItems == null) {
                for (OrderItem item : sessionOrderItems) {
                    OrderItem cartItem = orderItemDao.create();
                    cartItem.setProduct(item.getProduct());
                    cartItem.setQuantity(item.getQuantity());
                    if (persistCart != null) {
                        cartItem.setOrder(persistCart);
                    }
                    else {
                        cartItem.setOrder(cart);
                    }
                    orderItemDao.update(cartItem);

                }
            }
            if (sessionOrderItems.size() >= 0 && persistOrderItems.size() > 0) {
                for (OrderItem item : sessionOrderItems) {
                    if (persistOrderItems.contains(item)) {
                        orderItemDao.update(item);
                    }
                    else {
                        OrderItem cartItem = orderItemDao.create();
                        cartItem.setOrder(cart);
                        cartItem.setProduct(item.getProduct());
                        cartItem.setQuantity(item.getQuantity());
                        orderItemDao.update(cartItem);
                    }
                }
                for (OrderItem pItem : persistOrderItems) {
                    if (!sessionOrderItems.contains(pItem)) {
                        orderItemDao.delete(pItem);
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
