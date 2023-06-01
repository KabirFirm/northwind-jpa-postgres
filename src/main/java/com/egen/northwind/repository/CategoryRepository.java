package com.egen.northwind.repository;

import com.egen.northwind.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

//    @Query("select category from Category category where category.categoryName =:categoryName")
    List<Category> findCategoriesByCategoryName(@PathVariable String categoryName);
}
