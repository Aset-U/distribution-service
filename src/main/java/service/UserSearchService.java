package service;

import models.User;


public interface UserSearchService {

    public User getByUsernameAndPassword(String username, String password);
}
