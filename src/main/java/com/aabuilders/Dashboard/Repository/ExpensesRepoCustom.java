package com.aabuilders.Dashboard.Repository;

import com.aabuilders.Dashboard.Entity.ExpensesForm;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ExpensesRepoCustom {
    List<ExpensesForm> findFilteredPage(Specification<ExpensesForm> spec, int offset, int limit, Sort sort);

    long countFiltered(Specification<ExpensesForm> spec);
}
