package com.egen.northwind.service;

import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.dto.ProductDto;
import com.egen.northwind.entity.Category;
import com.egen.northwind.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductTransformService {

    public static ProductDto toProductDto(Product product) {

        return new ProductDto()
                .setId(product.getId())
                .setProductName(product.getProductName())
                .setSupplierId(product.getSupplierId())
                .setCompanyName(product.getCompanyName())
                .setCategoryId(product.getCategoryId())
                .setCategoryName(product.getCategoryName())
                .setQuantityPerUnit(product.getQuantityPerUnit())
                .setUnitPrice(product.getUnitPrice())
                .setUnitsInStock(product.getUnitsInStock())
                .setUnitsOnOrder(product.getUnitsOnOrder())
                .setReorderLevel(product.getReorderLevel())
                .setDiscontinued(product.getDiscontinued());
    }

    public static List<ProductDto> toProductDtoList(List<Product> productList) {
        return productList.parallelStream().map(ProductTransformService::toProductDto).collect(Collectors.toList());
    }
}
