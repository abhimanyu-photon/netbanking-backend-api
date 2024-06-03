package com.jpmc.netbanking.controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.jpmc.netbanking.dto.LoginRequest;
import com.jpmc.netbanking.dto.UserResponse;
import com.jpmc.netbanking.exception.UserValidation;
import com.jpmc.netbanking.model.Account;
import com.jpmc.netbanking.model.Users;
import com.jpmc.netbanking.repository.AccountRepository;
import com.jpmc.netbanking.service.AccountService;
import com.jpmc.netbanking.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    private final AccountRepository accountRepository;

    public UserController(UserService userService, AccountRepository accountRepository) {
        this.userService =  userService;
        this.accountRepository = accountRepository;
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody Users user) {
        if(String.valueOf(user.getPhone_number()).length() != 10) {
            throw new UserValidation("Phone number should be 10 digit");
        }
        Users registeredUser = userService.registerUser(user);
        
        UserResponse userResponse = new UserResponse();
        userResponse.setName(registeredUser.getName());
        userResponse.setEmail(registeredUser.getEmail());
        userResponse.setAddress(registeredUser.getAddress());
        userResponse.setPhone_number(registeredUser.getPhone_number());
        Account account = this.accountRepository.findByUserId(registeredUser.getId());
        userResponse.setAccountNumber(account.getAccountNumber());
        userResponse.setIFSC_code(account.getIFSC_code());
        userResponse.setBranch(account.getBranch());
        userResponse.setAccount_type(account.getAccount_type());
        

        return ResponseEntity.ok(userResponse);
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        Users user=null;
        UserResponse userResponse = new UserResponse();
        try {
            user=userService.getUserByUserNameAndPassword(loginRequest.getName(),loginRequest.getPassword()) ;
            if (ObjectUtils.isEmpty(user)){
                userResponse.setStatusCode(401);
                userResponse.setStatusMessage("Invalid name or password");
            }else {
                userResponse.setName(user.getName());
                userResponse.setEmail(user.getEmail());
                userResponse.setAddress(user.getAddress());
                userResponse.setPhone_number(user.getPhone_number());
                Account account = this.accountRepository.findByUserId(user.getId());
                userResponse.setAccountNumber(account.getAccountNumber());
                userResponse.setIFSC_code(account.getIFSC_code());
                userResponse.setBranch(account.getBranch());
                userResponse.setAccount_type(account.getAccount_type());
                userResponse.setStatusCode(200);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }


        return ResponseEntity.status(userResponse.getStatusCode()).body(userResponse);
    }


    @PostMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestParam String name, @RequestBody Users user) {
        Users updateUser = userService.updateUser(name,user);
        UserResponse userResponse = new UserResponse();
        userResponse.setName(updateUser.getName());
        userResponse.setEmail(updateUser.getEmail());
        userResponse.setAddress(updateUser.getAddress());
        userResponse.setPhone_number(updateUser.getPhone_number());
        userResponse.setStatusCode(200);
        return ResponseEntity.status(userResponse.getStatusCode()).body(userResponse);
    }

}
