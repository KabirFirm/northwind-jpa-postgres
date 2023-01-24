package com.egen.northwind.service;

import com.egen.northwind.dto.EmployeeDto;
import com.egen.northwind.entity.Employee;
import com.egen.northwind.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        var employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        employee.setBirthDate(getDate(employeeDto.getBirthDate()));
        employee.setHireDate(getDate(employeeDto.getHireDate()));
        employeeRepository.save(employee);
        return getEmployeeById(employee.getId());
    }

    public EmployeeDto getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .map(itm -> {
                    var employeeDto = new EmployeeDto();
                    BeanUtils.copyProperties(itm, employeeDto);
                    return employeeDto;
                }).orElseThrow(() -> new RuntimeException(String.format("Category not found with id [%s]", id)));
    }

    public EmployeeDto updateEmployee(Integer id, EmployeeDto employeeDto) {
        employeeRepository.findById(id)
                .ifPresent(itm -> {
                    BeanUtils.copyProperties(employeeDto, itm);
                    itm.setBirthDate(getDate(employeeDto.getBirthDate()));
                    itm.setHireDate(getDate(employeeDto.getHireDate()));
                    employeeRepository.save(itm);
                });
        return getEmployeeById(id);
    }

    public void delete(Integer id) {
        employeeRepository.findById(id).ifPresent(
                itm -> {
                    employeeRepository.deleteById(itm.getId());
                }
        );
    }

    public List<EmployeeDto> getEmployeeList() {
        return employeeRepository.findAll()
                .stream()
                .map(itm -> {
                    var employeeDto = new EmployeeDto();
                    BeanUtils.copyProperties(itm, employeeDto);
                    return employeeDto;
                })
                .sorted(Comparator.comparing(EmployeeDto::getFirstName))
                .collect(Collectors.toList());
    }

    public static LocalDate getDate(String date) {
        if (Objects.isNull(date)) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException pe) {
            return null;
        }
    }
}
