package dao;


import models.Admin;

public interface AdminDao extends GenericDao<Admin, Integer> {
    public Admin findByUsernameAndPassword(String username, String password);
}
