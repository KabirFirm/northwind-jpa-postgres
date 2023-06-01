package com.egen.northwind.service;

import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryTransformService {

    public static CategoryDto toCategoryDto(Category category) {

        return new CategoryDto()
                .setId(category.getId())
                .setCategoryName(category.getCategoryName())
                .setDescription(category.getDescription())
                .setPicture(category.getPicture());

    }

    public static List<CategoryDto> toCategoryDtoList(List<Category> categoryList) {
        return categoryList.parallelStream().map(CategoryTransformService::toCategoryDto).collect(Collectors.toList());
    }

}
