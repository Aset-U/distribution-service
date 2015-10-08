package action;


import dao.*;
import dao.mysql.MySqlDaoFactory;
import models.*;
import resource.ConfigurationManager;
import resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

public class AddToCartAction implements Action {

    @Override
    public ActionResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        ActionResult result = null;
        short quantity = 0;

        DaoFactory factory = MySqlDaoFactory.getInstance();

        String page = request.getParameter("page");

        try(Connection connection = (Connection) factory.getContext())
        {
            result = new ActionResult("/" + page + ".jsp");
            Client client = (Client) session.getAttribute("client");
            if (client == null) {
                request.setAttribute("errorLoginPassMessage",
                        MessageManager.getProperty("message.loginerror"));

                page = ConfigurationManager.getProperty("page.login");
                result = new ActionResult(page);
                return result;
            }

            String productId = request.getParameter("productId");
            Order cart = (Order) session.getAttribute("cart");

            if (cart == null) {
                cart = new Order();
                cart.setClient(client);
                cart.setStatus(Status.DRAFT);
            }

            if (!productId.isEmpty()) {
                String productQuantity = request.getParameter("quantity");
                quantity = Short.parseShort(productQuantity);
                ProductDao productDao = (ProductDao) factory.getDao(connection, Product.class);
                Product product = productDao.getByPK(Integer.parseInt(productId), false);
                for (int i = 1; i<= quantity; i++) {
                    cart.addItem(product);
                }
            }

            session.setAttribute("cart", cart);
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NumberFormatException e) {

        }
        return result;
    }

}
