package com.jpmc.netbanking.controller;


import com.jpmc.netbanking.dto.LoginRequest;
import com.jpmc.netbanking.dto.UserResponse;
import com.jpmc.netbanking.model.Account;
import com.jpmc.netbanking.model.Users;
import com.jpmc.netbanking.repository.AccountRepository;
import com.jpmc.netbanking.service.AccountService;
import com.jpmc.netbanking.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Users user=null;
        try {
            user=userService.getUserByUserName(loginRequest.getName()) ;
        } catch (Exception e) {
            // Invalid credentials, return 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid name or password");
        }


        return new ResponseEntity<>("login successfully",HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestParam String name, @RequestBody Users user) {
        Users updateUser = userService.updateUser(name,user);
        UserResponse userResponse = new UserResponse();
        userResponse.setName(updateUser.getName());
        userResponse.setEmail(updateUser.getEmail());
        return ResponseEntity.ok(userResponse);
    }

}
