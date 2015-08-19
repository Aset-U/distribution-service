package action;

import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutAction implements Action {
    String page =  ConfigurationManager.getProperty("page.index");
    private ActionResult home = new ActionResult(page, true);

    @Override
    public ActionResult execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.removeAttribute("password");
        session.invalidate();
        return home;
    }
}
