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
import com.example.assignment.model.order.Service.InvoiceService;
import com.example.assignment.model.order.Service.OrderService;
import com.example.assignment.model.product.Product;
import com.example.assignment.model.product.service.InventoryService;
import com.example.assignment.model.product.service.ProductService;
import com.example.assignment.model.user.User;
import com.example.assignment.model.user.service.UserService;

@Controller
@RequestMapping
public class ProjectController {

    private Order newOrder = new Order();
    private User currentUser;

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final InventoryService inventoryService;

    @Autowired
    public ProjectController(ProductService productService,
            InvoiceService invoiceService,
            UserService userService,
            OrderService orderService,
            InventoryService inventoryService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
        this.inventoryService = inventoryService;
    }

    @GetMapping("/")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/addProduct")
    public String getAddproductPage() {
        return "addProduct";
    }

    @PostMapping("/login")
    public String inputInformation(@RequestParam String shopName, @RequestParam String username, @RequestParam String password, Model model) {
        for (User user : userService.getUser()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password) && user.getShopName().equals(shopName)) {
                currentUser = user;
                if(currentUser.getRole().equals("staff")){
                    return "redirect:/order(1)";
                }
                else return "redirect:/manage";
            }
        }
        model.addAttribute("loginError", true);
        return "login";
    }


    @GetMapping("/order(1)")// annotation "/order" 
    public String getOrder(Model model) {
        model.addAttribute("newInventory", productService.getProducts());//trả về list của product->newInventory
        model.addAttribute("newOrderItems", newOrder.getOrderItems());
        return "order(1)";//trả về web order(1).html
    }

    // Tạo hóa đơn và in ra
    @GetMapping("/printOrder")
    public String printOrder(Model model) {
        orderService.createOrder(newOrder, currentUser);
        Invoice newInvoice = new Invoice(newOrder.getOrderDate(), newOrder, "Qr code", newOrder.getTotalBill());
        invoiceService.addInvoice(newInvoice);
        model.addAttribute("invoice", invoiceService.getInvoice().get(invoiceService.getInvoice().size() - 1));
        model.addAttribute("shopName", currentUser != null ? currentUser.getShopName() : "Default Shop");
        model.addAttribute("staffName", currentUser != null ? currentUser.getUsername() : "Default Staff");
        newOrder = new Order();
        return "invoice";
    }

    @PostMapping("/addProductToOrder")
    public String addProduct(@RequestParam long productId) {
        orderService.addOrderItem(productId, newOrder);
        return "redirect:/order(1)";
    }

    @GetMapping("/manage")
    public String getRevenueToday(LocalDate date, Model model) {
        //get revenue today
        date = LocalDate.now();
        model.addAttribute("revenue", invoiceService.getTotalInvoiceByDay(date, currentUser));

        model.addAttribute("topSoldsList", invoiceService.getTopSoldsByQuantity(date));
        return "manage";
    }

    @GetMapping("/productManagement")
    public String getProduct(Model model) {
        model.addAttribute("newInventory", productService.getProducts());//trả về list của product->newInventory
        return "productManagement";
    }

    @PostMapping("/addProduct")//
    public String saveProduct(@RequestParam String name,
            @RequestParam String category,
            @RequestParam int quantity,
            @RequestParam double cost,
            @RequestParam float price,
            @RequestParam String description,
            Model model
    ) {
        boolean hasError = false;

        if (Validation.isCostHigherThanPrice(cost, price)) {
            model.addAttribute("priceError", "Cost should not be higher than the price.");
            hasError = true;
        }

        if (Validation.isStringNull(name)) {
            model.addAttribute("nameError", "Product name is required.");
            hasError = true;
        }

        if (hasError) {
            model.addAttribute("hasError", true);
            return "addProduct";
        } else {
            productService.addProduct(new Product(
                    name,
                    description,
                    price,
                    quantity,
                    category,
                    true,
                    cost,
                    inventoryService.getInventory().get(0)));
        }

        return "redirect:/productManagement";
    }

    // @GetMapping("/manageWeek")
    // public String getRevenueThisWeek(LocalDate date, Model model) {
    //     // date=LocalDate.parse("2024-10-19");
    //     // date = LocalDate.of(2024, 10, 19); 
    //     date = LocalDate.now();
    //     model.addAttribute("revenueList", invoiceService.getDateAndRevenueByWeek(date));
    //     return "manage";
    // }
// @GetMapping("/manageWeek")
// public String getRevenueThisWeek(LocalDate date, Model model) {
//     date = LocalDate.now(); // Ngày hiện tại
//     List<DateAndRevenue> revenueList = invoiceService.getDateAndRevenueByWeek(date);
//     model.addAttribute("revenueList", revenueList); // Truyền sang view
//     return "manage"; // Trả về tên file HTML (manage.html)
// }
// @GetMapping("/manageWeek")
// public String getRevenueThisWeek(Model model) {
//     LocalDate date = LocalDate.now();
//     List<DateAndRevenue> revenueList = invoiceService.getDateAndRevenueByWeek(date);
//     // Kiểm tra dữ liệu trước khi chuyển đổi thành JSON
//     System.out.println("Revenue List: " + revenueList);
//     // Chuyển đổi danh sách thành JSON
//     ObjectMapper objectMapper = new ObjectMapper();
//     String revenueJson;
//     try {
//         revenueJson = objectMapper.writeValueAsString(revenueList);
//     } catch (Exception e) {
//         e.printStackTrace(); // In lỗi ra console
//         revenueJson = "[]"; // Đặt giá trị mặc định nếu có lỗi
//     }
//     model.addAttribute("revenueJson", revenueJson);
//     return "manage";
// }
    // @PostMapping("/changeQuantity")
    // public String changeQuantity(@RequestParam long orderItemId, @RequestParam int quantity) {
    //     orderService.changeQuantityByInput(newOrder, orderItemId, quantity);
    //     return "redirect:/order(1)";
    // }
    @PostMapping("/changeQuantity")
    public String changeQuantity(@RequestParam(defaultValue = "0") long orderItemId, @RequestParam String quantity, Model model) {
        int integerQuantity = 0;
        try {
            integerQuantity = Integer.parseInt(quantity);
        } catch (Exception e) {
            model.addAttribute("inputError", "Quantity must be an integer!");
        }

        if (orderItemId != 0) {
            orderService.changeQuantityByInput(newOrder, orderItemId, integerQuantity);
        } else {
            model.addAttribute("inputError", true);
        }
        return "redirect:/order(1)";
    }

    @PostMapping("/deleteItem")
    public String deleteItem(@RequestParam long id) {
        orderService.deleteOrderItem(newOrder, id);
        return "redirect:/order(1)";
    }
}
