package action;


import entity.Order;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UpdateProductAction implements Action{
    private String page = ConfigurationManager.getProperty("page.cart");
    private ActionResult result = new ActionResult(page);

    @Override
    public ActionResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Order cart = (Order) session.getAttribute("cart");
        return result;
    }
}
