package com.jpmc.netbanking.service;
import com.jpmc.netbanking.dto.TransactionDTO;
import com.jpmc.netbanking.request.AccountTransactionSearchRequest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

	List<TransactionDTO> getTransactionsWithInDates(AccountTransactionSearchRequest accountTransactionSearchRequest);
}
