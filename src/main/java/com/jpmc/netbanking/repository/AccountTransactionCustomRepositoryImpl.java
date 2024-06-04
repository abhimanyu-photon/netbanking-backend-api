package com.jpmc.netbanking.repository;

import com.jpmc.netbanking.model.Transaction;
import com.jpmc.netbanking.request.AccountTransactionSearchRequest;
import com.jpmc.netbanking.util.DateUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountTransactionCustomRepositoryImpl implements AccountTransactionCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Transaction> fetchAllTransactions(AccountTransactionSearchRequest searchRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = cb.createQuery(Transaction.class);
        Root<Transaction> root = criteriaQuery.from(Transaction.class);
        List<Predicate> conditions = new ArrayList<>();
        Predicate accountNumberPredicate = cb.equal(root.get("sourceAccount").get("accountNumber"), searchRequest.getAccountNumber());
        conditions.add(accountNumberPredicate);
        if(searchRequest.getStartDate() != null) {
            LocalDate sDate = DateUtils.convertStringToLocalDate(searchRequest.getStartDate());
            Predicate txnStartDatePredicate = cb.greaterThanOrEqualTo(root.get("transaction_date"), sDate);
            conditions.add(txnStartDatePredicate);

            LocalDate eDate = DateUtils.convertStringToLocalDate(searchRequest.getEndDate());
            Predicate txnEndDatePre = cb.lessThanOrEqualTo(root.get("transaction_date"), eDate);
            conditions.add(txnEndDatePre);
        }
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        TypedQuery<Transaction> results = entityManager.createQuery(criteriaQuery);
        return results.getResultList();
    }
}
