package dao.hibernate;

import dao.ForwarderDao;
import models.Forwarder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class ForwarderDaoImpl extends GenericDaoImpl<Forwarder, Integer> implements ForwarderDao{
    @Override
    public Forwarder findByUsernameAndPassword(String username, String password) {
        Forwarder forwarder = null;

        Criteria criteria = getCurrentSession().createCriteria(Forwarder.class);
        criteria.add(Restrictions.eq("username", username))
                .add(Restrictions.eq("password", password));

        forwarder = (Forwarder) criteria.uniqueResult();

        return forwarder;
    }
}
