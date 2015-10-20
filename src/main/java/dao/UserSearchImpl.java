package dao;

import dao.mysql.MySqlDaoFactory;
import entity.Admin;
import entity.Client;
import entity.Forwarder;
import entity.User;

import java.sql.Connection;


public class UserSearchImpl implements UserSearch {


    public UserSearchImpl() {

    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws PersistException {
        DaoFactory factory = new MySqlDaoFactory();
        Connection connection = (Connection)factory.getContext();
        ClientDao clientDao = (ClientDao) factory.getDao(connection, Client.class);
        ForwarderDao forwarderDao = (ForwarderDao) factory.getDao(connection, Forwarder.class);
        AdminDao adminDao = (AdminDao) factory.getDao(connection, Admin.class);
        Client findClient = (Client) clientDao.findByUsernameAndPassword(username, password);
        Admin findAdmin = (Admin) adminDao.findByUsernameAndPassword(username, password);
        Forwarder findForwarder = (Forwarder) forwarderDao.findByUsernameAndPassword(username, password);

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
