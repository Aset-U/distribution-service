package dao;

import entity.Client;

import java.util.List;


public interface ClientDao {
    Client getByPK(Integer productId) throws PersistException;
    public List<Client> getAll() throws PersistException;
}
