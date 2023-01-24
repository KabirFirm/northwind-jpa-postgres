package com.egen.northwind.controller;

import com.egen.northwind.dto.EmployeeDto;
import com.egen.northwind.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/employees", produces = {"application/json"})
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody EmployeeDto employeeDto) {
        try {
            employeeService.createNewEmployee(employeeDto);
            return ResponseEntity.ok("success");
        }catch (Exception e){
            log.error("{}",e.getMessage());
            throw new RuntimeException("Can not save data");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody EmployeeDto employeeDto) {
        try {
            employeeService.updateEmployee(id, employeeDto);
            return ResponseEntity.ok("success");
        }catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Can not update data....");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        try {
            employeeService.delete(id);
            return ResponseEntity.ok("success");

        }catch (Exception e){
            log.error("Error in EmployeeController:delete {}", e.getMessage());
            throw new RuntimeException("Error on deleting");
        }
    }

    @GetMapping
    public ResponseEntity<?> getEmployeeList(){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.getEmployeeList());
        }catch (Exception e){
            log.error("Error on geting Employee list Controller", e.getMessage());
            throw new RuntimeException("Getting data failed");
        }
    }

}
