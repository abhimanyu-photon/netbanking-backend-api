package com.jpmc.netbanking.service;

import com.jpmc.netbanking.dto.TransactionDTO;
import com.jpmc.netbanking.mapper.TransactionMapper;
import com.jpmc.netbanking.model.Account;
import com.jpmc.netbanking.model.Transaction;
import com.jpmc.netbanking.repository.AccountRepository;
import com.jpmc.netbanking.repository.TransactionRepository;
import com.jpmc.netbanking.request.AccountTransactionSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;

	private final AccountRepository accountRepository;

	private final TransactionMapper transactionMapper;

	@Override
	public List<TransactionDTO> getTransactionsWithInDates(AccountTransactionSearchRequest accountTransactionSearchRequest) {
		List<Transaction> transactions=transactionRepository.fetchAllTransactions(accountTransactionSearchRequest);
		return transactions.stream()
				.map(transactionMapper::toDto)
				.toList();
	}
}
