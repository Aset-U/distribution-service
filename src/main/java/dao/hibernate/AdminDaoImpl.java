package dao.hibernate;

import dao.AdminDao;
import models.Admin;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class AdminDaoImpl extends GenericDaoImpl<Admin, Integer> implements AdminDao {

    public Admin findByUsernameAndPassword(String username, String password) {
        Admin admin = null;

        Criteria criteria = getCurrentSession().createCriteria(Admin.class);
        criteria.add(Restrictions.eq("username", username))
        .add(Restrictions.eq("password", password));

        admin = (Admin) criteria.uniqueResult();

        return admin;
    }
}
