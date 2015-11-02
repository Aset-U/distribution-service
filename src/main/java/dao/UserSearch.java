package dao;

import models.User;


public interface UserSearch {
    public User findByUsernameAndPassword(String username, String password);
}
