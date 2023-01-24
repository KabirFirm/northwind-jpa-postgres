package com.egen.northwind.controller;

import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.entity.Category;
import com.egen.northwind.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostUpdate;

@RestController
@RequestMapping(path = "api/v1/categories", produces = {"application/json"})
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
//    private final ApiResponseEntityFactory responseFactory;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody CategoryDto categoryDto) {
        try {
            categoryService.createNewCategory(categoryDto);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Cannot save data !!!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody CategoryDto categoryDto) {
        try {
            categoryService.updateCategory(id, categoryDto);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Cannot update data !!!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            log.error("Error in CategoryController:delete {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error!!!");
        }
    }

    @GetMapping
    public ResponseEntity<?> getCategoryList() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(categoryService.getCategoryList());
        } catch (Exception e) {
            log.error("Error in CategoryController:getCategoryList {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error!!!");
        }
    }

}
