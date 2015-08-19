package entity;


import java.util.List;

public class Car extends Entity {

    private String number;
    private String model;
    private List<Forwarder> forwarders;


    public Car() {
    }

    public Car(Integer id, String number, String model, List<Forwarder> forwarders) {
        super(id);
        this.number = number;
        this.model = model;
        this.forwarders = forwarders;
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public List<Forwarder> getForwarders() {
        return forwarders;
    }

    public void setForwarders(List<Forwarder> forwarders) {
        this.forwarders = forwarders;
    }

    @Override
    public String toString() {
        return "Car{" +
                "number='" + number + '\'' +
                ", model='" + model + '\'' +
                ", forwarders=" + forwarders +
                '}';
    }
}
