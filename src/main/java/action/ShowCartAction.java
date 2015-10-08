package action;


import models.Client;
import models.Order;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShowCartAction implements Action{

    private String page = ConfigurationManager.getProperty("page.cart");
    private ActionResult result = new ActionResult(page);


    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException {

        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");

        if (client == null) {
            page = ConfigurationManager.getProperty("page.login");
            ActionResult login = new ActionResult(page);
            return login;
        }

        String clear = request.getParameter("clear");

        if ((clear != null) && clear.equals("true")) {

            Order cart = (Order) session.getAttribute("cart");
            cart.clear();
        }
        return result;
    }


}
