package com.jpmc.netbanking.service;

import com.jpmc.netbanking.enums.TransactionType;
import com.jpmc.netbanking.exception.InsufficientBalanceException;
import com.jpmc.netbanking.exception.NotFoundException;
import com.jpmc.netbanking.model.Account;
import com.jpmc.netbanking.model.Transaction;
import com.jpmc.netbanking.model.Users;
import com.jpmc.netbanking.repository.AccountRepository;
import com.jpmc.netbanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
    private AccountRepository accountRepository;
	
	@Autowired
    private TransactionRepository transactionRepository;

	@Override
    @Transactional
    public Account createAccount(Users user) {
        String accountNumber = generateUniqueAccountNumber();
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(0.0);
        account.setUser(user);
        return accountRepository.saveAndFlush(account);
    }
    
	private String generateUniqueAccountNumber() {
	    String accountNumber;
	    do {
	        // Generate a UUID as the account number
	        accountNumber = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
	    } while (accountRepository.findByAccountNumber(accountNumber) != null);

	    return accountNumber;
	}

    
    

    

    
    @Override
    public void cashDeposit(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new NotFoundException("Account not found");
        }

        double currentBalance = account.getBalance();
        double newBalance = currentBalance + amount;
        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.CASH_DEPOSIT);;
        transaction.setTransaction_date(new Date());
        transaction.setSourceAccount(account);
        transactionRepository.save(transaction);
    }
    
    @Override
    public void cashWithdrawal(String accountNumber, double amount) {
    	
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new NotFoundException("Account not found");
        }


        double currentBalance = account.getBalance();
        if (currentBalance < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        double newBalance = currentBalance - amount;
        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.CASH_WITHDRAWAL);
        transaction.setTransaction_date(new Date());
        transaction.setSourceAccount(account);
        transactionRepository.save(transaction);
    }
    
    @Override
    public void fundTransfer(String sourceAccountNumber, String targetAccountNumber, double amount) {
        Account sourceAccount = accountRepository.findByAccountNumber(sourceAccountNumber);
        System.out.println("SourceAccount:"+ sourceAccount);
        if (sourceAccount == null) {
            throw new NotFoundException("Source account not found");
        }

        Account targetAccount = accountRepository.findByAccountNumber(targetAccountNumber);
        System.out.println("targetAccount:"+ targetAccount);
        if (targetAccount == null) {
            throw new NotFoundException("Target account not found");
        }


        double sourceBalance = sourceAccount.getBalance();
        if (sourceBalance < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        double newSourceBalance = sourceBalance - amount;
        sourceAccount.setBalance(newSourceBalance);
        accountRepository.save(sourceAccount);

        double targetBalance = targetAccount.getBalance();
        double newTargetBalance = targetBalance + amount;
        targetAccount.setBalance(newTargetBalance);
        accountRepository.save(targetAccount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.CASH_TRANSFER);
        transaction.setTransaction_date(new Date());
        transaction.setSourceAccount(sourceAccount);
        transaction.setTargetAccount(targetAccount);
        transactionRepository.save(transaction);
    }
}
