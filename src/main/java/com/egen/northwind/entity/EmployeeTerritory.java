package com.egen.northwind.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "employee_territories")
public class EmployeeTerritory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "fk_employee_territories_employee_id"))
    private Employee employee;

    @Column(name = "employee_id", insertable = false, updatable = false)
    private Integer employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "territory_id", foreignKey = @ForeignKey(name = "fk_employee_territories_territory_id"))
    private Territory territory;

    @Column(name = "territory_id", insertable = false, updatable = false)
    private Integer territoryId;
}
