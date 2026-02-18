package com.aabuilders.Dashboard.Repository;

import com.aabuilders.Dashboard.Entity.ExpensesForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpensesRepo extends JpaRepository<ExpensesForm, Long> {
    @Query("SELECT e FROM ExpensesForm e WHERE FUNCTION('DATE', e.timestamp) = CURRENT_DATE")
    List<ExpensesForm> findTodayEntries();
    @Query("SELECT e FROM ExpensesForm e WHERE FUNCTION('DATE', e.timestamp) = :date")
    List<ExpensesForm> findBySpecificDate(@Param("date") LocalDate date);
    List<ExpensesForm> findBySiteName(String siteName);
    List<ExpensesForm> findByVendor(String vendor);
    List<ExpensesForm> findByBranchId(Long branchId);
    Optional<ExpensesForm> findByIdAndBranchId(Long id, Long branchId);
    @Query("SELECT e FROM ExpensesForm e WHERE e.accountType = 'Utility Bills' AND e.utilityType = 'Electricity'")
    List<ExpensesForm> findElectricityUtilityBills();
    @Query("SELECT e FROM ExpensesForm e WHERE e.accountType = 'Utility Bills' AND e.utilityType = 'Property'")
    List<ExpensesForm> findPropertyUtilityBills();
    @Query("SELECT e FROM ExpensesForm e WHERE e.accountType = 'Utility Bills' AND e.utilityType = 'Water'")
    List<ExpensesForm> findWaterUtilityBills();
    @Query("SELECT e FROM ExpensesForm e WHERE e.accountType = 'Utility Bills' AND e.utilityType = 'Telecom'")
    List<ExpensesForm> findTelecomUtilityBills();
    @Query("SELECT e FROM ExpensesForm e WHERE e.accountType = 'Utility Bills' AND e.utilityType = 'Subscription'")
    List<ExpensesForm> findSubscriptionUtilityBills();
    @Query("SELECT e FROM ExpensesForm e WHERE e.accountType = 'Utility Bills' AND e.utilityType = 'AMC'")
    List<ExpensesForm> findAmcUtilityBills();

}
