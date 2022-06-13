package com.egen.northwind.dto;

import com.egen.northwind.entity.Category;
import com.egen.northwind.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer id;
    private String productName;
    private SupplierDto supplierDto;
    private Integer supplierId;
    private CategoryDto categoryDto;
    private Integer categoryId;
    private String quantityPerUnit;
    private Double unitPrice;
    private Integer unitsInStock;
    private Integer unitsOnOrder;
    private Integer reorderLevel;
    private Integer discontinued;
}
