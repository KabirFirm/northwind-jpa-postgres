package com.egen.northwind.repository;

import com.egen.northwind.entity.EmployeeTerritory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeTerritoryRepository extends JpaRepository<EmployeeTerritory, Integer> {
}
