package dao;

import entity.Product;

import java.util.List;


public interface ProductDao {
    public Product getByPK(Integer productId, boolean withOrders) throws PersistException;
    public List<Product> getProductsByCategory(Integer categoryId) throws PersistException;
    public List<Product> getProductsByOrder(Integer orderId) throws PersistException;
    public List<Product> getAll() throws PersistException;
}
