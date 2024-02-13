package com.bank.customer.management.CustomerManagement.application.account.balance;

import com.bank.customer.management.CustomerManagement.domain.account.balance.Balance;
import com.bank.customer.management.CustomerManagement.infrastructure.account.balance.BalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Optional;

@Service
@RestController
@ResponseBody
@RequestMapping("/account/balance")
public class BalanceController {

    private static final Logger logger = LoggerFactory.getLogger(BalanceController.class);

    private static final Double MINIMUM_BALANCE = 10.00;

    @Autowired
    private BalanceRepository balanceRepository;


    @GetMapping("/{account_number}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Balance> getBalance(@PathVariable("account_number") Integer accountNumber) {
        logger.info("Getting balance");
        var balance = getBalanceByAccountNumber(accountNumber);
        if (balance.isEmpty()) {
            logger.error("Account not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Balance: {}", balance);
        return new ResponseEntity<>(balance.get(), HttpStatus.OK);
    }

    @PutMapping("/{account_number}/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Balance> withdrawCash(@PathVariable("account_number") Integer accountNumber,
                                                @RequestParam("amount") String withdraw) {
        logger.info("Withdrawing cash " + withdraw);
        var balance = getBalanceByAccountNumber(accountNumber);
        if (balance.isEmpty()) {
            logger.error("Account not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var amount = convertAmountToDouble(withdraw);

        if (isBalanceSufficient(balance.get(), amount)) {
            balance.get().setBalance(roundTwoDecimalPlace(balance.get().getBalance() - amount));
            balance.get().setUpdatedAt(String.valueOf(Instant.now()));
        } else {
            logger.error("Insufficient balance");
            return new ResponseEntity<>(balance.get(), HttpStatus.BAD_REQUEST);
        }

        balanceRepository.save(balance.get());

        logger.info("Balance: {}", balance);
        return new ResponseEntity<>(balance.get(), HttpStatus.OK);
    }

    @PutMapping("/{account_number}/deposit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Balance> depositCash(@PathVariable("account_number") Integer accountNumber,
                                               @RequestParam("amount") String deposit) {
        logger.info("Depositing cash");
        var balance = getBalanceByAccountNumber(accountNumber);
        if (balance.isEmpty()) {
            logger.error("Account not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var amount = convertAmountToDouble(deposit);

        balance.get().setBalance(roundTwoDecimalPlace(balance.get().getBalance() + amount));
        balance.get().setUpdatedAt(String.valueOf(Instant.now()));

        balanceRepository.save(balance.get());

        logger.info("Balance: {}", balance);
        return new ResponseEntity<>(balance.get(), HttpStatus.OK);
    }

    private Optional<Balance> getBalanceByAccountNumber(Integer accountNumber) {
        return balanceRepository.findById(accountNumber);
    }

    private boolean isBalanceSufficient(Balance balance, Double amount) {
        Double finalBalance = balance.getBalance() - amount;
        return finalBalance.compareTo(MINIMUM_BALANCE) >= 0;
    }

    private double roundTwoDecimalPlace(Double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private Double convertAmountToDouble(String amount) {
        return Double.parseDouble(amount);
    }
}
