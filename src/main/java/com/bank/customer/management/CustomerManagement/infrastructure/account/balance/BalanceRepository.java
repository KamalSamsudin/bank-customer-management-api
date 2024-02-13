package com.bank.customer.management.CustomerManagement.infrastructure.account.balance;

import com.bank.customer.management.CustomerManagement.domain.account.balance.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
}
