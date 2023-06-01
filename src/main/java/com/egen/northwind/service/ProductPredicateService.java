package com.egen.northwind.service;

import com.egen.northwind.dto.CategorySearchDto;
import com.egen.northwind.dto.ProductSearchDto;
import com.egen.northwind.entity.QCategory;
import com.egen.northwind.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class ProductPredicateService {

    private final static QProduct product = QProduct.product;

    public static Predicate productSearch(ProductSearchDto productSearchDto) {

        String searchTerm = productSearchDto.getSearchTerm();
        BooleanBuilder builder = new BooleanBuilder();

        if (!CollectionUtils.isEmpty(productSearchDto.getIdList())) {
            builder.and(product.id.in((Number) productSearchDto.getIdList()));
        }
        if (!CollectionUtils.isEmpty(productSearchDto.getProductNameList())) {
            builder.and(product.productName.in(productSearchDto.getProductNameList()));
        }

        if (StringUtils.isNotBlank(searchTerm)) {
            builder.or(product.productName.containsIgnoreCase(searchTerm));
            //builder.or(product.supplier.containsIgnoreCase(searchTerm));
        }

        return builder;

    }
}
