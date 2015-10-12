package models;


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table
public class Category extends AbstractEntity {

    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Category> categories = new HashSet<>();

    public Category() {
    }

    public Category(Integer id, String name, Set<Category> categories) {
        super(id);
        this.name = name;
        this.categories = categories;
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + getId() +
                "name='" + name + '\'' +
                '}';
    }
}
