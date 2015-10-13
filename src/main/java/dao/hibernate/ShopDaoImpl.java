package dao.hibernate;


import dao.ShopDao;
import models.Shop;

import java.util.List;

public class ShopDaoImpl extends GenericDaoImpl<Shop, Integer>  implements ShopDao{

    @Override
    public Shop findByName(String name) {
        return null;
    }

    @Override
    public Shop findByAddress(String address) {
        return null;
    }

    @Override
    public List<Shop> getAllByManager(Integer clientId) {
        return null;
    }
}
