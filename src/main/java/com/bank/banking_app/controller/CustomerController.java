package com.bank.banking_app.controller;

import com.bank.banking_app.entity.Customer;
import com.bank.banking_app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Optional<Customer> customer = customerService.findByUsername(username);
        if (customer.isPresent() && customer.get().getPassword().equals(password)) {
            return ResponseEntity.ok("Login Successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> checkBalance(@RequestParam String username) {
        Optional<Customer> customer = customerService.findByUsername(username);
        return customer.map(c -> ResponseEntity.ok(c.getBalance()))
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/transaction")
    public ResponseEntity<String> performTransaction(@RequestParam String username, @RequestParam double amount) {
        try {
            customerService.performTransaction(username, amount);
            return ResponseEntity.ok("Transaction successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction failed");
        }
    }
}
