package dao.hibernate;

import dao.ClientDao;
import models.Client;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class ClientDaoImpl extends GenericDaoImpl<Client, Integer> implements ClientDao{

    @Override
    public Client findByUsernameAndPassword(String username, String password) {
        Client client = null;

        Criteria criteria = getCurrentSession().createCriteria(Client.class);
        criteria.add(Restrictions.eq("username", username))
                .add(Restrictions.eq("password", password));

        client = (Client) criteria.uniqueResult();

        return client;
    }
}
