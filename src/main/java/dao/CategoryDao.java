package dao;


import models.Category;

import java.util.List;

public interface CategoryDao extends GenericDao<Category, Integer> {
    public Category findByName(String name);
}
