package com.egen.northwind.service;

import com.egen.northwind.dto.CategorySearchDto;

import com.egen.northwind.entity.QCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;


public class CategoryPredicateService {

    private final static QCategory category = QCategory.category;

    public static Predicate categorySearch(CategorySearchDto categorySearchDto) {

        String searchTerm = categorySearchDto.getSearchTerm();
        BooleanBuilder builder = new BooleanBuilder();

        if (!CollectionUtils.isEmpty(categorySearchDto.getIdList())) {
            builder.and(category.id.in((Number) categorySearchDto.getIdList()));
        }
        if (!CollectionUtils.isEmpty(categorySearchDto.getCategoryNameList())) {
            builder.and(category.categoryName.in(categorySearchDto.getCategoryNameList()));
        }

        if (StringUtils.isNotBlank(searchTerm)) {
            builder.or(category.categoryName.containsIgnoreCase(searchTerm));
            builder.or(category.description.containsIgnoreCase(searchTerm));
        }

        return builder;

    }

}
