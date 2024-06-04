package com.jpmc.netbanking.repository;

import com.jpmc.netbanking.model.Account;
import com.jpmc.netbanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.accountNumber=:accountNumber")
    Account findByAccountNumber(String accountNumber);

    @Query("SELECT a FROM Account a WHERE a.user.id=:userId")
    Account findByUserId(@Param("userId") Long userId);
}
