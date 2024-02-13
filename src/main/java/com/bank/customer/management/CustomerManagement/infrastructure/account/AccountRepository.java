package com.bank.customer.management.CustomerManagement.infrastructure.account;

import com.bank.customer.management.CustomerManagement.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
}
