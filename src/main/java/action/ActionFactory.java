package action;

import resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public class ActionFactory {
    static Map<String, Action> actions = new HashMap<>();

    static
    {
        actions.put("login", new LoginAction());
        actions.put("logout", new LogOutAction());
        actions.put("register", new ClientRegisterAction());
        actions.put("category", new CategoryProductsAction());
        actions.put("addToCart", new AddToCartAction());
        actions.put("product", new ShowProductAction());
        actions.put("logout", new LogOutAction());
        actions.put("cart", new ShowCartAction());
        actions.put("deleteProductCart", new DeleteProductCartAction());
        actions.put("checkout", new CheckOutAction());
        actions.put("confirmOrder", new ConfirmOrderAction());
        actions.put("order", new ShowOrderAction());
        actions.put("orders", new ShowAllOrdersAction());
    }

    public static Action getAction(HttpServletRequest request) {
        Action action = null;

        String actionCommand = request.getParameter("command");

        try {
                action = actions.get(actionCommand);
            } catch (IllegalArgumentException e) {
                request.setAttribute("wrongAction", actionCommand
                        + MessageManager.getProperty("message.wrongaction"));
            }


        return action;
    }
}
