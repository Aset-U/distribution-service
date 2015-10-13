package action;

import dao.mysql.MySqlClientDao;
import dao.mysql.MySqlDaoFactory;
import models.Client;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;


public class ChangeClientPasswordAction implements Action{

    private String page = ConfigurationManager.getProperty("page.clientPassword");
    private ActionResult result = new ActionResult(page);

    @Override
    public ActionResult execute(HttpServletRequest request) {
        DaoFactory factory = MySqlDaoFactory.getInstance();
        HttpSession session = request.getSession();

        Client client = (Client) session.getAttribute("client");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String RePassword = request.getParameter("RePassword");

        try(Connection connection = (Connection) factory.getContext())
        {
            MySqlClientDao clientDao = (MySqlClientDao) factory.getDao(connection, Client.class);

            if (!currentPassword.equals(client.getPassword())) {
                request.setAttribute("errorCurrent", "wrong input the current password");
                return result;
            }
            if (!newPassword.equals(RePassword)) {
                request.setAttribute("errorNew", "do not match");
            } else {
                client.setPassword(newPassword);
                clientDao.update(client);
                session.setAttribute("client", client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
