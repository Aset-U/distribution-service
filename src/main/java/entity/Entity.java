package entity;


import dao.Identified;

public class Entity implements Identified<Integer> {
    private Integer id = null;

    public Entity() {
    }

    public Entity(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
