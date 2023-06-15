package com.egen.northwind.controller;

import com.egen.northwind.dto.TerritoryDto;
import com.egen.northwind.service.TerritoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/territories", produces = {"application/json"})
@Slf4j
@RequiredArgsConstructor
public class TerritoryController {
    private final TerritoryService territoryService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody TerritoryDto territoryDto) {
        try {
            territoryService.createNewTerritory(territoryDto);
            return ResponseEntity.ok("success");

        }catch (Exception e) {
            log.error("error on saving {}", e.getMessage());
            throw new RuntimeException("can not save data");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody TerritoryDto territoryDto) {
        try {
            territoryService.updateTerritory(id, territoryDto);
            return ResponseEntity.ok("success");

        }catch (Exception e){
            log.error("{}", e.getMessage());
            throw new RuntimeException("Can not update data");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            territoryService.delete(id);
            return ResponseEntity.ok("success");

        }catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Can not delete data");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getTerritoryList() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(territoryService.getTerritoryList());
        }catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("There is no data");
        }
    }

    @GetMapping("/with-employee-territory")
    public ResponseEntity<?> getTerritoryWithEmployeeTerritoryList() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(territoryService.getTerritoryWithEmployeeTerritoryList());
        }catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("There is no data");
        }
    }
}
