package action;


import dao.ClientDao;
import dao.DaoFactory;
import dao.PersistException;
import dao.UserDao;
import dao.mysql.AbstractJDBCDao;
import dao.mysql.MySqlClientDao;
import dao.mysql.MySqlDaoFactory;
import entity.Client;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRegisterAction implements Action{
    ActionResult registration = new ActionResult("client_registration");
    ActionResult welcome = new ActionResult("home", true);

    @Override
    public ActionResult execute(HttpServletRequest request) throws ActionException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String phoneNumber = request.getParameter("phone_number");
        String email = request.getParameter("email");

        DaoFactory factory = MySqlDaoFactory.getInstance();
        MySqlClientDao clientDao = null;

        try(Connection connection = (Connection) factory.getContext())
        {
          /*  clientDao = (MySqlClientDao) factory.getDao(connection,Client.class);
            List<Client> clients = new ArrayList<>(clientDao.getAll());
            for (Client client : clients) {
                if (client.getUsername().equals(username)) {
                    request.setAttribute("username_error", "Пользователь с таким именем уже существует!");
                    return registration;
                }
            }*/

            Client newClient = clientDao.create();
            newClient.setUsername(username);
            newClient.setPassword(password);
            newClient.setFirstName(firstName);
            newClient.setLastName(lastName);
            newClient.setPhoneNumber(phoneNumber);
            newClient.setEmail(email);
            clientDao.update(newClient);

        } catch (PersistException e) {
            throw new ActionException("User error",e.getCause());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        HttpSession session = request.getSession();
        return welcome;
    }
}
