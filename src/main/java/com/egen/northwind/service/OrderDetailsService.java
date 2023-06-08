package com.egen.northwind.service;

import com.egen.northwind.dto.OrderDetailDto;
import com.egen.northwind.dto.OrderDto;
import com.egen.northwind.entity.Order;
import com.egen.northwind.entity.OrderDetail;
import com.egen.northwind.repository.CustomerRepository;
import com.egen.northwind.repository.OrderDetailRepository;
import com.egen.northwind.repository.OrderRepository;
import com.egen.northwind.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailsService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    public OrderDetailDto createNewOrderDetails(OrderDetailDto orderDetailDto)  {
        var orderDetail = new OrderDetail();
        BeanUtils.copyProperties(orderDetailDto, orderDetail);
        orderRepository.findById(orderDetail.getOrderId())
                .ifPresent(order-> {
                    orderDetail.setOrder(order);
                });

        productRepository.findById(orderDetail.getProductId())
                .ifPresent(product-> {
                    orderDetail.setProduct(product);
                });

        orderDetailRepository.save(orderDetail);

        return orderDetailDto;
    }

    public List<OrderDetailDto> getOrderDetailList() {
        return orderDetailRepository.findAll()
                .stream()
                .map(itm -> {
                    var orderDetailDto = new OrderDetailDto();
                    BeanUtils.copyProperties(itm, orderDetailDto);
                    return orderDetailDto;
                })
                .sorted(Comparator.comparing(OrderDetailDto::getOrderId))
                .collect(Collectors.toList());
    }
}
