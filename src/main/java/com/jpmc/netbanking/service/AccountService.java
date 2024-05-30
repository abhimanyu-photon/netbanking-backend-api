package com.jpmc.netbanking.service;

import com.jpmc.netbanking.model.Account;
import com.jpmc.netbanking.model.Users;

public interface AccountService {

	public Account createAccount(Users user);
	public void cashDeposit(String accountNumber, double amount);
	public void cashWithdrawal(String accountNumber, double amount);
	public void fundTransfer(String sourceAccountNumber, String targetAccountNumber, double amount);
	
	
}
