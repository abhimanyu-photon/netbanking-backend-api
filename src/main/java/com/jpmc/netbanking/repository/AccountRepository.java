package com.jpmc.netbanking.repository;

import com.jpmc.netbanking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumber(String accountNumber);

    @Query("SELECT a FROM Account a WHERE a.user.id=:userId")
    Account findByUserId(@Param("userId") Long userId);
}
