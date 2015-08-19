package action;


import dao.DaoFactory;
import dao.OrderDao;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlOrderItemDao;
import entity.Order;
import entity.OrderItem;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ShowOrderAction implements Action {

    private String page = ConfigurationManager.getProperty("page.orderDetails");
    private ActionResult result = new ActionResult(page);

    @Override
    public ActionResult execute(HttpServletRequest request) {
        DaoFactory factory = MySqlDaoFactory.getInstance();
        String orderId = request.getParameter("orderId");
        int id = Integer.parseInt(orderId);

        try(Connection connection = (Connection) factory.getContext())
        {

            OrderDao orderDao = (OrderDao) factory.getDao(connection, Order.class);

            Order order = orderDao.getByPK(id, true);
            System.out.println(order.getItems());
            HttpSession session = request.getSession();
            session.setAttribute("clientOrder", order);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
