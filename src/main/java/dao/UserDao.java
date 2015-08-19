package dao;

import entity.Client;
import entity.User;

import java.util.List;

/**
 * Created by Асет on 14.03.2015.
 */
public interface UserDao {
    public User getUserByUsernameAndPassword(String username, String password) throws PersistException;
}
