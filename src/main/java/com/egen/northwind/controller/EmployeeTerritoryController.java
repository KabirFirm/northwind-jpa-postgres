package com.egen.northwind.controller;

import com.egen.northwind.api_response.ApiResponseEntityFactory;
import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.dto.EmployeeTerritoryDto;
import com.egen.northwind.service.EmployeeTerritoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*") // all
@RequestMapping(path = "api/v1/employee_territories", produces = {"application/json"})
@Slf4j
@RequiredArgsConstructor
public class EmployeeTerritoryController {

    private final ApiResponseEntityFactory responseFactory;
    private final EmployeeTerritoryService employeeTerritoryService;

    @PostMapping
    public ResponseEntity<EmployeeTerritoryDto> save(@RequestBody EmployeeTerritoryDto employeeTerritoryDto) {
        return new ResponseEntity<>(employeeTerritoryService.createNewEmployeeTerritory(employeeTerritoryDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getEmployeeTerritoryList() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeTerritoryService.getEmployeeTerritoryList());
        } catch (Exception e) {
            log.error("Error in EmployeeTerritoryController:getEmployeeTerritoryList {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error!!!");
        }
    }

    @GetMapping("/with-territory")
    public ResponseEntity<?> getEmployeeTerritoryWithTerritory() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeTerritoryService.getEmployeeTerritoryWithTerritory());
        }catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("There is no data");
        }
    }
}
