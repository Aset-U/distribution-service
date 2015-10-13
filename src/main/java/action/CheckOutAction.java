package action;


import dao.mysql.MySqlDaoFactory;
import models.*;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

public class CheckOutAction implements Action{

    private String checkoutPage = ConfigurationManager.getProperty("page.checkout");
    private String cartPage = ConfigurationManager.getProperty("page.cart");
    private ActionResult result = new ActionResult(checkoutPage);

    @Override
    public ActionResult execute(HttpServletRequest request) {
        DaoFactory factory = MySqlDaoFactory.getInstance();
        HttpSession session = request.getSession();

        Order cart = (Order) session.getAttribute("cart");

        if (cart == null || cart.getItems().size() == 0) {
            result = new ActionResult(cartPage);
            request.setAttribute("warning", "Warning! your shopping cart is empty");
            return result;
        }
        try(Connection connection = (Connection) factory.getContext())
        {
            Client client = (Client) session.getAttribute("client");

            if (cart != null) {
                Order order = new Order();
                order.setId(cart.getId());
                order.setClient(client);
                order.setItems(cart.getItems());
                session.setAttribute("checkout", order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
