package dao.hibernate;


import dao.ShopDao;
import models.Shop;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ShopDaoImpl extends GenericDaoImpl<Shop, Integer>  implements ShopDao{

    @Override
    public Shop findByName(String name) {
        Shop shop = null;

        Criteria criteria = getCurrentSession().createCriteria(Shop.class);
        criteria.add(Restrictions.eq("name", name));

        shop = (Shop) criteria.uniqueResult();

        return shop;
    }

    @Override
    public Shop findByAddress(String address) {
        Shop shop = null;

        Criteria criteria = getCurrentSession().createCriteria(Shop.class);
        criteria.add(Restrictions.eq("address", address));

        shop = (Shop) criteria.uniqueResult();

        return shop;
    }

    @Override
    public List<Shop> getAllByManager(Integer clientId) {
        List<Shop> shops = null;

        Criteria criteria = getCurrentSession().createCriteria(Shop.class, "shop");
        criteria.createCriteria("shop.manager", "manager");
        criteria.add(Restrictions.eq("manager.id", clientId));

        shops = criteria.list();

        return shops;
    }
}
