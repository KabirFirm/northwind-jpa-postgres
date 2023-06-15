package com.egen.northwind.service;

import com.egen.northwind.dto.EmployeeDto;
import com.egen.northwind.dto.EmployeeTerritoryDto;
import com.egen.northwind.dto.TerritoryDto;
import com.egen.northwind.entity.EmployeeTerritory;
import com.egen.northwind.entity.QEmployeeTerritory;
import com.egen.northwind.entity.QTerritory;
import com.egen.northwind.entity.Territory;
import com.egen.northwind.repository.EmployeeRepository;
import com.egen.northwind.repository.EmployeeTerritoryRepository;
import com.egen.northwind.repository.RegionRepository;
import com.egen.northwind.repository.TerritoryRepository;
import com.egen.northwind.util.EntityTransformUtil;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeTerritoryService {

    private final EmployeeTerritoryRepository employeeTerritoryRepository;
    private final TerritoryRepository territoryRepository;
    private final EmployeeRepository employeeRepository;
    private final EntityManager entityManager;

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

    public List<EmployeeTerritory> getEmployeeTerritoryList() {
        List<EmployeeTerritory> employeeTerritoryList =  employeeTerritoryRepository.findAll()
                .stream()
                /*.map(itm -> {
                    var employeeTerritoryDto = new EmployeeTerritoryDto();
                    BeanUtils.copyProperties(itm, employeeTerritoryDto);
                    return employeeTerritoryDto;
                })*/
                .sorted(Comparator.comparing(EmployeeTerritory::getEmployeeId))
                .collect(Collectors.toList());

                //return employeeTerritoryList;

        List<EmployeeTerritory> employeeTerritoryListNew = employeeTerritoryList.parallelStream().map(employeeTerritory -> {
            var t = EntityTransformUtil.copyValueProp(employeeTerritory);

            //t.setTerritory(EntityTransformUtil.copy(employeeTerritory.getTerritory()));
            return t;
        }).collect(Collectors.toList());
        return employeeTerritoryListNew;
    }

    public List<EmployeeTerritory> getEmployeeTerritoryWithTerritory() {
        final QTerritory qTerritory = QTerritory.territory;
        final QEmployeeTerritory qEmployeeTerritory = QEmployeeTerritory.employeeTerritory;
        final JPAQuery<EmployeeTerritory> query = new JPAQuery<>(entityManager);
        var employeeTerritoryJPAQuery = query.from(qEmployeeTerritory)
                .leftJoin(qEmployeeTerritory.territory, qTerritory).fetchJoin()
                .orderBy(qEmployeeTerritory.id.asc());
        //return t;

        List<EmployeeTerritory> employeeTerritoryListNew = employeeTerritoryJPAQuery.fetch().parallelStream().map(employeeTerritory -> {
            var t = EntityTransformUtil.copyValueProp(employeeTerritory);
            t.setTerritory(EntityTransformUtil.copy(employeeTerritory.getTerritory()));
            return t;
        }).collect(Collectors.toList());

        return employeeTerritoryListNew;
    }
}
