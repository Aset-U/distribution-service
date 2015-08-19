package action;


public class ActionResult {
    private String view;
    private boolean redirection;

    public ActionResult(String view) {
        this.view = view;
    }

    public ActionResult(String view, boolean redirection) {
        this.view = view;
        this.redirection = redirection;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public boolean isRedirection() {
        return redirection;
    }

    public void setRedirection(boolean redirection) {
        this.redirection = redirection;
    }
}
