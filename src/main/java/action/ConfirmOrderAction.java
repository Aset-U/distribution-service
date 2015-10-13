package action;


import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlOrderDao;
import dao.mysql.MySqlOrderItemDao;
import dao.mysql.MySqlShopDao;
import models.*;
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
            MySqlShopDao shopDao = (MySqlShopDao) factory.getDao(connection, Shop.class);
            MySqlOrderDao orderDao = (MySqlOrderDao) factory.getDao(connection, Order.class);
            MySqlOrderItemDao orderItemDao = (MySqlOrderItemDao) factory.getDao(connection, OrderItem.class);
            Shop shop = shopDao.getByPK(Integer.parseInt(shopId));

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
                persistOrderItems = orderItemDao.getByOrderId(checkout.getId());
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

            List<Order> allOrders = orderDao.getByClientId(client.getId());
            List<Order> clientOrders = new ArrayList<>();

            for (Order ord : allOrders) {
                if (!ord.getStatus().equals(Status.DRAFT)) {
                    clientOrders.add(ord);
                }
            }
            for (Order cOrder : clientOrders) {
                List<OrderItem> items = orderItemDao.getByOrderId(cOrder.getId());
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
