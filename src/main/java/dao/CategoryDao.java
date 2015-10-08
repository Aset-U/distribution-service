package dao;


import models.Category;

import java.util.List;

public interface CategoryDao {
    public Category getName(String name) throws PersistException;
    public List<Category> getAllCategories() throws PersistException;
}
