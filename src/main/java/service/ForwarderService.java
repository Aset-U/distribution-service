package service;


import models.Forwarder;

public interface ForwarderService extends GenericService<Forwarder,Integer>{
    public Forwarder getByUsernameAndPassword(String username, String password);
}
