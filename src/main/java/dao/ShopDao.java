package dao;


import entity.Shop;

import java.util.List;

public interface ShopDao extends GenericDao<Shop, Integer> {

    public Shop findByName(String name) throws PersistException;

    public Shop findByAddress(String address) throws PersistException;

    public List<Shop> findByManager(int clientId) throws PersistException;

    public List<Shop> findShopsByForwarder(int forwarderId) throws PersistException;
}
