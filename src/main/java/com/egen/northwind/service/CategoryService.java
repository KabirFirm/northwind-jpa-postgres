package com.egen.northwind.service;

import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.entity.Category;
import com.egen.northwind.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDto createNewCategory(CategoryDto categoryDto) {
        var category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
//        category.setCategoryName(categoryDto.getCategoryName());
//        category.setPicture(categoryDto.getPicture());
//        category.setDescription(categoryDto.getDescription());
        categoryRepository.save(category);
        return getCategoryById(category.getId());
    }

    public CategoryDto getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(itm-> {
                    var categoryDto = new CategoryDto();
                    BeanUtils.copyProperties(itm, categoryDto);
                    return  categoryDto;
                }).orElseThrow(()-> new RuntimeException(String
                        .format("Category not found with id [%s]", id)));
    }

    public Category updateCategory(Integer id, CategoryDto categoryDto) {

        categoryRepository.findById(id)
                .ifPresent(itm-> {
                    BeanUtils.copyProperties(categoryDto, itm);
                    categoryRepository.save(itm);
                });

        return categoryRepository.findById(id).get();

    }

    public void delete(Integer id) {
        categoryRepository.findById(id).ifPresent(itm-> {
            categoryRepository.deleteById(itm.getId());
        });
    }

    public List<CategoryDto> getCategoryList() {
        return categoryRepository.findAll()
                .stream()
                .map(itm -> {
                    var categoryDto = new CategoryDto();
                    BeanUtils.copyProperties(itm, categoryDto);
                    return categoryDto;
                }).sorted(Comparator.comparing(CategoryDto::getCategoryName))
                .collect(Collectors.toList());
    }
}
