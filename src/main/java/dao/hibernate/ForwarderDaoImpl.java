package dao.hibernate;

import dao.ForwarderDao;
import models.Forwarder;

public class ForwarderDaoImpl extends GenericDaoImpl<Forwarder, Integer> implements ForwarderDao{
    @Override
    public Forwarder findByUsernameAndPassword(String username, String password) {
        return null;
    }
}
