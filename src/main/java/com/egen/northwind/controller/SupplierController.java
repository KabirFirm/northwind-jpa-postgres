package com.egen.northwind.controller;

import com.egen.northwind.api_response.ApiResponse;
import com.egen.northwind.api_response.ApiResponseEntityFactory;
import com.egen.northwind.dto.*;
import com.egen.northwind.service.CategoryService;
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
@RequestMapping(path = "api/v1/suppliers", produces = {"application/json"})
@Slf4j
@RequiredArgsConstructor
public class SupplierController {

    private final ApiResponseEntityFactory responseFactory;
    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierDto> save(@RequestBody SupplierDto supplierDto) {
        return new ResponseEntity<>(supplierService.createNewSupplier(supplierDto), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<SupplierDto> update( @RequestParam("id") Integer id,
                                               @RequestBody SupplierDto supplierDto) {
        try {
            return new ResponseEntity<>(supplierService.updateSupplier(id, supplierDto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}", e.getMessage());
            throw new RuntimeException("Cannot update data !!!");
        }
    }

    @GetMapping
    public ResponseEntity<?> getSupplierList() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(supplierService.getSupplierList());
        } catch (Exception e) {
            log.error("Error in SupplierController:getSupplierList {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error!!!");
        }
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<SupplierDto>>> searchSupplier(
            @RequestBody SupplierSearchDto supplierSearchDto) {
        try {
            if (Objects.nonNull(supplierSearchDto.getPage())) {
                return responseFactory.getResponse(supplierService.searchSupplier(supplierSearchDto));
            } else {
                return responseFactory.getResponse(supplierService.getAllSupplierPage(supplierSearchDto));
            }
        } catch (Exception e) {
            log.error("Error in searchCategory {}", e.getMessage());
            e.printStackTrace();
            return responseFactory.errorResponse("Error");
        }
    }

}
