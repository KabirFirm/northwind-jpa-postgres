package com.egen.northwind.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipperDto {
    private Integer id;
    private String company_name;
    private String phone;
}
