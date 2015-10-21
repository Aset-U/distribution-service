package action;


import dao.DaoFactory;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlProductDao;
import entity.Product;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PaginationForProductsAction implements Action {
    private String page = ConfigurationManager.getProperty("page.allProducts");
    private ActionResult result = new ActionResult(page);

    @Override
    public ActionResult execute(HttpServletRequest request) {
        int page = 1;
        int recordsPerPage = 6;
        DaoFactory factory = MySqlDaoFactory.getInstance();

        if(request.getParameter("paging") != null)
            page = Integer.parseInt(request.getParameter("paging"));

        try(Connection connection = (Connection) factory.getContext())
        {
            MySqlProductDao dao = (MySqlProductDao) factory.getDao(connection, Product.class);
            List<Product> products = dao.getForPage((page - 1) * recordsPerPage, recordsPerPage);
            request.setAttribute("allProducts", products);
            request.setAttribute("currentPage", page);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
