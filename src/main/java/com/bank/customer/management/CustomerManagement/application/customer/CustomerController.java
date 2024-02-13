package com.bank.customer.management.CustomerManagement.application.customer;

import com.bank.customer.management.CustomerManagement.domain.customer.Customer;
import com.bank.customer.management.CustomerManagement.infrastructure.customer.CustomerRepository;

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
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Get all customers
     * @return list of customers
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        logger.info("Getting all customers");
        List<Customer> customers = customerRepository.findAll();
        logger.info("List of customers: {}", customers.stream().map(Customer::toString).toList());
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * Get customer by id
     * @param id customer id
     * @return customer
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable("id") Integer id) {
        logger.info("Getting customer with id: {}", id);
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            logger.info("Customer with id: {} is {}", id, customer.get());
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            logger.info("Customer with id: {} not found", id);
            return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create customer
     * @param customer customer
     * @return created customer
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        if (customer.getName().isEmpty() || customer.getIdNumber().isEmpty()) {
            logger.warn("Name and id number cannot be empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // check for duplicates
        List<Customer> customers = customerRepository.findAll();
        var duplicates =
                customers.stream().filter(c ->
                        c.getIdNumber().equals(customer.getIdNumber())).findAny();
        if (duplicates.isPresent()) {
            logger.warn("Customer with id number: {} already exists", customer.getIdNumber());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
    }

    /**
     * Update customer
     * @param id customer id
     * @param customer customer
     * @return updated customer
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Integer id, @RequestBody Customer customer) {
        logger.info("Updating customer with id: {}", id);
        var existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isEmpty()) {
            logger.warn("Customer with id: {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (customer.getName().isEmpty() || customer.getIdNumber().isEmpty()) {
            logger.warn("First name and last name cannot be empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        customer.setId(id);
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
    }

    /**
     * Delete customer
     * @param id customer id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerRepository.deleteById(id);
        logger.info("Deleted customer with id: {}", id);
    }
}
