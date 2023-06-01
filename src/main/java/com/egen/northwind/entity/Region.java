package com.egen.northwind.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Integer id;

    @Column(name = "region_description", nullable = false)
    private String regionDescription;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<Territory> territoryList;
}
