package dao;



import models.Forwarder;

import java.util.List;

public interface ForwarderDao extends GenericDao<Forwarder, Integer>{
    public Forwarder findByUsernameAndPassword(String username, String password);
}
