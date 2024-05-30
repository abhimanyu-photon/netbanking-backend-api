package com.jpmc.netbanking.service;
import com.jpmc.netbanking.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {

	List<TransactionDTO> getAllTransactionsByAccountNumber(String accountNumber);

}
