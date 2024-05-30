package com.jpmc.netbanking.service;

import com.jpmc.netbanking.exception.NotFoundException;
import com.jpmc.netbanking.exception.UserValidation;
import com.jpmc.netbanking.model.Account;
import com.jpmc.netbanking.model.Users;
import com.jpmc.netbanking.repository.AccountRepository;
import com.jpmc.netbanking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
	private final UserRepository userRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final AccountService accountService;


    public UserServiceImpl(UserRepository userRepository, AccountService accountService, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.accountRepository=accountRepository;

    }
    
    @Override
    public Users getUserByAccountNumber(String accountNumber) {
        Account account= accountRepository.findByAccountNumber(accountNumber);
        Users user= account.getUser();
        return user;

    }
    
    @Override
    @Transactional
    public Users registerUser(Users user) {
        Users savedUser = userRepository.saveAndFlush(user);
        accountService.createAccount(savedUser);
        return savedUser;
    }

	@Override
	public void saveUser(Users user) {
		userRepository.save(user);
		
	}

    @Override
    public Users updateUser(String name,Users user) {
        Users existingUser = userRepository.findByName(name);
        if(user.getEmail() != null){
            if(user.getEmail().isEmpty())
                throw new UserValidation("Email can't be empty");
            else
                existingUser.setEmail(user.getEmail());
        }
        if(user.getName() != null){
            if(user.getName().isEmpty())
                throw new UserValidation("Name can't be empty");
            else
                existingUser.setName(user.getName());
        }
        if(user.getPhone_number() != null){
            if(user.getPhone_number().isEmpty())
                throw new UserValidation("Phone number can't be empty");
            else
                existingUser.setPhone_number(user.getPhone_number());
        }
        if(user.getAddress() != null){
            existingUser.setAddress(user.getAddress());
        }
        return userRepository.save(existingUser);
    }

    @Override
    public Users getUserByUserName(String userName) {
        Users user =userRepository.findByName(userName);
        if(ObjectUtils.isEmpty(user)){
            throw new NotFoundException("user not found");
        }
        return user;
    }




}
