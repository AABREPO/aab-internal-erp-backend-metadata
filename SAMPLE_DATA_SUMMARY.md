# 📊 Netstock Purchase Order - Sample Data Summary

## ✅ Successfully Initialized Sample Data

The following sample data has been inserted into the database for the construction/building management system:

---

## 🏢 **Vendors/Suppliers (5 Records)**

| ID | Vendor Name | Description |
|----|-------------|-------------|
| 1 | ABC Construction Supplies | Construction materials and tools |
| 2 | XYZ Hardware Store | Hardware and electrical supplies |
| 3 | Premium Steel Co. | Steel and metal supplies |
| 4 | Quality Cement Ltd. | Cement and concrete materials |
| 5 | Modern Plumbing Solutions | Plumbing fixtures and materials |

**API Endpoint**: `GET /api/vendor_Names/getAll`

---

## 📂 **Expense Categories (5 Records)**

| ID | Category | Description |
|----|----------|-------------|
| 1 | Construction Materials | Building materials and supplies |
| 2 | Electrical Supplies | Electrical equipment and wiring |
| 3 | Plumbing Materials | Plumbing fixtures and pipes |
| 4 | Tools and Equipment | Hand tools and machinery |
| 5 | Safety Equipment | Safety gear and protective equipment |

**API Endpoint**: `GET /api/expenses_categories/getAll`

---

## 🛠️ **Machine Tools & Equipment (5 Records)**

| ID | Tool Name | Description |
|----|-----------|-------------|
| 1 | Excavator | Heavy construction equipment |
| 2 | Concrete Mixer | Mixing equipment for concrete |
| 3 | Crane | Lifting and moving equipment |
| 4 | Drill Machine | Drilling and cutting tools |
| 5 | Safety Harness | Personal protective equipment |

**API Endpoint**: `GET /api/machine_tools/getAll`

---

## 💰 **Account Types (5 Records)**

| ID | Account Type | Description |
|----|--------------|-------------|
| 1 | Operating Expenses | Day-to-day operational costs |
| 2 | Capital Expenditure | Long-term asset investments |
| 3 | Maintenance Costs | Equipment and facility maintenance |
| 4 | Safety Equipment | Safety and protective gear |
| 5 | Project Materials | Project-specific materials |

**API Endpoint**: `GET /api/account_type/getAll`

---

## 📋 **Purchase Orders/Expenses (5 Records)**

| ID | ENo | Account Type | Site Name | Vendor | Quantity | Contractor | Amount | Category | Comments | Machine Tools |
|----|-----|--------------|-----------|--------|----------|------------|--------|----------|----------|---------------|
| 1 | 1001 | Operating Expenses | ABC Construction Supplies | Premium Steel Co. | 50 tons | Quality Cement Ltd. | ₹250,000 | Construction Materials | Steel beams for structural support | Excavator |
| 2 | 1002 | Capital Expenditure | XYZ Hardware Store | Modern Plumbing Solutions | 100 units | ABC Construction Supplies | ₹150,000 | Plumbing Materials | High-quality plumbing fixtures | Concrete Mixer |
| 3 | 1003 | Maintenance Costs | Premium Steel Co. | Quality Cement Ltd. | 25 bags | XYZ Hardware Store | ₹75,000 | Construction Materials | Cement for foundation work | Drill Machine |
| 4 | 1004 | Safety Equipment | Modern Plumbing Solutions | ABC Construction Supplies | 20 sets | Premium Steel Co. | ₹50,000 | Safety Equipment | Safety harnesses for workers | Safety Harness |
| 5 | 1005 | Project Materials | Quality Cement Ltd. | XYZ Hardware Store | 200 meters | Modern Plumbing Solutions | ₹120,000 | Electrical Supplies | Electrical wiring for new building | Crane |

**API Endpoint**: `GET /expenses_form/get_form`

---

## 🔧 **Technical Implementation**

### **Data Initialization Service**
- **File**: `src/main/java/com/aabuilders/Dashboard/Service/DataInitializationService.java`
- **Purpose**: Automatically populates the database with sample data on application startup
- **Implementation**: Implements `CommandLineRunner` to run after Spring Boot context is loaded
- **Safety**: Only inserts data if the respective tables are empty (prevents duplicate data)

### **Key Features**
- ✅ **Automatic Initialization**: Runs on application startup
- ✅ **Duplicate Prevention**: Only inserts if tables are empty
- ✅ **Comprehensive Data**: Covers all major entities in the system
- ✅ **Realistic Data**: Uses realistic construction industry data
- ✅ **API Ready**: All data accessible via REST endpoints

### **Database Tables Populated**
1. `vendor_names` - Supplier information
2. `expenses_categories` - Expense categorization
3. `machine_tools` - Equipment and tools
4. `account_type_expense` - Account type classification
5. `expenses_form` - Purchase orders and expenses

---

## 🚀 **How to Access the Data**

### **Via REST API**
```bash
# Get all vendors
curl http://localhost:8081/api/vendor_Names/getAll

# Get all expense categories
curl http://localhost:8081/api/expenses_categories/getAll

# Get all machine tools
curl http://localhost:8081/api/machine_tools/getAll

# Get all account types
curl http://localhost:8081/api/account_type/getAll

# Get all purchase orders/expenses
curl http://localhost:8081/expenses_form/get_form
```

### **Via Database**
```sql
-- Check vendors
SELECT * FROM vendor_names;

-- Check expense categories
SELECT * FROM expenses_categories;

-- Check machine tools
SELECT * FROM machine_tools;

-- Check account types
SELECT * FROM account_type_expense;

-- Check purchase orders
SELECT * FROM expenses_form;
```

---

## 📈 **Data Statistics**

- **Total Records Created**: 25 records across 5 tables
- **Vendors**: 5 suppliers for construction materials
- **Categories**: 5 expense categories
- **Tools**: 5 machine tools and equipment
- **Account Types**: 5 financial classifications
- **Purchase Orders**: 5 sample orders with realistic data

---

## 🎯 **Use Cases Supported**

1. **Purchase Order Management**: Complete PO lifecycle with vendors, items, and costs
2. **Expense Tracking**: Categorized expense management
3. **Inventory Management**: Equipment and tool tracking
4. **Financial Reporting**: Account type-based reporting
5. **Supplier Management**: Vendor relationship management

---

*Data initialized successfully on: 2025-08-02*
*Application running on: http://localhost:8081* 