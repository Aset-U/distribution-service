package dao.hibernate;

import dao.CategoryDao;
import models.Category;

public class CategoryDaoImpl extends GenericDaoImpl<Category, Integer> implements CategoryDao {

    @Override
    public Category findByName(String name) {
        return null;
    }
}
