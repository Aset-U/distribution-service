package dao.mysql;

import dao.*;
import models.Admin;
import models.Client;
import models.Forwarder;
import models.User;

import java.sql.Connection;


public class MySqlUserDao  implements UserDao {


    public MySqlUserDao() {

    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws PersistException {
        DaoFactory factory = new MySqlDaoFactory();
        Connection connection = (Connection)factory.getContext();
        MySqlClientDao clientDao = (MySqlClientDao) factory.getDao(connection, Client.class);
        MySqlForwarderDao forwarderDao = (MySqlForwarderDao) factory.getDao(connection, Forwarder.class);
        MySqlAdminDao adminDao = (MySqlAdminDao) factory.getDao(connection, Admin.class);
        Client findClient = clientDao.getUserByUsernameAndPassword(username, password);
        Admin findAdmin = adminDao.getUserByUsernameAndPassword(username, password);
        Forwarder findForwarder = forwarderDao.getUserByUsernameAndPassword(username, password);

       if (findClient != null) {

            return findClient;

        } else if (findForwarder != null) {

            return findForwarder;
        }
        else if (findAdmin != null){

            return findAdmin;
        }
        return null;
    }
}
