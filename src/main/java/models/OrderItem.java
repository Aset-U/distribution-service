package models;


public class OrderItem extends Entity{

    private Product product;
    private short quantity;
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

    public double getTotal() {
        double amount = 0;
        amount = (this.getQuantity() * product.getPrice());
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
