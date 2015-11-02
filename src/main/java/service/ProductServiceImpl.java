package service;


import dao.ProductDao;
import dao.hibernate.ProductDaoImpl;
import models.Product;

import java.util.List;

public class ProductServiceImpl extends GenericServiceImpl<Product, Integer> implements ProductService{

    private ProductDao productDao;

    public ProductServiceImpl(){
        this.productDao = new ProductDaoImpl();
    }

    public List<Product> getAllByCategory(Integer categoryId) {
        productDao.openCurrentSession();
        List<Product> products = productDao.getAllByCategory(categoryId);
        productDao.closeCurrentSession();
        return products;
    }
}
