package com.example.assignment.model.user.service;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.order.Invoice;
import com.example.assignment.model.order.Service.InvoiceService;
import com.example.assignment.model.product.Inventory;
import com.example.assignment.model.product.Product;
import com.example.assignment.model.product.service.ProductService;
import com.example.assignment.model.user.Manager;
import com.example.assignment.model.user.Staff;
import com.example.assignment.model.user.User;

@Service
public class ManagerServices {

    private UserService userService;
    private StaffService staffService;
    private Inventory inventoryManager;
    private InvoiceService invoiceService;
    private ProductService productService;


    @Autowired
    public ManagerServices(
            StaffService staffService,
            InvoiceService invoiceService,
            UserService userService) {
        this.staffService = staffService;
        this.invoiceService = invoiceService;
        this.userService = userService;
    }

    public void removeStaff(long id) {
        userService.removeUser(id);
    }

    public List<User> getStaffs() {
        return staffService.getStaff();
    }

    public int getOrdersAmount() {
        return invoiceService.getAmount();
    }

    public List<Product> getListProduct(Manager manager) {
        return manager.getInventoryManager().getProducts();
    }

    public double getStaffSalary(long id) {
        double salary = 0;
        for (User user : getStaffs()) {
            if (id == user.getId()) {
                salary = ((Staff) user).getSalary();
            }
        }
        return salary;
    }

    public double getTotalMoneyByDay(LocalDate date) {
        List<Invoice> invoices = invoiceService.getInvoice();
        double total = 0;
        for (Invoice invoice : invoices) {
            LocalDate invoiceDate = LocalDate.from(invoice.getInvoiceDate());
            if (invoiceDate.equals(date)) {
                total += invoice.getTotalAmount();
            }
        }
        return total;
    }

    public double getTotalMoneyByMonth(YearMonth month) {
        List<Invoice> invoices = invoiceService.getInvoice();
        double total = 0;
        for (Invoice invoice : invoices) {
            YearMonth invoiceMonth = YearMonth.from(invoice.getInvoiceDate());
            if (invoiceMonth.equals(month)) {
                total += invoice.getTotalAmount();
            }
        }
        return total;
    }

    public double getTotalMoneyByYear(Year year) {
        List<Invoice> invoices = invoiceService.getInvoice();
        double total = 0;
        for (Invoice invoice : invoices) {
            Year invoiceYear = Year.from(invoice.getInvoiceDate());
            if (invoiceYear.equals(year)) {
                total += invoice.getTotalAmount();
            }
        }
        return total;
    }

    public long findStaffByEmailAndNumber(String email, String phonenumber, Manager currentManager) {
        String shopNameOfManager = currentManager.getShopName();
        for (User staff : staffService.getStaff()) {
            if (staff.getEmail().equals(email) && staff.getPhonenumber().equals(phonenumber)) {
                if (staff.getShopName()==null) {
                    return staff.getId();//da tim thay staff<3
                }

                if (staff.getShopName().equals(shopNameOfManager)) {
                    return -1;//staff da ton tai trong cua hang
                }
                return staff.getId();//da tim thay staff<3
            }
        }
        return -2;//staff khong ton tai
    }

    public void applyStaffByManager(long id, Manager currentManager) {
        String shopNameOfManager = currentManager.getShopName();
        User staff = userService.findUser(id);
        if (staff.getShopName() == null) {
            staff.setShopName(shopNameOfManager);
            userService.addUser(staff);
        } else {
            Staff newuser = new Staff();
            newuser.setUsername(staff.getUsername());
            newuser.setPassword(staff.getPassword());
            newuser.setRole(staff.getRole());
            newuser.setName(staff.getName());
            newuser.setEmail(staff.getEmail());
            newuser.setPhonenumber(staff.getPhonenumber());
            newuser.setShopName(shopNameOfManager);
            userService.addUser(newuser);
        }
    }


}
