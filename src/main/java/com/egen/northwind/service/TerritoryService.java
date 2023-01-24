package com.egen.northwind.service;

import com.egen.northwind.dto.TerritoryDto;
import com.egen.northwind.entity.Territory;
import com.egen.northwind.repository.RegionRepository;
import com.egen.northwind.repository.TerritoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TerritoryService {
    private final TerritoryRepository territoryRepository;
    private final RegionRepository regionRepository;

    public TerritoryDto createNewTerritory(TerritoryDto territoryDto) {
        var territory = new Territory();
        BeanUtils.copyProperties(territoryDto, territory);
        regionRepository.findById(territory.getRegionId())
                        .ifPresent(region-> {
                            territory.setRegion(region);
                        });
        territoryRepository.save(territory);

        return getTerritoryById(territory.getId());
    }

    public TerritoryDto getTerritoryById(Integer id) {
        return territoryRepository.findById(id)
                .map(territory -> {
                    var territoryDto = new TerritoryDto();
                    BeanUtils.copyProperties(territory, territoryDto);
                    return territoryDto;
                }).orElseThrow(() -> new RuntimeException(String.format("Territory not found with id [%s]", id)));
    }

    public Territory updateTerritory(Integer id, TerritoryDto territoryDto){
        territoryRepository.findById(id)
                .ifPresent(territory -> {
                    BeanUtils.copyProperties(territoryDto, territory);
                    regionRepository.findById(territory.getRegionId())
                                    .ifPresent(region -> {
                                        territory.setRegion(region);
                                    });
                    territoryRepository.save(territory);
                });
        return territoryRepository.findById(id).get();
    }

    public void delete(Integer id) {
        territoryRepository.findById(id)
                .ifPresent(territory -> {
                    territoryRepository.deleteById(territory.getId());
                });
    }

    public List<TerritoryDto> getTerritoryList(){
        return territoryRepository.findAll()
                .stream()
                .map(territory -> {
                    var territoryDto = new TerritoryDto();
                    BeanUtils.copyProperties(territory, territoryDto);
                    return territoryDto;
                })
                .sorted(Comparator.comparing(TerritoryDto::getId))
                .collect(Collectors.toList());
    }
}
