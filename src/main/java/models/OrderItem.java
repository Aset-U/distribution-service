package models;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItem extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private short quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem() {}

    public OrderItem(Product product) {
        this.product = product;
        quantity = 1;
    }

    public OrderItem(Integer id, Product product, short quantity, Order order) {
        super(id);
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public BigDecimal getTotal() {
        BigDecimal amount = new BigDecimal("0");
        amount = product.getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
        return amount;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrderItem)) {
            return false;
        }
        OrderItem other = (OrderItem) obj;
        if (this.getProduct().getId() != other.getProduct().getId()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "OredrItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", order=" + order +
                '}';
    }
}
