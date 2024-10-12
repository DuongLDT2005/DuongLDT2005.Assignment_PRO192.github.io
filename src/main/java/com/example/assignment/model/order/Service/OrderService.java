package com.example.assignment.model.order.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.order.Order;
import com.example.assignment.model.order.OrderItem;
import com.example.assignment.model.order.OrderRepository;
import com.example.assignment.model.product.Inventory;
import com.example.assignment.model.product.service.ProductService;
import com.example.assignment.model.user.Manager;
import com.example.assignment.model.user.User;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private Order newOrder = new Order();
    private Inventory newInventory = new Inventory();
    private ProductService productService;

    public void createOrder() {
        LocalDate time = LocalDate.now();  // Thời gian hiện tại
        double totalAmount = newOrder.getOrderItems().stream().mapToDouble(OrderItem::getPrice).sum();  // Tính tổng số tiền
        String status = "done";  // Đơn hàng đã hoàn thành
        User duong = new Manager();  // Cần thêm logic cho người dùng (ví dụ: lấy từ session)

        // Cập nhật thông tin cho newOrder
        newOrder.setOrderDate(time);
        newOrder.setTotalBill(totalAmount);
        newOrder.setStatus(status);
        newOrder.setEmployee(duong);  // Cập nhật thông tin nhân viên
    }

}
