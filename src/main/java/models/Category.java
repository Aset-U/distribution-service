package models;



public class Category extends AbstractEntity {

    private String name;

    public Category() {
    }

    public Category(Integer id, String name) {
        super(id);
        this.name = name;
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

    @Override
    public String toString() {
        return "Category{" +
                "id=" + getId() +
                "name='" + name + '\'' +
                '}';
    }
}
