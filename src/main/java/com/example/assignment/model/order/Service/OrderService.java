package com.example.assignment.model.order.Service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.order.Order;
import com.example.assignment.model.order.OrderItem;
import com.example.assignment.model.order.OrderItemRepository;
import com.example.assignment.model.order.OrderRepository;
import com.example.assignment.model.product.Product;
import com.example.assignment.model.product.service.ProductService;
import com.example.assignment.model.user.User;
import com.example.assignment.model.user.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class OrderService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
            ProductService productService,
            UserService userService,
            OrderItemRepository orderItemRepository,
            EntityManager entityManager
    ) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userService = userService;
        this.orderItemRepository = orderItemRepository;
        this.entityManager = entityManager;
    }

    public void createOrder(Order newOrder, User currentUser) {
        LocalDate time = LocalDate.now();  // Thời gian hiện tại, bao gồm cả giây phút
        double totalAmount = newOrder.getOrderItems().stream().mapToDouble(OrderItem::getPrice).sum();  // Tính tổng số tiền
        String status = "done";
        if (currentUser != null) {
            currentUser = userService.updateUser(currentUser);  // Gắn lại currentUser trước khi sử dụng nó
        }
        // Cập nhật thông tin cho newOrder
        newOrder.setOrderDate(time);
        newOrder.setTotalBill(totalAmount);
        newOrder.setStatus(status);
        newOrder.setEmployee(currentUser);
        addOrder(newOrder);
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public Product findProductById(long productId) {
        Optional<Product> productOpt = productService.getProducts().stream()
                .filter(p -> p.getId() == (productId))
                .findFirst();
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            return product;
        } else {
            return null;
        }

    }

    public boolean checkExistedOrderItemsByProduct(Product product, Order newOrder) {
        for (OrderItem orderItem : newOrder.getOrderItems()) {
            if (orderItem.getProduct().getId() == product.getId()) {
                return true;
            }

        }
        return false;
    }

    public void addOrderItem(long productId, Order newOrder) {
        System.out.println("===================" + productId + "===========================");
        Product product = findProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }


        if (newOrder.getId() == 0) {
            newOrder = orderRepository.save(newOrder); 
        }

        Optional<OrderItem> existingOrderItem = newOrder.getOrderItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst();

        if (existingOrderItem.isPresent()) {
  
            existingOrderItem.get().setQuantity(existingOrderItem.get().getQuantity() + 1);
        } else {

            OrderItem newItem = new OrderItem(newOrder, product, 1, "note");
            newItem = orderItemRepository.save(newItem);  


            newOrder.getOrderItems().add(newItem);
        }

        // Save the updated order to ensure the order items are linked
        orderRepository.save(newOrder);
    }

    public void changeQuantityByInput(Order newOrder, long orderItemId, int quantity) {
        Optional<OrderItem> existingOrderItem = newOrder.getOrderItems().stream()
                .filter(item -> item.getId() == orderItemId)
                .findFirst();
        if (existingOrderItem.isPresent()) {
            // Update quantity neu orderitem exist
            existingOrderItem.get().setQuantity(quantity);
        }
    }


    public void deleteOrderItem(Order newOrder, long orderItemId) {
        Optional<OrderItem> existingOrderItem = newOrder.getOrderItems().stream()
                .filter(item -> item.getId() == orderItemId)
                .findFirst();
        if (existingOrderItem.isPresent()) {
            newOrder.getOrderItems().remove(existingOrderItem.get());
            orderRepository.save(newOrder);

            // Re-fetch `newOrder` to update it in the current session
            Order updatedOrder = orderRepository.findById(newOrder.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));
            updatedOrder.getOrderItems().size();


            newOrder.setOrderItems(updatedOrder.getOrderItems());
        }
    }


}
