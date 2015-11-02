package service;

import models.Admin;

public interface AdminService extends GenericService<Admin,Integer>{
    public Admin getByUsernameAndPassword(String username, String password);
}
