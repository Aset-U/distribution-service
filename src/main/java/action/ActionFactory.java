package action;

import resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public class ActionFactory {

    private static Map<String, Action> actions = new HashMap<>();

    private ActionFactory() {
        initialActions();
    }

    private static class SingletonHolder {
        public static final ActionFactory instance = new ActionFactory();
    }

    public static ActionFactory getInstance()  {
        return SingletonHolder.instance;
    }
    private static void initialActions()  {
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
        actions.put("changeClientPassword", new ChangeClientPasswordAction());
        actions.put("shopList", new ShowClientShopsAction());
        actions.put("addClientShop", new AddShopAction());
        actions.put("deleteClientShop", new DeleteShopAction());
    }

    public Action getAction(HttpServletRequest request) {
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
