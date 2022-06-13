package com.egen.northwind.dto;

import com.egen.northwind.entity.Order;
import com.egen.northwind.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {

    private Integer id;
    private OrderDto orderDto;
    private Integer orderId;
    private ProductDto productDto;
    private Integer productId;
    private Double unitPrice;
    private Integer quantity;
    private Double discount;
}
