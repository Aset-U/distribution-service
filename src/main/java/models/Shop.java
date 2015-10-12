package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shop")
public class Shop extends AbstractEntity implements Serializable {

    private String name;

    private String address;

    @Column(length = 12)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Client manager;

    @OneToMany(mappedBy = "shop")
    private Set<Order> orders = new HashSet<>();

    private List<Forwarder> forwarders;

    public Shop() {
    }


    public Shop(Integer id, String name, String address, String phone, Client manager, Set<Order> orders, List<Forwarder> forwarders) {
        super(id);
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.manager = manager;
        this.orders = orders;
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

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
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
