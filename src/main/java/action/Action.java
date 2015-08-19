package action;


import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public interface Action {
    ActionResult execute(HttpServletRequest request);
}
