package service;


import models.Category;

public interface CategoryService extends GenericService<Category,Integer>{
    public Category getByName(String name);
}
