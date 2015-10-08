package dao;

import models.User;

/**
 * Created by Асет on 14.03.2015.
 */
public interface UserDao {
    public User getUserByUsernameAndPassword(String username, String password) throws PersistException;
}
