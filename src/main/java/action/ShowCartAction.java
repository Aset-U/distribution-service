package action;


import entity.Client;
import entity.Order;
import entity.Product;
import resource.ConfigurationManager;
import resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.*;

public class ShowCartAction implements Action{

    private String page = ConfigurationManager.getProperty("page.cart");
    private ActionResult result = new ActionResult(page);


    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException {

        HttpSession session = request.getSession();
        /*Client client = (Client) session.getAttribute("client");

        if (client == null) {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));

            page = ConfigurationManager.getProperty("page.login");
            ActionResult login = new ActionResult(page);
            return login;
        }*/

        String clear = request.getParameter("clear");

        if ((clear != null) && clear.equals("true")) {

            Order cart = (Order) session.getAttribute("cart");
            cart.clear();
        }
        return result;
    }


}
