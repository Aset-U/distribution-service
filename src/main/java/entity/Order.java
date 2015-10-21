package entity;


import java.util.ArrayList;
import java.util.List;

public class Order extends Entity {

    private Status status;
    private Client client;
    private Shop shop;
    private List<OrderItem> items;
    private int numberOfItems;
    private double total;

    public Order() {
        items = new ArrayList<OrderItem>();
        numberOfItems = 0;
        total = 0;
    }


    public Order(Integer id, Status status, Client client, Shop shop) {
        super(id);
        this.status = status;
        this.client = client;
        this.shop = shop;
        items = new ArrayList<OrderItem>();
        numberOfItems = 0;
        total = 0;
    }

    public synchronized void addItem(Product product) {

        boolean newItem = true;

        for (OrderItem scItem : items) {

            if (scItem.getProduct().getId() == product.getId()) {

                newItem = false;
                scItem.incrementQuantity();
            }
        }

        if (newItem) {
            OrderItem scItem = new OrderItem(product);
            items.add(scItem);
        }
    }

    public synchronized void update(Product product, String quantity) {

        short qty = -1;

        // cast quantity as short
        qty = Short.parseShort(quantity);

        if (qty >= 0) {

            OrderItem item = null;

            for (OrderItem scItem : items) {

                if (scItem.getProduct().getId() == product.getId()) {

                    if (qty != 0) {
                        // set item quantity to new value
                        scItem.setQuantity(qty);
                    } else {
                        // if quantity equals 0, save item and break
                        item = scItem;
                        break;
                    }
                }
            }

            if (item != null) {
                // remove from cart
                items.remove(item);
            }
        }
    }



    public synchronized int getNumberOfItems() {

        numberOfItems = 0;

        for (OrderItem scItem : items) {

            numberOfItems += scItem.getQuantity();
        }

        return numberOfItems;
    }

    public synchronized double getSubtotal() {

        double amount = 0;

        for (OrderItem scItem : items) {

            Product product = (Product) scItem.getProduct();
            amount += (scItem.getQuantity() * product.getPrice());
        }

        return amount;
    }

    public synchronized double getTotal() {

        return total;
    }

    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
        total = 0;
    }
    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public synchronized List<OrderItem> getItems() {
        return items;
    }
    public synchronized void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "client=" + client +
                ", status=" + status +
                '}';
    }
}
