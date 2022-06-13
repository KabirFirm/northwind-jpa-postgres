package com.egen.northwind.dto;

import com.egen.northwind.entity.Employee;
import com.egen.northwind.entity.Territory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTerritoryDto {
    private Integer id;
    private EmployeeDto employeeDto;
    private Integer employeeId;
    private TerritoryDto territoryDto;
    private Integer territoryId;
}
