package com.jpmc.netbanking.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountTransactionSearchRequest {

    private String accountNumber;

    private String startDate;

    private String endDate;
}
