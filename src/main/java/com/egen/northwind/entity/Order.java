package com.egen.northwind.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @OneToMany(mappedBy = "orderId", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetailList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_orders_customer_id"))
    private Customer customer;

    @Column(name = "customer_id", insertable = false, updatable = false)
    private Integer customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "fk_orders_employee_id"))
    private Employee employee;

    @Column(name = "employee_id", insertable = false, updatable = false)
    private Integer employeeId;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "required_date")
    private LocalDate required_date;

    @Column(name = "shipped_date")
    private LocalDate shippedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_via", foreignKey = @ForeignKey(name = "fk_orders_ship_via"))
    private Shipper shipper;

    @Column(name = "ship_via", insertable = false, updatable = false)
    private Integer shipVia;

    @Column(name = "freight")
    private Double freight;

    @Column(name = "ship_name", length = 40)
    private String shipName;

    @Column(name = "ship_address", length = 60)
    private String shipAddress;

    @Column(name = "ship_city", length = 15)
    private String shipCity;

    @Column(name = "ship_region", length = 15)
    private String shipRegion;

    @Column(name = "ship_postal_code", length = 10)
    private String shipPostalCode;

    @Column(name = "ship_country", length = 15)
    private String shipCountry;
}
