package dao;

import entity.Product;

import java.util.List;


public interface ProductDao {
    public Product getByPK(Integer productId, boolean withOrders) throws PersistException;
    public List<Product> getProductByCategory(Integer categoryId) throws PersistException;
    public List<Product> getProductsByOrder(Integer orderId) throws PersistException;
    public List<Product> getAllProduct() throws PersistException;
}