package com.aabuilders.Dashboard.Service;

import com.aabuilders.Dashboard.DTO.ExpensesEdit;
import com.aabuilders.Dashboard.DTO.ExpensesFilterDto;
import com.aabuilders.Dashboard.Entity.ExpensesAudit;
import com.aabuilders.Dashboard.Entity.ExpensesForm;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

public interface ExpensesServices {
    ExpensesForm saveForm(ExpensesForm expensesForm, Long branchId);
    List<ExpensesForm> getAllEntries();
    List<ExpensesForm> getLast400Entries();
    StreamingResponseBody streamFilteredExpenses(ExpensesFilterDto filter);
    List<ExpensesAudit> getAllAuditsEntries();
    boolean updateExpense(Long id, ExpensesEdit expensesEdit);
    List<ExpensesAudit> getAllAuditsByExpenseId(Long expenseId);
    boolean clearExpenseData(Long id, String editedBy);
    List<ExpensesForm> getElectricityUtilityBills();
    List<ExpensesForm> getPropertyUtilityBills();
    List<ExpensesForm> getWaterUtilityBills();
    List<ExpensesForm> getTelecomUtilityBills();
    List<ExpensesForm> getSubscriptionUtilityBills();
    List<ExpensesForm> getAmcUtilityBills();
    List<ExpensesForm> getProfessionalUtilityBills();
    ExpensesForm saveFormWithFixedTimestamp(ExpensesForm expensesForm, Long branchId);
}
