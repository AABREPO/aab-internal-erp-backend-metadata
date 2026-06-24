package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.Entity.*;
import com.aabuilders.Dashboard.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Service to initialize sample data for the purchase order system.
 * This service runs on application startup to populate the database with sample data.
 */
@Service
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private VendorNameRepository vendorNameRepository;
    
    @Autowired
    private ExpensesRepo expensesRepo;
    
    @Autowired
    private ExpensesCategoryRepository expensesCategoryRepository;
    
    @Autowired
    private MachineToolsRepository machineToolsRepository;
    
    @Autowired
    private AccountTypeExpenseRepository accountTypeExpenseRepository;

    @Override
    public void run(String... args) throws Exception {
        initializeVendorData();
        initializeExpenseCategories();
        initializeMachineTools();
        initializeAccountTypes();
        initializeExpenseData();
    }

    /**
     * Initialize vendor/supplier data for purchase orders.
     */
    private void initializeVendorData() {
        if (vendorNameRepository.count() == 0) {
            List<VendorNames> vendors = Arrays.asList(
                createVendor("ABC Construction Supplies", "Construction materials and tools"),
                createVendor("XYZ Hardware Store", "Hardware and electrical supplies"),
                createVendor("Premium Steel Co.", "Steel and metal supplies"),
                createVendor("Quality Cement Ltd.", "Cement and concrete materials"),
                createVendor("Modern Plumbing Solutions", "Plumbing fixtures and materials")
            );
            vendorNameRepository.saveAll(vendors);
            System.out.println("✅ Initialized 5 vendor records");
        }
    }

    /**
     * Initialize expense categories for purchase orders.
     */
    private void initializeExpenseCategories() {
        if (expensesCategoryRepository.count() == 0) {
            List<ExpensesCategory> categories = Arrays.asList(
                createExpenseCategory("Construction Materials", "Building materials and supplies"),
                createExpenseCategory("Electrical Supplies", "Electrical equipment and wiring"),
                createExpenseCategory("Plumbing Materials", "Plumbing fixtures and pipes"),
                createExpenseCategory("Tools and Equipment", "Hand tools and machinery"),
                createExpenseCategory("Safety Equipment", "Safety gear and protective equipment")
            );
            expensesCategoryRepository.saveAll(categories);
            System.out.println("✅ Initialized 5 expense category records");
        }
    }

    /**
     * Initialize machine tools and equipment data.
     */
    private void initializeMachineTools() {
        if (machineToolsRepository.count() == 0) {
            List<MachineTools> tools = Arrays.asList(
                createMachineTool("Excavator", "Heavy construction equipment"),
                createMachineTool("Concrete Mixer", "Mixing equipment for concrete"),
                createMachineTool("Crane", "Lifting and moving equipment"),
                createMachineTool("Drill Machine", "Drilling and cutting tools"),
                createMachineTool("Safety Harness", "Personal protective equipment")
            );
            machineToolsRepository.saveAll(tools);
            System.out.println("✅ Initialized 5 machine tools records");
        }
    }

    /**
     * Initialize account types for expenses.
     */
    private void initializeAccountTypes() {
        if (accountTypeExpenseRepository.count() == 0) {
            List<AccountTypeExpense> accountTypes = Arrays.asList(
                createAccountType("Operating Expenses", "Day-to-day operational costs"),
                createAccountType("Capital Expenditure", "Long-term asset investments"),
                createAccountType("Maintenance Costs", "Equipment and facility maintenance"),
                createAccountType("Safety Equipment", "Safety and protective gear"),
                createAccountType("Project Materials", "Project-specific materials")
            );
            accountTypeExpenseRepository.saveAll(accountTypes);
            System.out.println("✅ Initialized 5 account type records");
        }
    }

    /**
     * Initialize sample expense/purchase order data.
     */
    private void initializeExpenseData() {
        if (expensesRepo.count() == 0) {
            List<ExpensesForm> expenses = Arrays.asList(
                createExpense("Operating Expenses", 1001L, "ABC Construction Supplies", "Premium Steel Co.", "50 tons", "Quality Cement Ltd.", 250000, "Construction Materials", "Steel beams for structural support", "Excavator"),
                createExpense("Capital Expenditure", 1002L, "XYZ Hardware Store", "Modern Plumbing Solutions", "100 units", "ABC Construction Supplies", 150000, "Plumbing Materials", "High-quality plumbing fixtures", "Concrete Mixer"),
                createExpense("Maintenance Costs", 1003L, "Premium Steel Co.", "Quality Cement Ltd.", "25 bags", "XYZ Hardware Store", 75000, "Construction Materials", "Cement for foundation work", "Drill Machine"),
                createExpense("Safety Equipment", 1004L, "Modern Plumbing Solutions", "ABC Construction Supplies", "20 sets", "Premium Steel Co.", 50000, "Safety Equipment", "Safety harnesses for workers", "Safety Harness"),
                createExpense("Project Materials", 1005L, "Quality Cement Ltd.", "XYZ Hardware Store", "200 meters", "Modern Plumbing Solutions", 120000, "Electrical Supplies", "Electrical wiring for new building", "Crane"),
                createExpense("Operating Expenses", 1006L, "Premium Steel Co.", "ABC Construction Supplies", "30 tons", "Modern Plumbing Solutions", 180000, "Construction Materials", "Reinforcement steel for columns", "Crane"),
                createExpense("Capital Expenditure", 1007L, "Quality Cement Ltd.", "XYZ Hardware Store", "150 units", "Premium Steel Co.", 225000, "Electrical Supplies", "LED lighting fixtures for office", "Drill Machine"),
                createExpense("Maintenance Costs", 1008L, "Modern Plumbing Solutions", "ABC Construction Supplies", "40 bags", "Quality Cement Ltd.", 120000, "Construction Materials", "High-strength concrete mix", "Concrete Mixer"),
                createExpense("Safety Equipment", 1009L, "XYZ Hardware Store", "Premium Steel Co.", "15 sets", "Modern Plumbing Solutions", 45000, "Safety Equipment", "Hard hats and safety goggles", "Safety Harness"),
                createExpense("Project Materials", 1010L, "ABC Construction Supplies", "Quality Cement Ltd.", "300 meters", "XYZ Hardware Store", 180000, "Electrical Supplies", "Copper wiring for electrical system", "Excavator")
            );
            expensesRepo.saveAll(expenses);
            System.out.println("✅ Initialized 10 expense/purchase order records");
        }
    }

    private VendorNames createVendor(String name, String description) {
        VendorNames vendor = new VendorNames();
        vendor.setVendorName(name);
        return vendor;
    }

    private ExpensesCategory createExpenseCategory(String name, String description) {
        ExpensesCategory category = new ExpensesCategory();
        category.setCategory(name);
        return category;
    }

    private MachineTools createMachineTool(String name, String description) {
        MachineTools tool = new MachineTools();
        tool.setMachineTool(name);
        return tool;
    }

    private AccountTypeExpense createAccountType(String name, String description) {
        AccountTypeExpense accountType = new AccountTypeExpense();
        accountType.setAccountType(name);
        return accountType;
    }

    private ExpensesForm createExpense(String accountType, Long eno, String siteName, String vendor,
                                     String quantity, String contractor, int amount, String category, 
                                     String comments, String machineTools) {
        ExpensesForm expense = new ExpensesForm();
        expense.setAccountType(accountType);
        expense.setENo(eno);
        expense.setTimestamp(LocalDateTime.now());
        expense.setDate(LocalDate.now());
        expense.setSiteName(siteName);
        expense.setVendor(vendor);
        expense.setQuantity(quantity);
        expense.setContractor(contractor);
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setComments(comments);
        expense.setMachineTools(machineTools);
        return expense;
    }
} 