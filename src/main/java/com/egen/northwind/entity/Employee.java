package com.egen.northwind.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Integer id;

    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @Column(name = "first_name", length = 10, nullable = false)
    private String firstName;

    @Column(name = "title", length = 30)
    private String title;

    @Column(name = "title_of_courtesy", length = 25)
    private String titleOfCourtesy;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "address", length = 60)
    private String address;

    @Column(name = "city", length = 15)
    private String city;

    @Column(name = "region", length = 15)
    private String region;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "country", length = 15)
    private String country;

    @Column(name = "home_phone", length = 24)
    private String homePhone;

    @Column(name = "extension", length = 4)
    private String extension;

    @Column(name = "photo")
    private String photo;

    @Column(name = "notes")
    private String notes;

    @Column(name = "reports_to")
    private Integer reportsTo;

    @Column(name = "photo_path", length = 255)
    private String photoPath;

    /*@OneToMany(mappedBy = "employeeId", fetch = FetchType.LAZY)
    private List<Order> orderList;

    @OneToMany(mappedBy = "employeeId", fetch = FetchType.LAZY)
    private List<EmployeeTerritory> employeeTerritoryList;*/


}
