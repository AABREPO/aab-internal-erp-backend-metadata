package com.aabuilders.Dashboard.Repository;

import com.aabuilders.Dashboard.Entity.ExpensesForm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpensesRepoCustomImpl implements ExpensesRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ExpensesForm> findFilteredPage(Specification<ExpensesForm> spec, int offset, int limit, Sort sort) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExpensesForm> query = cb.createQuery(ExpensesForm.class);
        Root<ExpensesForm> root = query.from(ExpensesForm.class);

        Predicate predicate = spec.toPredicate(root, query, cb);
        if (predicate != null) {
            query.where(predicate);
        }

        if (sort != null && sort.isSorted()) {
            List<Order> orders = new ArrayList<>();
            for (Sort.Order order : sort) {
                orders.add(order.isAscending()
                        ? cb.asc(root.get(order.getProperty()))
                        : cb.desc(root.get(order.getProperty())));
            }
            query.orderBy(orders);
        }

        TypedQuery<ExpensesForm> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        return typedQuery.getResultList();
    }

    @Override
    public long countFiltered(Specification<ExpensesForm> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<ExpensesForm> root = query.from(ExpensesForm.class);
        query.select(cb.count(root));

        Predicate predicate = spec.toPredicate(root, query, cb);
        if (predicate != null) {
            query.where(predicate);
        }

        return entityManager.createQuery(query).getSingleResult();
    }
}
