package com.example.restfulthymeleaf.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired private CustomerRepository customerRepo;

    @GetMapping("/get/customers/all")
    public Iterable<Customer> getCustomers() {
        return customerRepo.findAll();
    }

    @GetMapping("/get/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable(value = "id") long id) {
        Optional<Customer> response = customerRepo.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
