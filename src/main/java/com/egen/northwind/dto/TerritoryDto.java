package com.egen.northwind.dto;

import com.egen.northwind.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerritoryDto {
    private Integer id;
    private String territoryDescription;
    private RegionDto regionDto;
    private Integer regionId;
}
