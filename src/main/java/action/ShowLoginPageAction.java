package action;

import entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class ShowLoginPageAction implements Action {
    private ActionResult login = new ActionResult("login");
    private ActionResult home = new ActionResult("home", true);


    @Override
    public ActionResult execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) return home;
        return login;
    }
}
