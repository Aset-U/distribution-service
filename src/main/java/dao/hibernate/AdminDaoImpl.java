package dao.hibernate;

import dao.AdminDao;
import models.Admin;

public class AdminDaoImpl extends GenericDaoImpl<Admin, Integer> implements AdminDao {

    @Override
    public Admin findByUsernameAndPassword(String username, String password) {
        return null;
    }
}
