package com.egen.northwind.dto;

import com.egen.northwind.entity.Category;
import com.egen.northwind.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductDto {
    private Integer id;
    private String productName;
    private SupplierDto supplierDto;
    private Integer supplierId;
    private String companyName;
    private CategoryDto categoryDto;
    private Integer categoryId;
    private String categoryName;
    private String quantityPerUnit;
    private Double unitPrice;
    private Integer unitsInStock;
    private Integer unitsOnOrder;
    private Integer reorderLevel;
    private Integer discontinued;
}
