package com.egen.northwind.entity;

import lombok.Data;
import javax.persistence.*;


@Entity
@Data
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    @Column(name = "category_name", length = 15, nullable = false)
    private String categoryName;

    @Column(name = "description")
    private String description;

    @Column(name = "picture")
    private String picture;

//    @OneToMany(mappedBy = "categoryId",fetch = FetchType.LAZY)
//    private List<Product> productList;
}
