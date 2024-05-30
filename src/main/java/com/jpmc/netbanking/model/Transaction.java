package com.jpmc.netbanking.model;

import com.jpmc.netbanking.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Date transaction_date;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "target_account_id")
    private Account targetAccount;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                ", transaction_date=" + transaction_date +
                ", sourceAccount=" + sourceAccount +
                ", targetAccount=" + targetAccount +
                '}';
    }
}
