package action;

import dao.*;
import dao.mysql.MySqlCategoryDao;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlProductDao;
import entity.Category;
import entity.Product;

import javax.servlet.http.HttpServletRequest;

public class CreateProductAction implements Action{

    ActionResult productTable = new ActionResult("productTable",true);

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException {
        DaoFactory factory = MySqlDaoFactory.getInstance();
        GenericDao productDao = (MySqlProductDao) factory.getDao(factory.getContext(), Product.class);
        CategoryDao categoryDao = (MySqlCategoryDao) factory.getDao(factory.getContext(),Category.class);

        try {
            String productName = request.getParameter("productName");
            int quantity = Integer.parseInt(request.getParameter("productQuantity"));
            double price = Double.parseDouble(request.getParameter("productPrice"));
            Category category = categoryDao.getName(request.getParameter("categoryName")) ;

            Product product = (Product) productDao.create();
            product.setName(productName);
            product.setPrice(price);
            product.setCategory(category);

        }catch (PersistException e){
            throw new ActionException(e.getCause());
        }

        return productTable;
    }
}
