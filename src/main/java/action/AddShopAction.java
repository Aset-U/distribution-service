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

public class AddShopAction implements Action {
    private String shopEditPage =  ConfigurationManager.getProperty("page.editShop");
    private ActionResult result = new ActionResult(shopEditPage);

    @Override
    public ActionResult execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        DaoFactory factory = MySqlDaoFactory.getInstance();

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        Client client = (Client) session.getAttribute("client");

        try(Connection connection = (Connection) factory.getContext())
        {
            if (!name.isEmpty() && !address.isEmpty() && !phone.isEmpty()) {

                ShopDao shopDao = (ShopDao) factory.getDao(connection, Shop.class);

                Shop newShop = shopDao.create();
                newShop.setName(name);
                newShop.setAddress(address);
                newShop.setPhone(phone);
                newShop.setManager(client);
                shopDao.update(newShop);

                List<Shop> shopList = shopDao.findByManager(client.getId());
                session.setAttribute("shops", shopList);

            } else {
                if (name.isEmpty()){
                    request.setAttribute("nullNameError", "Please enter shop name");
                }
                if (address.isEmpty()){
                    request.setAttribute("nullAddressError", "Please enter shop address");
                }
                if (phone.isEmpty()){
                    request.setAttribute("nullPhoneError", "Please enter shop phone");
                }

                result = new ActionResult(shopEditPage);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
