package com.jpmc.netbanking.controller;

import com.jpmc.netbanking.dto.*;
import com.jpmc.netbanking.request.AccountTransactionSearchRequest;
import com.jpmc.netbanking.service.AccountService;
import com.jpmc.netbanking.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jpmc.netbanking.util.DateUtils.convertStringToDate;
import static com.jpmc.netbanking.util.DateUtils.convertStringToLocalDate;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<?> cashDeposit(@RequestBody AmountRequest amountRequest) {

        if (amountRequest.getAmount() <= 0) {
            Map<String, String> err = new HashMap<>();
            err.put("Error", "Invalid amount");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }

        accountService.cashDeposit(amountRequest.getAccountNumber(), amountRequest.getAmount());

        Map<String, String> response = new HashMap<>();
        response.put("msg", "Cash deposited successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> cashWithdrawal(@RequestBody AmountRequest amountRequest) {
        if (amountRequest.getAmount() <= 0) {
            Map<String, String> err = new HashMap<>();
            err.put("Error", "Invalid amount");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
        accountService.cashWithdrawal(amountRequest.getAccountNumber(),
                amountRequest.getAmount());

        Map<String, String> response = new HashMap<>();
        response.put("msg", "Cash withdrawn successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/fund-transfer")
    public ResponseEntity<?> fundTransfer(@RequestBody FundTransferRequest fundTransferRequest) {
        if (fundTransferRequest.getAmount() <= 0) {
            Map<String, String> err = new HashMap<>();
            err.put("Error", "Invalid amount");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }

        accountService.fundTransfer(fundTransferRequest.getSourceAccountNumber(), fundTransferRequest.getTargetAccountNumber(),
                 fundTransferRequest.getAmount());
        Map<String, String> response = new HashMap<>();
        response.put("msg", "Fund transferred successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactionsWithInDates(@RequestParam() String accountNumber,
                                                                           @RequestParam(required = false) String fromDate,
                                                                           @RequestParam(required = false) String toDate) {
        final AccountTransactionSearchRequest accountTransactionSearchRequest = new AccountTransactionSearchRequest();
        accountTransactionSearchRequest.setAccountNumber(accountNumber);
        accountTransactionSearchRequest.setStartDate(fromDate);
        accountTransactionSearchRequest.setEndDate(toDate);
        List<TransactionDTO> transactions = transactionService
                .getTransactionsWithInDates(accountTransactionSearchRequest);
        return ResponseEntity.ok(transactions);
    }

}
