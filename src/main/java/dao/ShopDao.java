package dao;


import entity.Shop;

import java.util.List;

public interface ShopDao {
    public Shop getByName(String name) throws PersistException;
    public Shop getByAddress(String address) throws PersistException;
    public List<Shop>  getShopsByManager(int clientId) throws PersistException;
    public List<Shop> getShopsByForwarder(int forwarderId) throws PersistException;
}
