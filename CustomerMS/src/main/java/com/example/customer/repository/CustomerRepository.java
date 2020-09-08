package com.example.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.customer.model.CustomerModel;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerModel, Integer>{

}
