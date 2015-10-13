package action;


import dao.ProductDao;
import dao.mysql.MySqlDaoFactory;
import models.Order;
import models.Product;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class DeleteProductCartAction implements Action {

    private String page = ConfigurationManager.getProperty("page.cart");
    private ActionResult result = new ActionResult(page);
    private Map<Product, Integer> cartItems = null;

    @Override
    public ActionResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        DaoFactory factory = MySqlDaoFactory.getInstance();
        Order cart = (Order) session.getAttribute("cart");
        String productId = request.getParameter("productDeleted");


        try(Connection connection = (Connection)factory.getContext())
        {
            ProductDao productDao = (ProductDao) factory.getDao(connection, Product.class);
            Product product = productDao.getByPK(Integer.parseInt(productId), false);
            cart.update(product, "0");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
