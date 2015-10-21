package dao;

import entity.Product;

import java.util.List;


public interface ProductDao extends GenericDao<Product, Integer> {

    public List<Product> findByCategory(Integer categoryId) throws PersistException;

    public List<Product> getForPage(int offset, int noOfRecords) throws PersistException;
}
