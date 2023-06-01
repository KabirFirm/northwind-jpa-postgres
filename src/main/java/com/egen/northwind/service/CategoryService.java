package com.egen.northwind.service;

import com.egen.northwind.advice.BadRequestAlertException;
import com.egen.northwind.advice.DuplicateException;
import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.dto.CategorySearchDto;
import com.egen.northwind.entity.Category;
import com.egen.northwind.entity.QCategory;
import com.egen.northwind.repository.CategoryRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.egen.northwind.service.CategoryPredicateService.categorySearch;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityManager entityManager;

    public CategoryDto createNewCategory(CategoryDto categoryDto)  {
        var category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
//        category.setCategoryName(categoryDto.getCategoryName());
//        category.setPicture(categoryDto.getPicture());
//        category.setDescription(categoryDto.getDescription());
        if (categoryRepository.findCategoriesByCategoryName(categoryDto.getCategoryName()).isEmpty()) {
            BeanUtils.copyProperties(categoryRepository.save(category), categoryDto);
            return categoryDto;
        } else throw new DuplicateException();
    }

    public CategoryDto getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(itm -> {
                    var categoryDto = new CategoryDto();
                    BeanUtils.copyProperties(itm, categoryDto);
                    return categoryDto;
                }).orElseThrow(() -> new RuntimeException(String
                        .format("Category not found with id [%s]", id)));
    }

    public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {

        categoryRepository.findById(id)
                .ifPresent(itm -> {
                    BeanUtils.copyProperties(categoryDto, itm);
                    categoryRepository.save(itm);

                });
        return categoryDto;

        //return categoryRepository.findById(id).get();

    }

    public void delete(Integer id) {
        categoryRepository.findById(id).ifPresent(itm -> {
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

    public Page<CategoryDto> searchCategory(CategorySearchDto categorySearchDto) {

        final QCategory category = QCategory.category;
        final JPAQuery<Category> query = new JPAQuery<>(entityManager);

        Predicate predicate = categorySearch(categorySearchDto);
        Pageable pageable = PageRequest.of(categorySearchDto.getPage(), categorySearchDto.getSize(),
                Sort.by("createdDate").descending());

        var categoryList = query.from(category)
                .where(predicate)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(category.id.desc())
                .fetch();
        return new PageImpl<>(CategoryTransformService.toCategoryDtoList(categoryList),
                pageable, query.fetchCount());
    }

    public Page<CategoryDto> getAllCategoryPage(CategorySearchDto categorySearchDto) {
        Predicate predicate = categorySearch(categorySearchDto);

        final QCategory category = QCategory.category;
        final JPAQuery<Category> query = new JPAQuery<>(entityManager);
        var categoryList = query.from(category)
                .where(predicate)
                .orderBy(category.id.desc())
                .fetch();

        return new PageImpl<>(CategoryTransformService.toCategoryDtoList(categoryList));
    }
}
