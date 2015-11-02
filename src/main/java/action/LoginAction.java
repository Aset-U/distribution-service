package action;



import service.*;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.ConfigurationManager;
import resource.MessageManager;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public class LoginAction implements Action{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAction.class);
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException {
        String page =  ConfigurationManager.getProperty("page.allProducts");
        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        ActionResult result = new ActionResult(page);

        UserSearchService userSearchService = new UserSearchServiceImpl();

        HttpSession session = request.getSession();
        User user = userSearchService.getByUsernameAndPassword(username, password);

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

        ShopService shopService = new ShopServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        OrderItemService orderItemService = new OrderItemServiceImpl();
        Order cart = null;

        List<Shop> shops = shopService.getAllByManager(client.getId());
        List<Order> orders = orderService.getAllByClient(client.getId());

        if (orders != null) {

            for (Order o : orders) {
                if (o.getStatus().equals(Status.DRAFT)) {
                    cart = o;
                }
            }
            if (cart != null) {
                List<OrderItem> orderItems = orderItemService.getAllByOrder(cart.getId());
                if (orderItems != null) {
                    cart.setItems(orderItems);
                }
                session.setAttribute("cart", cart);
            }
        }
        session.setAttribute("shops", shops);
    }
}
