package action;


import dao.DaoFactory;
import dao.ProductDao;
import dao.mysql.MySqlDaoFactory;
import entity.Order;
import entity.Product;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
            Product product = productDao.findByPK(Integer.parseInt(productId));
            cart.update(product, "0");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
