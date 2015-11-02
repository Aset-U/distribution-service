package servlet;

import models.*;
import service.*;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;


@WebListener()
public class ServletListener implements ServletRequestListener, HttpSessionListener  {

    private int page = 1;
    private int recordsPerPage = 6;

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        ProductService productService = new ProductServiceImpl();
        CategoryService categoryService = new CategoryServiceImpl();

        List<Product> products = productService.getAll();
        List<Category> categories = categoryService.getAll();

        session.setAttribute("allProducts", products);
        session.setAttribute("categories", categories);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        initializePersistCart(session);

    }

    private void initializePersistCart(HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        Order cart = (Order) session.getAttribute("cart");
        List<OrderItem> sessionOrderItems = cart.getItems();
        List<OrderItem> persistOrderItems = null;

        OrderService orderService = new OrderServiceImpl();
        OrderItemService orderItemService = new OrderItemServiceImpl();
        Order persistCart = new Order();

        if (cart.getId() == null) {
            persistCart.setClient(client);
            orderService.add(persistCart);
        }
        else {
            persistOrderItems = orderItemService.getAllByOrder(cart.getId());
        }
        if (persistOrderItems == null) {
            for (OrderItem item : sessionOrderItems) {
                OrderItem cartItem = new OrderItem();
                cartItem.setProduct(item.getProduct());
                cartItem.setQuantity(item.getQuantity());
                orderItemService.add(cartItem);

                if (persistCart != null) {
                    cartItem.setOrder(persistCart);
                }
                else {
                    cartItem.setOrder(cart);
                }
                orderItemService.update(cartItem);
            }
        }
        if (sessionOrderItems.size() >= 0 && persistOrderItems.size() > 0) {
            for (OrderItem item : sessionOrderItems) {
                if (persistOrderItems.contains(item)) {
                    orderItemService.update(item);
                }
                else {
                    OrderItem cartItem = new OrderItem();
                    cartItem.setOrder(cart);
                    cartItem.setProduct(item.getProduct());
                    cartItem.setQuantity(item.getQuantity());
                    orderItemService.add(cartItem);
                }
            }
            for (OrderItem pItem : persistOrderItems) {
                if (!sessionOrderItems.contains(pItem)) {
                    orderItemService.remove(pItem);
                }
            }
        }
    }
}
