package action;


import dao.DaoFactory;
import dao.OrderDao;
import dao.mysql.MySqlDaoFactory;
import models.Order;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

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
            HttpSession session = request.getSession();
            session.setAttribute("clientOrder", order);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
