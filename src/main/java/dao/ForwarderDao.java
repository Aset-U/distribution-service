package dao;



import entity.Forwarder;

import java.util.List;

public interface ForwarderDao extends GenericDao<Forwarder, Integer>, UserSearch {

    public List<Forwarder> findByShop(int shopId) throws PersistException;

    public List<Forwarder> findByCar(int carId) throws PersistException;
}
