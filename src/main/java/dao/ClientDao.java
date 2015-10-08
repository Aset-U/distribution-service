package dao;

import models.Client;

import java.util.List;


public interface ClientDao {
    Client getByPK(Integer productId) throws PersistException;
    public List<Client> getAll() throws PersistException;
}
