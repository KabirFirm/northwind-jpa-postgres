package com.egen.northwind.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer id;

    @Column(name = "product_name", length = 40, nullable = false)
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "fk_products_supplier_id"))
    private Supplier supplier;

    @Column(name = "supplier_id", insertable = false, updatable = false)
    private Integer supplierId;

    @Column(name = "company_name", length = 40, nullable = true)
    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_products_category_id"))
    private Category category;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Integer categoryId;

    @Column(name = "category_name", length = 15, nullable = true)
    private String categoryName;

    @Column(name = "quantity_per_unit")
    private String quantityPerUnit;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "units_in_stock")
    private Integer unitsInStock;

    @Column(name = "units_on_order")
    private Integer unitsOnOrder;

    @Column(name = "reorder_level")
    private Integer reorderLevel;

    @Column(name = "discontinued", nullable = true)
    private Integer discontinued;


}
