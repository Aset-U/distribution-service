package action;


import dao.DaoFactory;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.ShopDao;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlOrderDao;
import dao.mysql.MySqlOrderItemDao;
import dao.mysql.MySqlShopDao;
import entity.*;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderAction implements Action {

    private String page = ConfigurationManager.getProperty("page.orders");
    private String checkoutPage = ConfigurationManager.getProperty("page.checkout");
    private ActionResult result = new ActionResult(page);

    @Override
    public ActionResult execute(HttpServletRequest request) {
        DaoFactory factory = MySqlDaoFactory.getInstance();
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        Order checkout = (Order) session.getAttribute("checkout");
        String shopId = (String) request.getParameter("clientShop");

        Order order = new Order();

        if (checkout == null || checkout.getItems().size() == 0) {
            result = new ActionResult(checkoutPage);
            request.setAttribute("noItems", "You have no items... sorry");
            return result;
        }
        try(Connection connection = (Connection) factory.getContext())
        {
            ShopDao shopDao = (ShopDao) factory.getDao(connection, Shop.class);
            OrderDao orderDao = (OrderDao) factory.getDao(connection, Order.class);
            OrderItemDao orderItemDao = (OrderItemDao) factory.getDao(connection, OrderItem.class);
            Shop shop = shopDao.findByPK(Integer.parseInt(shopId));

            List<OrderItem> persistOrderItems = null;

            if (checkout.getId() == null) {
                order = orderDao.create();
                order.setStatus(Status.INSPECTED);
                order.setClient(client);
                order.setItems(checkout.getItems());
                order.setShop(shop);
                orderDao.update(order);
                for (OrderItem item : checkout.getItems()) {
                    OrderItem orderItem = orderItemDao.create();
                    orderItem.setOrder(order);
                    orderItem.setProduct(item.getProduct());
                    orderItem.setQuantity(item.getQuantity());
                    orderItemDao.update(orderItem);
                }

            }
            else if (session.getAttribute("cart") != null) {
                persistOrderItems = orderItemDao.findByOrderId(checkout.getId());
                checkout.setStatus(Status.INSPECTED);
                checkout.setShop(shop);
                orderDao.update(checkout);
                for (OrderItem item : checkout.getItems()) {
                    if (!persistOrderItems.contains(item)) {
                        OrderItem orderItem = orderItemDao.create();
                        orderItem.setOrder(checkout);
                        orderItem.setProduct(item.getProduct());
                        orderItem.setQuantity(item.getQuantity());
                        orderItemDao.update(orderItem);
                    }
                    }
                }

            else if (session.getAttribute("cart") == null) {
                order = orderDao.create();
                order.setStatus(Status.INSPECTED);
                order.setClient(client);
                order.setItems(checkout.getItems());
                order.setShop(shop);
                orderDao.update(order);
                for (OrderItem item : checkout.getItems()) {
                    OrderItem orderItem = orderItemDao.create();
                    orderItem.setOrder(order);
                    orderItem.setProduct(item.getProduct());
                    orderItem.setQuantity(item.getQuantity());
                    orderItemDao.update(orderItem);
                }
            }

            List<Order> allOrders = orderDao.findByClient(client.getId());
            List<Order> clientOrders = new ArrayList<>();

            for (Order o : allOrders) {
                if (!o.getStatus().equals(Status.DRAFT)) {
                    clientOrders.add(o);
                }
            }
            for (Order cOrder : clientOrders) {
                List<OrderItem> items = orderItemDao.findByOrderId(cOrder.getId());
                cOrder.setItems(items);
            }
            session.setAttribute("orders", clientOrders);
            session.removeAttribute("cart");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
