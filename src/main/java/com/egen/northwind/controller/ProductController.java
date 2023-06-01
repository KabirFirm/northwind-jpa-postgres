package com.egen.northwind.controller;

import com.egen.northwind.api_response.ApiResponse;
import com.egen.northwind.api_response.ApiResponseEntityFactory;
import com.egen.northwind.dto.*;
import com.egen.northwind.service.ProductService;
import com.egen.northwind.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin("*") // all
@RequestMapping(path = "api/v1/products", produces = {"application/json"})
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ApiResponseEntityFactory responseFactory;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> save(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.createNewProduct(productDto), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<ProductDto> update( @RequestParam("id") Integer id,
                                               @RequestBody ProductDto productDto) {
        try {
            return new ResponseEntity<>(productService.updateProduct(id, productDto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}", e.getMessage());
            throw new RuntimeException("Cannot update data !!!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            log.error("Error in ProductController:delete {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error!!!");
        }
    }

    @GetMapping
    public ResponseEntity<?> getProductList() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productService.getProductList());
        } catch (Exception e) {
            log.error("Error in ProductController:getProductList {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error!!!");
        }
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> searchProduct(
            @RequestBody ProductSearchDto productSearchDto) {
        try {
            if (Objects.nonNull(productSearchDto.getPage())) {
                return responseFactory.getResponse(productService.searchProduct(productSearchDto));
            } else {
                return responseFactory.getResponse(productService.getAllProductPage(productSearchDto));
            }
        } catch (Exception e) {
            log.error("Error in searchCategory {}", e.getMessage());
            e.printStackTrace();
            return responseFactory.errorResponse("Error");
        }
    }

}
