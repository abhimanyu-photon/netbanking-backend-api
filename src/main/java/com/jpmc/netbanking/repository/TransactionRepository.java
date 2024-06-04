package com.jpmc.netbanking.repository;

import com.jpmc.netbanking.model.Account;
import com.jpmc.netbanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, AccountTransactionCustomRepository {
    // Add any custom query methods here, if needed

    //List<Transaction> findBySourceAccount_AccountNumberOrTargetAccount_AccountNumber(String sourceAccountNumber, String targetAccountNumber);
    //@Query("SELECT a FROM Transaction a WHERE a.user.id=:userId and a.transaction_date IN('','')")
    //List<Transaction> f(@Param("sourceAccountNumber") String sourceAccountNumber, @Param("sourceAccountNumber")String targetAccountNumber, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    //List<Transaction> findBySourceAccount_AccountNumberOrTargetAccount_AccountNumberAndDateBetween(String sourceAccountNumber, Date fromDate, Date toDate);
}
