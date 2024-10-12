package com.example.assignment.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.assignment.model.order.Invoice;
import com.example.assignment.model.order.Order;
import com.example.assignment.model.order.OrderItem;
import com.example.assignment.model.product.Inventory;
import com.example.assignment.model.product.Product;
import com.example.assignment.model.product.service.ProductService;
import com.example.assignment.model.user.Manager;
import com.example.assignment.model.user.User;
import com.example.assignment.model.user.UserList;
import com.example.assignment.model.user.service.UserService;

@Controller
@RequestMapping
public class ProjectController {

    private Order newOrder = new Order();
    private Inventory newInventory = new Inventory();
    private UserList newUserList = new UserList();

    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProjectController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    // @PostConstruct
    // public void init() {
    //     setUserInit();
    // }
    // @PostConstruct
    // public void addUser(){
    // }
    public void createOrder() {
        LocalDate time = LocalDate.now();  // Thời gian hiện tại
        double totalAmount = newOrder.getOrderItems().stream().mapToDouble(OrderItem::getPrice).sum();  // Tính tổng số tiền
        String status = "done";  // Đơn hàng đã hoàn thành
        User duong = new Manager();  // Cần thêm logic cho người dùng (ví dụ: lấy từ session)

        // Cập nhật thông tin cho newOrder
        newOrder.setOrderDate(time);
        newOrder.setTotalBill(totalAmount);
        newOrder.setStatus(status);
        // newOrder.setEmployee(duong);  // Cập nhật thông tin nhân viên
    }

    @GetMapping("/")
    public String getLoginPage() {
        return "login"; // Points to login.html
    }

    @PostMapping("/login")
    public String inputInformation(@RequestParam String shopName, @RequestParam String username, @RequestParam String password, Model model) {
        for (User user : userService.getUser()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password) && user.getShopName().equals(shopName)) {
                return "redirect:/order";

            }
        }
        model.addAttribute("loginError", true); // Set error message in case of failure
        return "login"; // Redirect back to login page (index.html) with error

    }

    // Hiển thị sản phẩm
    @GetMapping("/order(1)")// annotation "/order" 
    public String getOrder(Model model) {
        // setInitialValue();
        model.addAttribute("newInventory", productService.getProducts());//trả về list của product->newInventory
        model.addAttribute("newOrderItems", newOrder.getOrderItems());
        return "order(1)";//trả về web order.html
    }

    // Tạo hóa đơn và in ra
    @GetMapping("/printOrder")
    public String printOrder(Model model) {
        createOrder();  // Cập nhật thông tin cho newOrder
        Invoice newInvoice = new Invoice(newOrder.getOrderDate(), newOrder, "Qr code", newOrder.getTotalBill());
        model.addAttribute("invoice", newInvoice);
        // newOrder.setOrderItems(null);
        return "invoice";  // HTML file cho hóa đơn
    }

    @PostMapping("/addOrderItem")
    public String addOrderItem(@RequestParam long productId, @RequestParam int quantity) {
        // System.out.println("Product ID: " + productId);
        // System.out.println("Quantity: " + quantity);
        Optional<Product> productOpt = newInventory.getProducts().stream()
                .filter(p -> p.getId() == (productId))
                .findFirst();

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            newOrder.getOrderItems().add(new OrderItem(product, quantity, product.getPrice() * quantity, "Lmao"));

            product.setQuantity(product.getQuantity() - quantity);
        }
        return "redirect:/order";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam long productId) {
        Optional<Product> productOpt = productService.getProducts().stream()
                .filter(p -> p.getId() == (productId))
                .findFirst();
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            newOrder.getOrderItems().add(new OrderItem(product, 1, product.getPrice() * 1, "lag"));

            // product.setQuantity(product.getQuantity() - quantity);
        }
        return "redirect:/order(1)";
    }

    @CrossOrigin(origins = "http://localhost:5500") // Adjust if needed
    @GetMapping("/api/product")
    public Optional<Product> getProductById(@RequestParam long id) {
        return productService.getProducts().stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    // @PostMapping("/addOrderItem")
    // @ResponseBody  // Để trả về JSON
    // public OrderItem addOrderItem(@RequestParam long productId, @RequestParam int quantity) {
    //     Optional<Product> productOpt = productService.getProducts().stream()
    //             .filter(p -> p.getId() == (productId))
    //             .findFirst();

    //     if (productOpt.isPresent()) {
    //         Product product = productOpt.get();
    //         OrderItem newOrderItem = new OrderItem(product, quantity, product.getPrice() * quantity, "Lmao");

    //         newOrder.getOrderItems().add(newOrderItem);
    //         product.setQuantity(product.getQuantity() - quantity);

    //         return newOrderItem;  // Trả về sản phẩm đã thêm
    //     }
    //     return null;
    // }

}
