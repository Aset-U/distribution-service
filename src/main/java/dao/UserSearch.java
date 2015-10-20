package dao;

import entity.Client;
import entity.User;

import java.util.List;

/**
 * Created by Асет on 14.03.2015.
 */
public interface UserSearch {
    public User findByUsernameAndPassword(String username, String password) throws PersistException;
}
