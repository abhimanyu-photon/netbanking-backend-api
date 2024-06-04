package com.jpmc.netbanking.repository;

import com.jpmc.netbanking.model.Transaction;
import com.jpmc.netbanking.request.AccountTransactionSearchRequest;

import java.util.List;

public interface AccountTransactionCustomRepository {

    List<Transaction> fetchAllTransactions(AccountTransactionSearchRequest searchRequest);
}
