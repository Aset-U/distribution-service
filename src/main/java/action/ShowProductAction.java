package action;


import dao.DaoFactory;
import dao.ProductDao;
import dao.mysql.MySqlDaoFactory;
import entity.Product;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

public class ShowProductAction implements Action{
    private String page =  ConfigurationManager.getProperty("page.productDetails");
    private ActionResult result = new ActionResult(page);

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException {
        DaoFactory factory = MySqlDaoFactory.getInstance();
        Connection connection = (Connection) factory.getContext();
        String productId = request.getParameter("productId");
        Product product = null;
        if (!productId.isEmpty()) {
            ProductDao productDao = (ProductDao) factory.getDao(connection, Product.class);
            product = productDao.getByPK(Integer.parseInt(productId), false);
        }
        HttpSession session = request.getSession();
        session.setAttribute("product", product);
        return result;
    }
}
