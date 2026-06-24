package com.aabuilders.Dashboard.Repository;

import com.aabuilders.Dashboard.DTO.ExpensesFilterDto;
import com.aabuilders.Dashboard.Entity.ExpensesForm;
import org.springframework.data.jpa.domain.Specification;

public final class ExpensesFormSpecification {

    private ExpensesFormSpecification() {
    }

    public static Specification<ExpensesForm> fromFilter(ExpensesFilterDto filter) {
        return Specification.where(hasBranchId(filter.getBranchId()))
                .and(hasSiteName(filter.getSiteName()))
                .and(hasProjectId(filter.getProjectId()))
                .and(hasVendor(filter.getVendor()))
                .and(hasVendorId(filter.getVendorId()))
                .and(hasContractor(filter.getContractor()))
                .and(hasContractorId(filter.getContractorId()))
                .and(hasCategory(filter.getCategory()))
                .and(hasAccountType(filter.getAccountType()))
                .and(hasAccountTypeId(filter.getAccountTypeId()))
                .and(hasPaymentMode(filter.getPaymentMode()))
                .and(hasEnteredBy(filter.getEnteredBy()))
                .and(hasSource(filter.getSource()))
                .and(hasUtilityType(filter.getUtilityType()))
                .and(hasMachineTools(filter.getMachineTools()))
                .and(hasDateFrom(filter.getDateFrom()))
                .and(hasDateTo(filter.getDateTo()))
                .and(hasTimestampFrom(filter.getTimestampFrom()))
                .and(hasTimestampTo(filter.getTimestampTo()))
                .and(hasAmountMin(filter.getAmountMin()))
                .and(hasAmountMax(filter.getAmountMax()));
    }

    private static Specification<ExpensesForm> hasBranchId(Long branchId) {
        return (root, query, cb) -> branchId == null ? null : cb.equal(root.get("branchId"), branchId);
    }

    private static Specification<ExpensesForm> hasSiteName(String siteName) {
        return (root, query, cb) -> isBlank(siteName) ? null : cb.equal(root.get("siteName"), siteName);
    }

    private static Specification<ExpensesForm> hasProjectId(Long projectId) {
        return (root, query, cb) -> projectId == null ? null : cb.equal(root.get("projectId"), projectId);
    }

    private static Specification<ExpensesForm> hasVendor(String vendor) {
        return (root, query, cb) -> isBlank(vendor) ? null : cb.equal(root.get("vendor"), vendor);
    }

    private static Specification<ExpensesForm> hasVendorId(Long vendorId) {
        return (root, query, cb) -> vendorId == null ? null : cb.equal(root.get("vendorId"), vendorId);
    }

    private static Specification<ExpensesForm> hasContractor(String contractor) {
        return (root, query, cb) -> isBlank(contractor) ? null : cb.equal(root.get("contractor"), contractor);
    }

    private static Specification<ExpensesForm> hasContractorId(Long contractorId) {
        return (root, query, cb) -> contractorId == null ? null : cb.equal(root.get("contractorId"), contractorId);
    }

    private static Specification<ExpensesForm> hasCategory(String category) {
        return (root, query, cb) -> isBlank(category) ? null : cb.equal(root.get("category"), category);
    }

    private static Specification<ExpensesForm> hasAccountType(String accountType) {
        return (root, query, cb) -> isBlank(accountType) ? null : cb.equal(root.get("accountType"), accountType);
    }

    private static Specification<ExpensesForm> hasAccountTypeId(Long accountTypeId) {
        return (root, query, cb) -> accountTypeId == null ? null : cb.equal(root.get("accountTypeId"), accountTypeId);
    }

    private static Specification<ExpensesForm> hasPaymentMode(String paymentMode) {
        return (root, query, cb) -> isBlank(paymentMode) ? null : cb.equal(root.get("paymentMode"), paymentMode);
    }

    private static Specification<ExpensesForm> hasEnteredBy(String enteredBy) {
        return (root, query, cb) -> isBlank(enteredBy) ? null : cb.equal(root.get("enteredBy"), enteredBy);
    }

    private static Specification<ExpensesForm> hasSource(String source) {
        return (root, query, cb) -> isBlank(source) ? null : cb.equal(root.get("source"), source);
    }

    private static Specification<ExpensesForm> hasUtilityType(String utilityType) {
        return (root, query, cb) -> isBlank(utilityType) ? null : cb.equal(root.get("utilityType"), utilityType);
    }

    private static Specification<ExpensesForm> hasMachineTools(String machineTools) {
        return (root, query, cb) -> isBlank(machineTools) ? null : cb.equal(root.get("machineTools"), machineTools);
    }

    private static Specification<ExpensesForm> hasDateFrom(java.time.LocalDate dateFrom) {
        return (root, query, cb) -> dateFrom == null ? null : cb.greaterThanOrEqualTo(root.get("date"), dateFrom);
    }

    private static Specification<ExpensesForm> hasDateTo(java.time.LocalDate dateTo) {
        return (root, query, cb) -> dateTo == null ? null : cb.lessThanOrEqualTo(root.get("date"), dateTo);
    }

    private static Specification<ExpensesForm> hasTimestampFrom(java.time.LocalDateTime timestampFrom) {
        return (root, query, cb) -> timestampFrom == null ? null : cb.greaterThanOrEqualTo(root.get("timestamp"), timestampFrom);
    }

    private static Specification<ExpensesForm> hasTimestampTo(java.time.LocalDateTime timestampTo) {
        return (root, query, cb) -> timestampTo == null ? null : cb.lessThanOrEqualTo(root.get("timestamp"), timestampTo);
    }

    private static Specification<ExpensesForm> hasAmountMin(Integer amountMin) {
        return (root, query, cb) -> amountMin == null ? null : cb.greaterThanOrEqualTo(root.get("amount"), amountMin);
    }

    private static Specification<ExpensesForm> hasAmountMax(Integer amountMax) {
        return (root, query, cb) -> amountMax == null ? null : cb.lessThanOrEqualTo(root.get("amount"), amountMax);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
