package service;

import models.Product;

import java.util.List;

public interface ProductService extends GenericService<Product,Integer>{
    public List<Product> getAllByCategory(Integer categoryId);
}
