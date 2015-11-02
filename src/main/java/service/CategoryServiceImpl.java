package service;


import dao.CategoryDao;
import dao.hibernate.CategoryDaoImpl;
import models.Category;

public class CategoryServiceImpl extends GenericServiceImpl<Category, Integer> implements CategoryService{

    private CategoryDao categoryDao;

    public CategoryServiceImpl() {
        this.categoryDao = new CategoryDaoImpl();
    }

    public Category getByName(String name) {
        categoryDao.openCurrentSession();
        Category category = categoryDao.findByName(name);
        categoryDao.closeCurrentSession();
        return category;
    }
}
