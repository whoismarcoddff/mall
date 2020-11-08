package com.example.admin.service;

import com.example.admin.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;

public interface CustomerService {
    List<Customer> getAll();

    Optional<Customer> findById(int id);

    Customer saveOrUpdate(Customer customer);

    void deleteById(int id);

    Boolean existsById(int id);
}
