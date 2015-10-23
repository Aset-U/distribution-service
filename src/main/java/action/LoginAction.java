package action;


import dao.*;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlOrderDao;
import dao.mysql.MySqlOrderItemDao;
import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.ConfigurationManager;
import resource.MessageManager;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class LoginAction implements Action{

    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException {
        String page =  ConfigurationManager.getProperty("page.allProducts");
        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        ActionResult result = new ActionResult(page);

        UserSearch userSearch = new UserSearchImpl();

        HttpSession session = request.getSession();
        User user = userSearch.findByUsernameAndPassword(username, password);

        if (user == null) {
            request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("message.loginerror"));
            page =  ConfigurationManager.getProperty("page.login");
            ActionResult login = new ActionResult(page);
            return login;
        }
        if (user.equals(Admin.class)) {
            page = ConfigurationManager.getProperty("page.admin");
            ActionResult adminForm = new ActionResult(page);
            session.setAttribute("admin", user);
            return adminForm;
        }
        else if (user.equals(Forwarder.class)) {
            page = ConfigurationManager.getProperty("page.forwarder");
            ActionResult forwarderForm = new ActionResult(page);
            session.setAttribute("forwarder", user);

            return forwarderForm;
        }

        initializeClient((Client) user, session);
        session.setAttribute("client", user);

        return result;
    }

    private void initializeClient(Client client, HttpSession session) {
        DaoFactory factory = MySqlDaoFactory.getInstance();
        try(Connection connection = (Connection) factory.getContext())
        {
            OrderDao orderDao = (OrderDao) factory.getDao(connection, Order.class);
            OrderItemDao orderItemDao = (OrderItemDao) factory.getDao(connection, OrderItem.class);
            ShopDao shopDao = (ShopDao) factory.getDao(connection, Shop.class);
            List<Shop> shops = shopDao.findByManager(client.getId());

            Order cart = null;
            List<Order> orders = orderDao.findByClient(client.getId());

            if (orders != null) {

                for (Order o : orders) {
                    if (o.getStatus().equals(Status.DRAFT)) {
                        cart = o;
                    }
                }
                if (cart != null) {
                    List<OrderItem> orderItems = orderItemDao.findByOrderId(cart.getId());
                    if (orderItems != null) {
                        cart.setItems(orderItems);
                    }
                    session.setAttribute("cart", cart);
                }

            }
            session.setAttribute("shops", shops);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
