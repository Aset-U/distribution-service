package service;


import models.Shop;

import java.util.List;

public interface ShopService extends GenericService<Shop,Integer>{
    public Shop getByName(String name);
    public Shop getByAddress(String address);
    public List<Shop> getAllByManager(Integer clientId);
}
