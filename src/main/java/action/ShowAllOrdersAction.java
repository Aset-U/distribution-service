package action;


import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlOrderDao;
import dao.mysql.MySqlOrderItemDao;
import models.Client;
import models.Order;
import models.OrderItem;
import models.Status;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowAllOrdersAction implements Action {
    private String page = ConfigurationManager.getProperty("page.orders");
    private ActionResult result = new ActionResult(page);

    @Override
    public ActionResult execute(HttpServletRequest request) {
        DaoFactory factory = MySqlDaoFactory.getInstance();
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");

        try(Connection connection = (Connection) factory.getContext())
        {
            MySqlOrderDao orderDao = (MySqlOrderDao) factory.getDao(connection, Order.class);
            MySqlOrderItemDao orderItemDao = (MySqlOrderItemDao) factory.getDao(connection, OrderItem.class);
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
