package com.example.admin.rest;

import com.example.admin.entity.Customer;
import com.example.admin.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/customers/{customerId}")
    public Customer findById(@PathVariable("customerId") int customerId) {
        Optional<Customer> result = customerService.findById(customerId);
        if (!result.isPresent()) {
            throw new RuntimeException("Invalid customerId " + customerId);
        }
        return result.get();
    }

    @PutMapping("/customers/{customerId}")
    public Customer update(@PathVariable int customerId, @RequestBody Customer customer) {
        if (customer == null) {
            throw new RuntimeException("Customer can not be null");
        }
        if (!customerService.existsById(customerId)) {
            throw new RuntimeException("Customer id " + customerId + " not exist");
        }
        customer.setId(customerId);
        return customerService.saveOrUpdate(customer);
    }

    @PostMapping("/customers")
    public Customer create(@RequestBody Customer customer) {
        if (customer == null) {
            throw new RuntimeException("Customer can not be null");
        }
        customer.setId(0);
        return customerService.saveOrUpdate(customer);
    }

    @DeleteMapping("/customers/{customerId}")
    public void delete(@PathVariable int customerId) {
        if (!customerService.existsById(customerId)) {
            throw new RuntimeException("Customer id provided not exist");
        }
        customerService.deleteById(customerId);
    }

}
