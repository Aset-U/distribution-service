package dao;



import entity.Forwarder;

import java.util.List;

public interface ForwarderDao  {

    public List<Forwarder> getForwardersByShop(int shopId) throws PersistException;

    public List<Forwarder> getForwardersByCar(int carId) throws PersistException;
}
