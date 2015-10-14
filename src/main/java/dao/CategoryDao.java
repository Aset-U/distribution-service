package dao;


import entity.Category;

import java.util.List;

public interface CategoryDao {
    public Category getByName(String name) throws PersistException;
    public List<Category> getAllCategories() throws PersistException;
}
