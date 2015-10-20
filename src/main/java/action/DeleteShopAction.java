package action;

import com.mysql.jdbc.Connection;
import dao.DaoFactory;
import dao.ShopDao;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlShopDao;
import entity.Client;
import entity.Shop;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class DeleteShopAction implements Action{
    private String shopsInfoPage =  ConfigurationManager.getProperty("page.clientShopsInfo");
    private ActionResult result = new ActionResult(shopsInfoPage);

    @Override
    public ActionResult execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        DaoFactory factory = MySqlDaoFactory.getInstance();
        Client client = (Client) session.getAttribute("client");
        String shopId = request.getParameter("shopDeleted");

        try(Connection connection = (Connection) factory.getContext())
        {
            ShopDao shopDao = (ShopDao) factory.getDao(connection, Shop.class);
            Shop shop  = shopDao.findByPK(Integer.parseInt(shopId));
            shopDao.delete(shop);

            List<Shop> shopList = shopDao.findByManager(client.getId());

            session.setAttribute("shops", shopList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
