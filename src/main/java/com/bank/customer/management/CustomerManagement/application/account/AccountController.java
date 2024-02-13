package com.bank.customer.management.CustomerManagement.application.account;

import com.bank.customer.management.CustomerManagement.domain.account.Account;
import com.bank.customer.management.CustomerManagement.infrastructure.account.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Service
@RestController
@ResponseBody
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountRepository accountRepository;

    private static final List<String> ACCOUNT_TYPES = List.of("SAVINGS", "CURRENT", "FIXED_DEPOSIT");
    private static final List<String> ACCOUNT_STATUSES = List.of("ACTIVE", "INACTIVE");

    /**
     * Get account by id
     * @param id account id
     * @return account
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Account>> getAccountById(@PathVariable("id") Integer id) {
        logger.info("Getting account with id: {}", id);
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            logger.info("Account with id: {} is {}", id, account.get());
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            logger.info("Account with id: {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create account
     * @param account account to create
     * @return created account
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        verifyAccount(account);
        logger.info("Creating account: {}", account);
        Account createdAccount = accountRepository.save(account);
        logger.info("Created account: {}", createdAccount);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    /**
     * Update account
     * @param id account id
     * @param account account to update
     * @return updated account
     */
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") Integer id, @RequestBody Account account) {
        verifyAccount(account);
        logger.info("Updating account with id: {}", id);
        account.setId(id);
        Account updatedAccount = accountRepository.save(account);
        logger.info("Updated account: {}", updatedAccount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    /**
     * Delete account
     * @param id account id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable("id") Integer id) {
        logger.info("Deleting account with id: {}", id);
        accountRepository.deleteById(id);
    }

    private void verifyAccount(Account account) {
        if (account.getCustomerId() == null || account.getAccountNumber().isEmpty() || account.getAccountType().isEmpty() || account.getStatus().isEmpty()) {
            throw new IllegalArgumentException("Customer id, account number, account type and status cannot be empty");
        }
        if (!ACCOUNT_TYPES.contains(account.getAccountType())) {
            throw new IllegalArgumentException("Invalid account type");
        }
        if (!ACCOUNT_STATUSES.contains(account.getStatus())) {
            throw new IllegalArgumentException("Invalid account status");
        }
    }

}
