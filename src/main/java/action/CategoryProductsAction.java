package action;


import dao.DaoFactory;
import dao.ProductDao;
import dao.mysql.MySqlDaoFactory;
import entity.Product;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;

public class CategoryProductsAction implements Action{
    private String page =  ConfigurationManager.getProperty("page.productsByCategory");
    private ActionResult result = new ActionResult(page);

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException {
        String id = request.getParameter("categoryId");
        DaoFactory factory = MySqlDaoFactory.getInstance();
        Connection connection = (Connection)factory.getContext();
        ProductDao productDao = (ProductDao) factory.getDao(connection, Product.class);
        List<Product> products = productDao.findByCategory(Integer.parseInt(id));

        HttpSession session = request.getSession();
        session.setAttribute("categoryProducts", products);

        return result;
    }
}
