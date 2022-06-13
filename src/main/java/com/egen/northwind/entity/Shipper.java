package com.egen.northwind.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "shippers")
public class Shipper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipper_id", nullable = false)
    private Integer id;

//    @OneToMany(mappedBy = "shipVia", fetch = FetchType.LAZY)
//    private List<Order> orderList;

    @Column(name = "company_name", length = 40, nullable = false)
    private String company_name;

    @Column(name = "phone", length = 24)
    private String phone;
}
