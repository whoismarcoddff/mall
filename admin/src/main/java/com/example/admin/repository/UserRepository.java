package com.example.admin.repository;

import com.example.admin.entity.Customer;
import com.example.admin.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
