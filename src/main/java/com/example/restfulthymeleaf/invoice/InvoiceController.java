package com.example.restfulthymeleaf.invoice;

import com.example.restfulthymeleaf.customer.Customer;
import com.example.restfulthymeleaf.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class InvoiceController {
    @Autowired private InvoiceRepository invoiceRepo;
    @Autowired private CustomerRepository customerRepo;

    @PostMapping("/post/customers/{id}/invoice")
    public ResponseEntity<Invoice> postInvoice(@PathVariable(value = "id") long id, @RequestBody Invoice invoiceRequest) {
        Optional<Customer> optional = customerRepo.findById(id);
        if (optional.isPresent()) {
            invoiceRequest.setCustomer(optional.get());
            invoiceRequest.setDate(LocalDate.now().toString());
            invoiceRepo.save(invoiceRequest);
            return new ResponseEntity<>(invoiceRequest, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/get/invoices")
    public Iterable<Invoice> allInvoices() {
        return invoiceRepo.findAll();
    }

    @GetMapping("/get/invoices/{id}")
    public ResponseEntity<Invoice> findInvoiceById(@PathVariable(value = "id") long id) {
        Optional<Invoice> invoiceOptional = invoiceRepo.findById(id);
        if (invoiceOptional.isPresent()) {
            return new ResponseEntity<>(invoiceOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/delete/invoices/{id}")
    public ResponseEntity<HttpStatus> deleteInvoiceById(@PathVariable("id") long id) {
        invoiceRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
