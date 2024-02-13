package com.bank.customer.management.CustomerManagement.domain.account.balance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Balance")
@Getter
@Setter
public class Balance {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String account_number;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "updated_at", nullable = false)
    private String updatedAt;

    public Balance() {
    }
}
