package com.jpmc.netbanking.service;

import com.jpmc.netbanking.dto.AccountResponse;
import com.jpmc.netbanking.dto.UserResponse;

public interface DashboardService {
    UserResponse getUserDetails(String accountNumber);
    AccountResponse getAccountDetails(String accountNumber);
}