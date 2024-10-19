package com.example.assignment.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.assignment.model.order.Invoice;
import com.example.assignment.model.order.Order;
import com.example.assignment.model.order.OrderItem;
import com.example.assignment.model.order.Service.InvoiceService;
import com.example.assignment.model.order.Service.OrderService;
import com.example.assignment.model.product.Inventory;
import com.example.assignment.model.product.service.ProductService;
import com.example.assignment.model.user.Staff;
import com.example.assignment.model.user.User;
import com.example.assignment.model.user.service.StaffService;
import com.example.assignment.model.user.service.UserService;

@Controller
@RequestMapping
public class ProjectController {

    private Order newOrder = new Order();
    private Inventory newInventory = new Inventory();
    private User currentUser;
    // private UserList newUserList = new UserList();

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final StaffService staffService;

    @Autowired
    public ProjectController(ProductService productService,
            InvoiceService invoiceService,
            UserService userService,
            OrderService orderService,
            StaffService staffService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
        this.staffService = staffService;
    }

    // @PostConstruct
    // public void init() {
    //     setInitialValue();
    // }
    // @PostConstruct
    // public void init(){
    //     setDefault();
    // }

    // public void setDefault(){
    //      Staff staff1=new Staff(0,0,0,"byla@coffee","phongle@gmail.com","Nguyen Huu Phong","phong123",989612290,"staff",true,"phong");
    //     Staff staff2=new Staff(0,0,0,"hadilao@hotpot","diemquynh@gmail.com","Le Thi Diem Quynh","quynh123",989612290,"staff",true,"quynh");
    //     userService.addUser(staff2);
    //     userService.addUser(staff1);
    // }

    public void createOrder() {
        LocalDate time = LocalDate.now();  // Thời gian hiện tại
        double totalAmount = newOrder.getOrderItems().stream().mapToDouble(OrderItem::getPrice).sum();  // Tính tổng số tiền
        String status = "done";  // Đơn hàng đã hoàn thành
        // User duong1 = staffService.getStaff().get(5);
        // User duong1 = new Manager();
        // staffService.getStaff().get(9);

        Staff duong1 = new Staff();
        if (currentUser != null) {
            currentUser = userService.updateUser(currentUser);  // Gắn lại currentUser trước khi sử dụng nó
        }

        // Cập nhật thông tin cho newOrder
        newOrder.setOrderDate(time);
        newOrder.setTotalBill(totalAmount);
        newOrder.setStatus(status);
        newOrder.setEmployee(currentUser);

    }

    // @Transactional
    // public void updateUser(User user) {
    //     userService.merge(user);
    // }

    @GetMapping("/")
    public String getLoginPage() {
        return "login"; // Points to login.html
    }

    @PostMapping("/login")
    public String inputInformation(@RequestParam String shopName, @RequestParam String username, @RequestParam String password, Model model) {
        for (User user : userService.getUser()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password) && user.getShopName().equals(shopName)) {
                currentUser = user;
                return "redirect:/order(1)";

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
        return "order(1)";//trả về web order(1).html
    }

    // Tạo hóa đơn và in ra
    @GetMapping("/printOrder")
    public String printOrder(Model model) {
        createOrder();  // Cập nhật thông tin cho newOrder
        Invoice newInvoice = new Invoice(newOrder.getOrderDate(), newOrder, "Qr code", newOrder.getTotalBill());
        // ArrayList<OrderItem> orderItems = new ArrayList<>(newOrder.getOrderItems());
        // newInvoice.getOrder().setOrderItems(orderItems);
        invoiceService.addInvoice(newInvoice);
        model.addAttribute("invoice", invoiceService.getInvoice().get(invoiceService.getInvoice().size()-1));
        // model.addAttribute("invoice", newInvoice);
        newOrder = new Order();
        return "invoice";  // HTML file cho hóa đơn
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam long productId) {
        orderService.addOrderItem(productId, newOrder);
        return "redirect:/order(1)";
    }
}
