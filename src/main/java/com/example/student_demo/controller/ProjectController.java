package com.example.student_demo.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.student_demo.model.order.Invoice;
import com.example.student_demo.model.order.Order;
import com.example.student_demo.model.order.OrderItem;
import com.example.student_demo.model.product.Inventory;
import com.example.student_demo.model.product.Product;
import com.example.student_demo.model.user.User;

@Controller
public class ProjectController {

    private Order newOrder = new Order();
    private Inventory newInventory = new Inventory();

    // Khởi tạo danh sách sản phẩm
    public void setInitialValue() {
        Product product1 = new Product("de1", "Nước cam", "Best seller", 30000, 20, "Nước ép", true);
        Product product2 = new Product("de2", "Sữa chua đá", "Best seller", 20000, 20, "Sữa chua", true);
        Product product3 = new Product("de3", "Cà phê sữa Sài gòn", "Best seller", 17000, 20, "Cà phê", true);
        Product product4 = new Product("de4", "Bạc xỉu", "Best seller", 20000, 20, "Cà phê", true);
        newInventory.getProducts().add(product1);
        newInventory.getProducts().add(product2);
        newInventory.getProducts().add(product3);
        newInventory.getProducts().add(product4);
    }

    // Hiển thị sản phẩm
    @GetMapping("/order")// annotation "/order" 
    public String getOrder(Model model) {
        setInitialValue();
        model.addAttribute("newInventory", newInventory.getProducts());//trả về list của product->newInventory
        model.addAttribute("newOrderItems", newOrder.getOrderItems());
        return "order";//trả về web order.html
    }
    
    // Tạo hóa đơn và in ra
    @GetMapping("/printOrder")
    public String printOrder(Model model) {
        createOrder();  // Cập nhật thông tin cho newOrder
        Invoice newInvoice = new Invoice(newOrder.getOrderDate(), newOrder, "Qr code", newOrder.getTotalBill());
        model.addAttribute("invoice", newInvoice);
        return "invoice";  // HTML file cho hóa đơn
    }

// Cập nhật thông tin đơn hàng
    public void createOrder() {
        LocalDate time = LocalDate.now();  // Thời gian hiện tại
        double totalAmount = newOrder.getOrderItems().stream().mapToDouble(OrderItem::getPrice).sum();  // Tính tổng số tiền
        String status = "done";  // Đơn hàng đã hoàn thành
        User duong = new User();  // Cần thêm logic cho người dùng (ví dụ: lấy từ session)

        // Cập nhật thông tin cho newOrder
        newOrder.setOrderDate(time);
        newOrder.setTotalBill(totalAmount);
        newOrder.setStatus(status);
        newOrder.setEmployee(duong);  // Cập nhật thông tin nhân viên
    }

    // Thêm sản phẩm vào đơn hàng
    @PostMapping("/addOrderItem")
    public String addOrderItem(@RequestParam String productId, @RequestParam int quantity) {

        Optional<Product> productOpt = newInventory.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            newOrder.getOrderItems().add(new OrderItem(product, quantity, product.getPrice() * quantity, "Lmao"));
        }
        return "redirect:/order";
    }
}
