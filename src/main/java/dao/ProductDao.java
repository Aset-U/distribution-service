package dao;

import entity.Product;

import java.util.List;


public interface ProductDao extends GenericDao<Product, Integer> {

    public List<Product> findByCategory(Integer categoryId) throws PersistException;

}
