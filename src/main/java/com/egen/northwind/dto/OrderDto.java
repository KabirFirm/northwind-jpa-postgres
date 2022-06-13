package com.egen.northwind.dto;

import com.egen.northwind.entity.Customer;
import com.egen.northwind.entity.Employee;
import com.egen.northwind.entity.Shipper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Integer id;
    private CustomerDto customerDto;
    private Integer customerId;
    private EmployeeDto employeeDto;
    private Integer employeeId;
    private LocalDate orderDate;
    private LocalDate required_date;
    private LocalDate shippedDate;
    private ShipperDto shipperDto;
    private Integer shipVia;
    private Double freight;
    private String shipName;
    private String shipAddress;
    private String shipCity;
    private String shipRegion;
    private String shipPostalCode;
    private String shipCountry;
}
