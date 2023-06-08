package com.egen.northwind.controller;

import com.egen.northwind.dto.OrderDetailDto;
import com.egen.northwind.dto.OrderDto;
import com.egen.northwind.service.OrderDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*") // all
@RequestMapping(path = "api/v1/order_details", produces = {"application/json"})
@Slf4j
@RequiredArgsConstructor
public class OrderDetailController {

    private  final OrderDetailsService orderDetailsService;

    @PostMapping
    public ResponseEntity<OrderDetailDto> save(@RequestBody OrderDetailDto orderDetailDto) {
        return new ResponseEntity<>(orderDetailsService.createNewOrderDetails(orderDetailDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getOrderDetailList() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(orderDetailsService.getOrderDetailList());
        } catch (Exception e) {
            log.error("Error in OrderDetailController:getOrderDetailList {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error!!!");
        }
    }
}
