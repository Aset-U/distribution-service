package service;

import dao.AdminDao;
import dao.GenericDao;
import dao.hibernate.AdminDaoImpl;
import models.Admin;

public class AdminServiceImpl extends GenericServiceImpl<Admin, Integer> implements AdminService{

    private AdminDao adminDao;

    public AdminServiceImpl(){
        this.adminDao = new AdminDaoImpl();
    }

    public Admin getByUsernameAndPassword(String username, String password) {
        adminDao.openCurrentSession();
        Admin admin = adminDao.findByUsernameAndPassword(username, password);
        adminDao.closeCurrentSession();
        return admin;
    }
}
