package dao;

import models.Client;

import java.util.List;

public interface ClientDao extends GenericDao<Client, Integer>{
    public Client findByUsernameAndPassword(String username, String password);
}
