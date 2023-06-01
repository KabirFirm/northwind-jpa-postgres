package com.egen.northwind.controller;

import com.egen.northwind.advice.DuplicateException;
import com.egen.northwind.api_response.ApiResponse;
import com.egen.northwind.api_response.ApiResponseEntityFactory;
import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.dto.CategorySearchDto;
import com.egen.northwind.entity.Category;
import com.egen.northwind.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostUpdate;
import java.util.Objects;

@RestController
@CrossOrigin("*") // all
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/v1/categories", produces = {"application/json"})
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final ApiResponseEntityFactory responseFactory;
    private final CategoryService categoryService;
//    private final ApiResponseEntityFactory responseFactory;

    @PostMapping
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.createNewCategory(categoryDto), HttpStatus.OK);
//        categoryService.createNewCategory(categoryDto);
//        return ResponseEntity.ok("success");
    }

    @PutMapping()
    public ResponseEntity<CategoryDto> update( @RequestParam("id") Integer id,
                                    @RequestBody CategoryDto categoryDto) {
        try {
            return new ResponseEntity<>(categoryService.updateCategory(id, categoryDto), HttpStatus.OK);
//            categoryService.updateCategory(id, categoryDto);
//            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
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

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<CategoryDto>>> searchCategory(
            @RequestBody CategorySearchDto categorySearchDto) {
        try {
            if (Objects.nonNull(categorySearchDto.getPage())) {
                return responseFactory.getResponse(categoryService.searchCategory(categorySearchDto));
            } else {
                return responseFactory.getResponse(categoryService.getAllCategoryPage(categorySearchDto));
            }
        } catch (Exception e) {
            log.error("Error in searchCategory {}", e.getMessage());
            e.printStackTrace();
            return responseFactory.errorResponse("Error");
        }
    }

}
