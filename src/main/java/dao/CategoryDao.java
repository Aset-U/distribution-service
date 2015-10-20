package dao;


import entity.Category;

import java.util.List;

public interface CategoryDao extends GenericDao<Category, Integer>{
    public Category getByName(String name) throws PersistException;
}
