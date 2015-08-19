package dao.mysql;

import dao.*;
import entity.Admin;
import entity.Client;
import entity.Forwarder;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


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
