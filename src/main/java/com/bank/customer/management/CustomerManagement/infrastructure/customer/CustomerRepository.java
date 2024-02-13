package com.bank.customer.management.CustomerManagement.infrastructure.customer;

import com.bank.customer.management.CustomerManagement.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}

