package servlet;


import com.sun.deploy.net.HttpRequest;
import dao.*;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlOrderDao;
import dao.mysql.MySqlOrderItemDao;
import entity.*;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@WebListener()
public class SessionListener implements ServletRequestListener, HttpSessionListener {
    DaoFactory factory = MySqlDaoFactory.getInstance();
    int page = 1;
    int recordsPerPage = 6;


    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        try(Connection connection = (Connection) factory.getContext()) {
            ProductDao productDao = (ProductDao) factory.getDao(connection, Product.class);
            List<Product> products = productDao.getForPage((page - 1) * recordsPerPage, recordsPerPage);
            request.setAttribute("allProducts", products);
            request.setAttribute("currentPage", page);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        try(Connection connection = (Connection) factory.getContext()) {

            CategoryDao categoryDao = (CategoryDao) factory.getDao(connection, Category.class);

            List<Category> categories = categoryDao.getAll();
            //List<Product> products = productDao.getForPage();
          //  session.setAttribute("allProducts", products);

            session.setAttribute("categories", categories);
        }catch (SQLException e) {
            e.printStackTrace();
        }

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
            OrderDao orderDao = (OrderDao) factory.getDao(connection, Order.class);
            OrderItemDao orderItemDao = (OrderItemDao) factory.getDao(connection, OrderItem.class);
            Order persistCart = null;
            if (cart.getId() == null) {
                persistCart = orderDao.create();
                persistCart.setClient(client);
                orderDao.update(persistCart);
            }
            else {
                persistOrderItems = orderItemDao.findByOrderId(cart.getId());
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

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {


    }


}
