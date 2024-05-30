package com.jpmc.netbanking.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Basic(optional = false)
    private String accountNumber;

    private double balance;

    private String account_type = "Saving";

    private String branch = "BANGALURU";

    private String IFSC_code = "BNG001";

    private String accountstatus;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @Basic(optional = false)
    private Users user;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", account_type='" + account_type + '\'' +
                ", branch='" + branch + '\'' +
                ", IFSC_code='" + IFSC_code + '\'' +
                ", accountstatus='" + accountstatus + '\'' +
                ", user=" + user +
                '}';
    }
}
