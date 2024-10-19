package com.example.assignment.model.order.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.order.Order;
import com.example.assignment.model.order.OrderItem;
import com.example.assignment.model.order.OrderRepository;
import com.example.assignment.model.product.Inventory;
import com.example.assignment.model.product.Product;
import com.example.assignment.model.product.service.ProductService;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService ;

    @Autowired
    public OrderService(OrderRepository orderRepository,ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService=productService;
    }

    private Order newOrder = new Order();
    private Inventory newInventory = new Inventory();
    

    // public void createOrder() {
    //     LocalDate time = LocalDate.now();  // Thời gian hiện tại
    //     double totalAmount = newOrder.getOrderItems().stream().mapToDouble(OrderItem::getPrice).sum();  // Tính tổng số tiền
    //     String status = "done";  // Đơn hàng đã hoàn thành
    //     User duong = new Manager();  // Cần thêm logic cho người dùng (ví dụ: lấy từ session)

    //     // Cập nhật thông tin cho newOrder
    //     newOrder.setOrderDate(time);
    //     newOrder.setTotalBill(totalAmount);
    //     newOrder.setStatus(status);
    //     newOrder.setEmployee(duong);  // Cập nhật thông tin nhân viên
    // }

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

    // public void addOrderItem(long productId, Order newOrder) {
    //     if(checkExistedOrderItemsByProduct(findProductById(productId), newOrder)){
    //         for (OrderItem orderItem : newOrder.getOrderItems()) {
    //             if (orderItem.getProduct().getId() == productId) {
    //                 orderItem.setQuantity(orderItem.getQuantity()+1);
    //             }
    
    //         }
    //     }
    //     else
    //     {

    //         newOrder.getOrderItems().add(new OrderItem(newOrder,findProductById(productId), 1, "lag"));
    //     } 

    // }
    public void addOrderItem(long productId, Order newOrder) {
        Product product = findProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        
        // Ensure no duplicate OrderItem is added
        Optional<OrderItem> existingOrderItem = newOrder.getOrderItems().stream()
            .filter(item -> item.getProduct().getId() == productId)
            .findFirst();
        
        if (existingOrderItem.isPresent()) {
            // Update the quantity if the item already exists
            existingOrderItem.get().setQuantity(existingOrderItem.get().getQuantity() + 1);
        } else {
            // Add new order item
            OrderItem newItem = new OrderItem(newOrder, product, 1, "note");
            newOrder.getOrderItems().add(newItem);
        }
    }
    

}
