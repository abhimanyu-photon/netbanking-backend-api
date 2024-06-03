package com.jpmc.netbanking.service;



import com.jpmc.netbanking.model.Users;

public interface UserService {
	public Users registerUser(Users user);

	Users getUserByAccountNumber(String account_no);

	public void saveUser(Users user);

	Users updateUser(String name,Users user);
	Users getUserByUserName(String userName);
	Users getUserByUserNameAndPassword(String userName, String password);
}
