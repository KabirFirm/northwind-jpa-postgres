package com.egen.northwind.controller;

import com.egen.northwind.api_response.ApiResponseEntityFactory;
import com.egen.northwind.dto.EmployeeTerritoryDto;
import com.egen.northwind.dto.OrderDto;
import com.egen.northwind.service.EmployeeTerritoryService;
import com.egen.northwind.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*") // all
@RequestMapping(path = "api/v1/orders", produces = {"application/json"})
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final ApiResponseEntityFactory responseFactory;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> save(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.createNewOrder(orderDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getOrderList() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(orderService.getOrderList());
        } catch (Exception e) {
            log.error("Error in OrderController:getOrderList {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error!!!");
        }
    }
}
