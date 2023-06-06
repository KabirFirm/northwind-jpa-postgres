package com.egen.northwind.controller;

import com.egen.northwind.api_response.ApiResponseEntityFactory;
import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.dto.CustomerDto;
import com.egen.northwind.service.CategoryService;
import com.egen.northwind.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*") // all
@RequestMapping(path = "api/v1/customers", produces = {"application/json"})
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

    private final ApiResponseEntityFactory responseFactory;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> save(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.createNewCustomer(customerDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCustomerList() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(customerService.getCustomerList());
        } catch (Exception e) {
            log.error("Error in CustomerController:getCustomerList {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error!!!");
        }
    }
}
