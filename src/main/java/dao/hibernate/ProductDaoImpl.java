package dao.hibernate;


import dao.ProductDao;
import models.Product;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ProductDaoImpl extends GenericDaoImpl<Product, Integer> implements ProductDao{

    @Override
    public List<Product> getAllByCategory(Integer categoryId) {
        List<Product> products = null;

        Criteria criteria = getCurrentSession().createCriteria(Product.class, "product");
        criteria.createCriteria("product.category", "category")
                .add(Restrictions.eq("category.id", categoryId));

        products = criteria.list();

        return products;
    }

}
