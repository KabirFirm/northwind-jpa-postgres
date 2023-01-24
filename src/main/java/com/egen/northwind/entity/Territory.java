package com.egen.northwind.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "territories")
public class Territory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "territory_id")
    private Integer id;

    @Column(name = "territory_description", nullable = false)
    private String territoryDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", foreignKey = @ForeignKey(name = "fk_territories_region_id"))
    private Region region;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Integer regionId;

//    @OneToMany(mappedBy = "territory", fetch = FetchType.LAZY)
//    private List<EmployeeTerritory> employeeTerritoryList;
}
