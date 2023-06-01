package com.egen.northwind.service;

import com.egen.northwind.dto.CategorySearchDto;
import com.egen.northwind.dto.SupplierSearchDto;
import com.egen.northwind.entity.QCategory;
import com.egen.northwind.entity.QSupplier;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class SupplierPredicateService {

    private final static QSupplier supplier = QSupplier.supplier;

    public static Predicate supplierSearch(SupplierSearchDto supplierSearchDto) {

        String searchTerm = supplierSearchDto.getSearchTerm();
        BooleanBuilder builder = new BooleanBuilder();

        if (!CollectionUtils.isEmpty(supplierSearchDto.getIdList())) {
            builder.and(supplier.id.in((Number) supplierSearchDto.getIdList()));
        }
        if (!CollectionUtils.isEmpty(supplierSearchDto.getCompanyNameList())) {
            builder.and(supplier.companyName.in(supplierSearchDto.getCompanyNameList()));
        }

        if (StringUtils.isNotBlank(searchTerm)) {
            builder.or(supplier.companyName.containsIgnoreCase(searchTerm));
            builder.or(supplier.contactName.containsIgnoreCase(searchTerm));
        }

        return builder;

    }
}
