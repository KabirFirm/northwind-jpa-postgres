package com.egen.northwind.repository;

import com.egen.northwind.entity.Category;
import com.egen.northwind.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Category> findProductsByProductName(@PathVariable String productName);
}
