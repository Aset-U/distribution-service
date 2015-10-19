package dao.hibernate;

import dao.CategoryDao;
import models.Category;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class CategoryDaoImpl extends GenericDaoImpl<Category, Integer> implements CategoryDao {

    @Override
    public Category findByName(String name) {
        Category category = null;

        Criteria criteria = getCurrentSession().createCriteria(Category.class);
        criteria.add(Restrictions.eq("name", name));
        category = (Category) criteria.uniqueResult();

        return category;
    }
}
