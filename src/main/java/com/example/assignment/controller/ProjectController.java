package com.example.assignment.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.example.assignment.model.product.Inventory;
import com.example.assignment.model.product.Product;
import com.example.assignment.model.product.service.InventoryService;
import com.example.assignment.model.product.service.ProductService;
import com.example.assignment.model.user.Manager;
import com.example.assignment.model.user.Staff;
import com.example.assignment.model.user.User;
import com.example.assignment.model.user.service.ManagerServices;
import com.example.assignment.model.user.service.StaffService;
import com.example.assignment.model.user.service.UserService;

@Controller
@RequestMapping
public class ProjectController {

    private Order newOrder = new Order();
    private Staff currentStaff;
    private Manager currentManager;
    private List<Product> currentProducts;
    private Staff tempStaff = new Staff();

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final InventoryService inventoryService;
    private final ManagerServices managerServices;
    private final StaffService staffServices;

    @Autowired
    public ProjectController(
            ProductService productService,
            InvoiceService invoiceService,
            UserService userService,
            OrderService orderService,
            InventoryService inventoryService,
            ManagerServices managerServices,
            StaffService staffServices
    ) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
        this.inventoryService = inventoryService;
        this.managerServices = managerServices;
        this.staffServices = staffServices;
    }

    @GetMapping("/")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @GetMapping("/addProduct")
    public String getAddproductPage() {
        return "addProduct";
    }

    @GetMapping("/searchStaff")
    public String getSearchStaffPage() {
        return "searchStaff";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "errorPage";
    }

    @PostMapping("/login")
    public String inputInformation(@RequestParam String shopName, @RequestParam String username, @RequestParam String password, Model model) {
        for (User user : userService.getUser()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password) && user.getShopName().equals(shopName)) {
                if (user.getRole().equals("staff")) {
                    currentStaff = (Staff) user;
                    return "redirect:/order(1)";
                } else {
                    currentManager = (Manager) user;
                    return "redirect:/manage";
                }
            }
        }
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/order(1)")
    public String getOrder(Model model) {
        if (currentStaff == null) {
            return "redirect:/error";
        }
        if (currentProducts == null) {
            currentProducts = productService.getProductByStaff(currentStaff);
            ArrayList<String> categoryList = productService.getCategoryList(productService.getProductByStaff(currentStaff));

            model.addAttribute("newInventory", currentProducts);
            model.addAttribute("newOrderItems", newOrder.getOrderItems());

            if (!(productService.getProductByStaff(currentStaff) == null)) {
                model.addAttribute("categoryList", categoryList);

            }
        } else {
            ArrayList<String> categoryList = productService.getCategoryList(productService.getProductByStaff(currentStaff));

            model.addAttribute("newInventory", currentProducts);
            model.addAttribute("newOrderItems", newOrder.getOrderItems());

            if (!(productService.getProductByStaff(currentStaff) == null)) {
                model.addAttribute("categoryList", categoryList);
            }
        }

        return "order(1)";
    }

    @PostMapping("/filterByCategory")
    public String filterBycategory(@RequestParam String category, Model model) {
        if (currentProducts == null) {
            return "order(1)";
        }
        currentProducts = productService.getProductByCatagory(productService.getProductByStaff(currentStaff), category);
        return "redirect:/order(1)";
    }

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

    @GetMapping("/printOrder")
    public String printOrder(Model model) {

        if (newOrder.getOrderItems().isEmpty()) {
            model.addAttribute("nullOrder", "Please select an item!");
            model.addAttribute("staff", currentStaff);
            return "redirect:/order(1)";
        }

        orderService.createOrder(newOrder, currentStaff);
        Invoice newInvoice = new Invoice(newOrder.getOrderDate(), newOrder, "Qr code", newOrder.getTotalBill());

        invoiceService.addInvoice(newInvoice);
        model.addAttribute("invoice", invoiceService.getInvoice().get(invoiceService.getInvoice().size() - 1));
        model.addAttribute("shopName", currentStaff != null ? currentStaff.getShopName() : "Default Shop");
        model.addAttribute("staffName", currentStaff != null ? currentStaff.getUsername() : "Default Staff");

        newOrder = new Order();
        return "invoice";
    }

    @PostMapping("/addProductToOrder")
    public String addProduct(@RequestParam long productId) {
        orderService.addOrderItem(productId, newOrder);
        return "redirect:/order(1)";
    }

    @PostMapping("/deleteItem")
    public String deleteItem(@RequestParam long id) {
        orderService.deleteOrderItem(newOrder, id);
        return "redirect:/order(1)";
    }

    @GetMapping("/manage")
    public String getRevenueToday(LocalDate date, Model model) {
        date = LocalDate.now();
        model.addAttribute("revenue", invoiceService.getTotalInvoiceByDay(date, currentManager));
        model.addAttribute("topSoldsList", invoiceService.getTopSoldsByQuantity( currentManager));
        return "manage";
    }

    @GetMapping("/productManagement")
    public String getProduct(Model model) {
        model.addAttribute("newInventory", productService.getProductByUser(currentManager));
        return "productManagement";
    }

    @PostMapping("/addProduct")
    public String saveProduct(
            @RequestParam(required = false) Long productId,
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam int quantity,
            @RequestParam double cost,
            @RequestParam float price,
            @RequestParam String description,
            Model model) {

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
            Product product;
            if (productId != null) {
                product = productService.getProductById(productId);
                product.setName(name);
                product.setCategory(category);
                product.setQuantity(quantity);
                product.setCost(cost);
                product.setPrice(price);
                product.setDescription(description);
            } else {
                product = new Product(
                        name,
                        description,
                        price,
                        quantity,
                        category,
                        true,
                        cost,
                        currentManager.getInventoryManager()
                );
            }
            productService.addProduct(product);
            inventoryService.setRestockDate(currentManager.getInventoryManager());
        }

        return "redirect:/productManagement";
    }

    @GetMapping("/modifyProduct")
    public String returnDefaultProduct(Model model) {
        Product product12 = new Product(
                "DEFAULT VALUE",
                "DEFAULT VALUE",
                0,
                0,
                "DEFAULT VALUE",
                true, 0,
                inventoryService.getInventory().get(0));
        model.addAttribute("product", product12);

        return "modifyProduct";
    }

    @PostMapping("/getProductById")
    public String returnProductById(@RequestParam long id, Model model) {
        Product returnProduct = productService.getProductById(id);
        if (returnProduct != null) {
            model.addAttribute("product", returnProduct);
        }
        return "modifyProduct";
    }

    @PostMapping("/signup")
    public String addUser(
            @RequestParam String name,
            @RequestParam String shopName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String role,
            @RequestParam String userName,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model
    ) {
        Validation validation = new Validation(userService);
        HashMap<String, String> loginError = validation.getLoginError(
                name,
                shopName,
                email,
                phoneNumber,
                role,
                userName,
                password,
                confirmPassword);
        if (loginError.isEmpty()) {
            if (role.equals("staff")) {
                userService.addUser(new Staff(
                        null,
                        email,
                        name,
                        password,
                        phoneNumber,
                        role,
                        false,
                        userName));
            } else {
                Inventory newInventory = new Inventory();
                inventoryService.addInventory(newInventory);
                userService.addUser(new Manager(
                        shopName,
                        email,
                        name,
                        password,
                        phoneNumber,
                        role,
                        false,
                        userName,
                        newInventory
                ));
            }
        } else {
            String key = null;
            String value = null;
            for (String returnModel : loginError.keySet()) {
                key = returnModel;
            }
            for (String returnModel : loginError.values()) {
                value = returnModel;
            }
            model.addAttribute(key, value);
            return "signup";

        }
        return "redirect:/";
    }

    @PostMapping("/searchStaff")
    public String searchStaffOnEmailAndPhoneNumber(@RequestParam String email, @RequestParam String phoneNumber, Model model) {
        long id = managerServices.findStaffByEmailAndNumber(email, phoneNumber, currentManager);
        model.addAttribute("staffId", 0);
        if (id == -2) {
            model.addAttribute("staffNotFound", "Staff not found");
            return "searchStaff";
        } else if (id == -1) {
            model.addAttribute("currentStaff", "This staff is your own staff");
            return "searchStaff";
        } else {
            model.addAttribute("staffId", id);
            
            return "redirect:/applyStaff?staffId=" + id;
        }
    }

    @PostMapping("/getStaffById")
    public String returnStaffById(@RequestParam long id, Model model) {
        tempStaff = (Staff) userService.findUser(id);
        return "redirect:/applyStaff";
    }

    @GetMapping("/applyStaff")
    public String returnDefaultStaff(Model model) {
        model.addAttribute("staff", tempStaff);
        return "applyStaff";
    }

    @PostMapping("/applyStaffById")
    public String applyStaffById(@RequestParam long id) {
        managerServices.applyStaffByManager(id, currentManager);
        return "redirect:/staffManagement";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam long id) {
        productService.removeProduct(id);
        return "redirect:/productManagement";
    }

    @GetMapping("/staffManagement")
    public String getStaffList(Model model) {
        model.addAttribute("staffList", staffServices.getStaffByCurrentManager(currentManager));
        return "staffManagement";
    }

    @PostMapping("/logOut")
    public String logout() {
        this.currentManager = null;
        this.currentStaff = null;
        return "redirect:/";
    }

    @PostMapping("/returnDefaultProduct")
    public String turnbackToDefaultProduct() {
        currentProducts = null;
        return "redirect:/order(1)";
    }

}
