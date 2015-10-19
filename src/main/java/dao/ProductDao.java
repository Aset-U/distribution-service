package dao;

import models.Product;

import java.util.List;

public interface ProductDao extends GenericDao<Product, Integer>{
    public List<Product> getAllByCategory(Integer categoryId);

}
