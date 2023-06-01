package com.egen.northwind.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ProductSearchDto {

    private List<String> idList;
    private List<String> productNameList;

    private Boolean enabled;

    private String searchTerm;

    private Integer page;
    private Integer size = 10;
    //private List<String> departmentIdList;
}
