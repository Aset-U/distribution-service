package service;


import dao.ShopDao;
import dao.hibernate.ShopDaoImpl;
import models.Shop;

import java.util.List;

public class ShopServiceImpl extends GenericServiceImpl<Shop, Integer> implements ShopService{

    private ShopDao shopDao;

    public ShopServiceImpl() {
        this.shopDao = new ShopDaoImpl();
    }

    public Shop getByName(String name) {
        shopDao.openCurrentSession();
        Shop shop = shopDao.findByName(name);
        shopDao.closeCurrentSession();
        return shop;
    }

    public Shop getByAddress(String address) {
        shopDao.openCurrentSession();
        Shop shop = shopDao.findByAddress(address);
        shopDao.closeCurrentSession();
        return shop;
    }

    public List<Shop> getAllByManager(Integer clientId) {
        shopDao.openCurrentSession();
        List<Shop> shops = shopDao.getAllByManager(clientId);
        shopDao.closeCurrentSession();
        return shops;
    }
}
