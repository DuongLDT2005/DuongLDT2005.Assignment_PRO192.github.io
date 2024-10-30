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
import com.example.assignment.model.product.service.InventoryService;
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
    private InventoryService inventoryService;

    @Autowired
    public ManagerServices(StaffService staffService, InvoiceService invoiceService,InventoryService inventoryService) {
        this.staffService = staffService;
        this.invoiceService = invoiceService;
        this.inventoryService=inventoryService;
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

    public void removeProduct(){
        
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

    public double getTotalProfit() {
        double totalProfit = 0;
        return totalProfit;

    }
}
