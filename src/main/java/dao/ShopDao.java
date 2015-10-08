package dao;


import models.Shop;

import java.util.List;

public interface ShopDao {
    public Shop getShopByName(String name) throws PersistException;
    public Shop getShopByAddress(String address) throws PersistException;
    public List<Shop>  getShopsByManager(int clientId) throws PersistException;
    public List<Shop> getShopsByForwarder(int forwarderId) throws PersistException;
}
