package com.jpmc.netbanking.service;

import com.jpmc.netbanking.model.Account;
import com.jpmc.netbanking.model.Users;

public interface AccountService {

	public Account createAccount(Users user);
	public void cashDeposit(String accountNumber, String pin, double amount);
	public void cashWithdrawal(String accountNumber, String pin, double amount);
	public void fundTransfer(String sourceAccountNumber, String targetAccountNumber, double amount);
	
	
}
