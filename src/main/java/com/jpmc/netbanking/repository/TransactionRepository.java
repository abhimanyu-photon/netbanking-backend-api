package com.jpmc.netbanking.repository;

import com.jpmc.netbanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Add any custom query methods here, if needed

    List<Transaction> findBySourceAccount_AccountNumberOrTargetAccount_AccountNumber(String sourceAccountNumber, String targetAccountNumber);

}
