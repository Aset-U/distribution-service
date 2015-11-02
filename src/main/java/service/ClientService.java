package service;

import models.Client;

public interface ClientService extends GenericService<Client,Integer>{
    public Client getByUsernameAndPassword(String username, String password);
}
