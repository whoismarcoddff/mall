package com.example.admin.service.impl;

import com.example.admin.entity.Customer;
import com.example.admin.repository.CustomerRepository;
import com.example.admin.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        return customers;
    }

    @Override
    public Optional<Customer> findById(int id) {
        Optional<Customer> result = customerRepository.findById(id);
        return result;
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(int id) {
        return customerRepository.existsById(id);
    }


}
