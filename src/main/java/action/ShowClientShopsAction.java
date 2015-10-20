package action;


import dao.DaoFactory;
import dao.ShopDao;
import dao.mysql.MySqlDaoFactory;
import entity.Client;
import entity.Shop;
import resource.ConfigurationManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ShowClientShopsAction implements Action{

    private String page =  ConfigurationManager.getProperty("page.clientShopsInfo");
    private ActionResult result = new ActionResult(page);

    @Override
    public ActionResult execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        DaoFactory factory = MySqlDaoFactory.getInstance();
        Client client = (Client) session.getAttribute("client");

        try (Connection connection = (Connection) factory.getContext())
        {
            ShopDao shopDao = (ShopDao) factory.getDao(connection, Shop.class);
            List<Shop> shopList = shopDao.findByManager(client.getId());

            session.setAttribute("shopList", shopList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
