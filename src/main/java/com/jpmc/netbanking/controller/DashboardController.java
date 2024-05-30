package com.jpmc.netbanking.controller;


import com.jpmc.netbanking.dto.AccountRequest;
import com.jpmc.netbanking.dto.AccountResponse;
import com.jpmc.netbanking.dto.UserRequest;
import com.jpmc.netbanking.dto.UserResponse;
import com.jpmc.netbanking.model.Users;
import com.jpmc.netbanking.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUserDetails(@RequestBody UserRequest user) {
        String accountNumber = user.getAccountNumber();
        UserResponse userResponse = dashboardService.getUserDetails(accountNumber);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/account")
    public ResponseEntity<AccountResponse> getAccountDetails(@RequestBody AccountRequest accountRequest) {
        String accountNumber = accountRequest.getAccountNumber();
        AccountResponse accountResponse = dashboardService.getAccountDetails(accountNumber);
        return ResponseEntity.ok(accountResponse);
    }
    
    
   
}