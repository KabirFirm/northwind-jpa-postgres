package com.egen.northwind.service;

import com.egen.northwind.dto.EmployeeDto;
import com.egen.northwind.dto.EmployeeTerritoryDto;
import com.egen.northwind.dto.TerritoryDto;
import com.egen.northwind.entity.EmployeeTerritory;
import com.egen.northwind.entity.Territory;
import com.egen.northwind.repository.EmployeeRepository;
import com.egen.northwind.repository.EmployeeTerritoryRepository;
import com.egen.northwind.repository.RegionRepository;
import com.egen.northwind.repository.TerritoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeTerritoryService {

    private final EmployeeTerritoryRepository employeeTerritoryRepository;
    private final TerritoryRepository territoryRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeTerritoryDto createNewEmployeeTerritory(EmployeeTerritoryDto employeeTerritoryDto) {
        var employeeTerritory = new EmployeeTerritory();
        BeanUtils.copyProperties(employeeTerritoryDto, employeeTerritory);
        employeeRepository.findById(employeeTerritory.getEmployeeId())
                .ifPresent(employee-> {
                    employeeTerritory.setEmployee(employee);
                });
        territoryRepository.findById(employeeTerritory.getTerritoryId())
                .ifPresent(territory-> {
                    employeeTerritory.setTerritory(territory);
                });
        employeeTerritoryRepository.save(employeeTerritory);

        return employeeTerritoryDto;
    }

    public List<EmployeeTerritoryDto> getEmployeeTerritoryList() {
        return employeeTerritoryRepository.findAll()
                .stream()
                .map(itm -> {
                    var employeeTerritoryDto = new EmployeeTerritoryDto();
                    BeanUtils.copyProperties(itm, employeeTerritoryDto);
                    return employeeTerritoryDto;
                })
                .sorted(Comparator.comparing(EmployeeTerritoryDto::getEmployeeId))
                .collect(Collectors.toList());
    }
}
