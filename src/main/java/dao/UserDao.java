package dao;

import models.User;


public interface UserDao {
    public User findByUsernameAndPassword(String username, String password);
}
