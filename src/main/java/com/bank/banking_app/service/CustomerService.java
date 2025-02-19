package com.bank.banking_app.service;

import com.bank.banking_app.entity.Customer;
import com.bank.banking_app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public Customer createCustomer(String username, String password, double balance) {
        Customer customer = new Customer(username, password, balance);
        return customerRepository.save(customer);
    }

    public void performTransaction(String username, double amount) {
        Customer customer = customerRepository.findByUsername(username).orElseThrow();
        double currentBalance = customer.getBalance();
        customer.setBalance(currentBalance + amount);  // Add or subtract based on the transaction type
        customerRepository.save(customer);
    }
}
