package dao.hibernate;


import dao.ProductDao;
import models.Product;

import java.util.List;

public class ProductDaoImpl extends GenericDaoImpl<Product, Integer> implements ProductDao{

    @Override
    public List<Product> getAllByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public List<Product> getAllByOrder(Integer orderId) {
        return null;
    }
}
