package com.egen.northwind.service;


import com.egen.northwind.dto.OrderDto;
import com.egen.northwind.entity.Order;
import com.egen.northwind.entity.QOrder;
import com.egen.northwind.entity.QOrderDetail;
import com.egen.northwind.repository.*;
import com.egen.northwind.util.EntityTransformUtil;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
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
    private final EntityManager entityManager;

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

     public List<Order> getOrderWithDetail() {

         final QOrder qOrder = QOrder.order;
         final QOrderDetail qOrderDetail = QOrderDetail.orderDetail;
         final JPAQuery<Order> query = new JPAQuery<>(entityManager);

         var orderJPAQuery = query.from(qOrder)
                 .leftJoin(qOrder.orderDetailList, qOrderDetail).fetchJoin()
                 //.where(predicate)
                 .limit(10)
                 .offset(0);

         List<Order> orderListNew = orderJPAQuery.fetch().parallelStream().map(order -> {
             var t = EntityTransformUtil.copyValueProp(order);
             t.setOrderDetailList(EntityTransformUtil.copyList(order.getOrderDetailList()));
             return t;
         }).collect(Collectors.toList());
         return orderListNew;

    }




}
