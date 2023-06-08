package com.egen.northwind.service;


import com.egen.northwind.dto.OrderDto;
import com.egen.northwind.entity.Order;
import com.egen.northwind.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShipperRepository shipperRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    public OrderDto createNewOrder(OrderDto orderDto)  {
        var order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        employeeRepository.findById(order.getEmployeeId())
                .ifPresent(employee-> {
                    order.setEmployee(employee);
                });

        customerRepository.findById(order.getCustomerId())
                .ifPresent(customer-> {
                    order.setCustomer(customer);
                });

        shipperRepository.findById(order.getShipVia())
                .ifPresent(shipper-> {
                    order.setShipper(shipper);
                });
        orderRepository.save(order);

        return orderDto;
    }

    public List<OrderDto> getOrderList() {
        return orderRepository.findAll()
                .stream()
                .map(itm -> {
                    var orderDto = new OrderDto();
                    BeanUtils.copyProperties(itm, orderDto);
                    return orderDto;
                })
                .sorted(Comparator.comparing(OrderDto::getEmployeeId))
                .collect(Collectors.toList());
    }
}
