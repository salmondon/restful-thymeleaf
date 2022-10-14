package com.example.restfulthymeleaf.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class CustomerModelView {
    @Autowired private CustomerRepository customerRepo;

    @GetMapping("/")
    public String Index(Model model) {
        Id id = new Id();
        model.addAttribute("id", id);
        model.addAttribute("customers", customerRepo.findAll());
        return "index";
    }

    @GetMapping("/newCustomerForm")
    public String addNewCustomer(Model model) {
        Customer customerForm = new Customer();
        model.addAttribute("customer", customerForm);
        return "addcustomer";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer request) {
        customerRepo.save(request);
        return "redirect:/";
    }

    @GetMapping("/findById")
    public String getById(Model model, @ModelAttribute(value = "customerId")Id id) {
        Optional<Customer> customer = customerRepo.findById(Long.parseLong(id.getId()));
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customer";
        } else {
            return "/redirect:/";
        }
    }
}
