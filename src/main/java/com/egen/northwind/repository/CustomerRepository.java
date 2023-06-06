package com.egen.northwind.repository;

import com.egen.northwind.entity.Category;
import com.egen.northwind.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findCustomersByCompanyName(@PathVariable String companyName);
}
