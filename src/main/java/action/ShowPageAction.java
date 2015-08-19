package action;

import javax.servlet.http.HttpServletRequest;

public class ShowPageAction implements Action {
    private ActionResult actionResult;

    public ShowPageAction(String page) {
        actionResult = new ActionResult("/" + page);
    }
    public ActionResult execute(HttpServletRequest request) {
        return actionResult;
    }
}
