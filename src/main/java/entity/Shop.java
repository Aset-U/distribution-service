package entity;

import java.util.List;

public class Shop extends Entity {

    private String name;
    private String address;
    private String phone;
    private Client manager;
    private List<Forwarder> forwarders;

    public Shop() {
    }


    public Shop(Integer id, String name, String address, String phone, Client manager, List<Forwarder> forwarders) {
        super(id);
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.manager = manager;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Client getManager() {
        return manager;
    }

    public void setManager(Client manager) {
        this.manager = manager;
    }

    public List<Forwarder> getForwarders() {
        return forwarders;
    }

    public void setForwarders(List<Forwarder> forwarders) {
        this.forwarders = forwarders;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", manager=" + manager +
                ", forwarders=" + forwarders +
                '}';
    }
}
