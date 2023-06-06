package com.egen.northwind.service;

import com.egen.northwind.advice.DuplicateException;
import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.dto.CustomerDto;
import com.egen.northwind.entity.Category;
import com.egen.northwind.entity.Customer;
import com.egen.northwind.repository.CategoryRepository;
import com.egen.northwind.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final EntityManager entityManager;

    public CustomerDto createNewCustomer(CustomerDto customerDto)  {
        var customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        if (customerRepository.findCustomersByCompanyName(customerDto.getCompanyName()).isEmpty()) {
            BeanUtils.copyProperties(customerRepository.save(customer), customerDto);
            return customerDto;
        } else throw new DuplicateException();
    }

    public List<CustomerDto> getCustomerList() {
        return customerRepository.findAll()
                .stream()
                .map(itm -> {
                    var customerDto = new CustomerDto();
                    BeanUtils.copyProperties(itm, customerDto);
                    return customerDto;
                }).sorted(Comparator.comparing(CustomerDto::getCompanyName))
                .collect(Collectors.toList());
    }
}
