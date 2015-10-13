package dao;


import models.Shop;

import java.util.List;

public interface ShopDao extends GenericDao<Shop, Integer>{
    public Shop findByName(String name);
    public Shop findByAddress(String address);
    public List<Shop> getAllByManager(Integer clientId);
}
