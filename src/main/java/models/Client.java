package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Client extends User implements Serializable {

    @Column(name = "[order]")
    @OneToMany(mappedBy = "client")
    Set<Order> oredrs = new HashSet<>();

    @Column(name = "shop")
    @OneToMany(mappedBy = "manager")
    Set<Shop> shops;

    public Client(){}

    public Client(Integer id, String username, String password, String firstName, String lastName, String phoneNumber, String email, Set<Order> oredrs, Set<Shop> shops) {
        super(id, username, password, firstName, lastName, phoneNumber, email);
        this.oredrs = oredrs;
        this.shops = shops;
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    public Set<Order> getOredrs() {
        return oredrs;
    }

    public void setOredrs(Set<Order> oredrs) {
        this.oredrs = oredrs;
    }

    public Set<Shop> getShops() {
        return shops;
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
    }

    @Override
    public String toString() {
        return "Client{" +
                "oredrs=" + oredrs +
                ", shops=" + shops +
                '}';
    }
}
