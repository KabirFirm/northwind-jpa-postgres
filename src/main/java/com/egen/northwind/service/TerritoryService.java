package com.egen.northwind.service;

import com.egen.northwind.dto.TerritoryDto;
import com.egen.northwind.entity.Order;
import com.egen.northwind.entity.QEmployeeTerritory;
import com.egen.northwind.entity.QTerritory;
import com.egen.northwind.entity.Territory;
import com.egen.northwind.repository.RegionRepository;
import com.egen.northwind.repository.TerritoryRepository;
import com.egen.northwind.util.EntityTransformUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TerritoryService {
    private final TerritoryRepository territoryRepository;
    private final RegionRepository regionRepository;

    private final EntityManager entityManager;

    public TerritoryDto createNewTerritory(TerritoryDto territoryDto) {
        var territory = new Territory();
        BeanUtils.copyProperties(territoryDto, territory);
        regionRepository.findById(territory.getRegionId())
                .ifPresent(region -> {
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

    public Territory updateTerritory(Integer id, TerritoryDto territoryDto) {
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

    public List<Territory> getTerritoryList() {
        List<Territory> territoryList = territoryRepository.findAll()
                .stream()
                /*.map(territory -> {
                    var territoryDto = new TerritoryDto();
                    BeanUtils.copyProperties(territory, territoryDto);
                    return territoryDto;
                })*/
                .sorted(Comparator.comparing(Territory::getId))
                .collect(Collectors.toList());

        List<Territory> territoryListNew = territoryList.parallelStream().map(territory -> {
            var t = EntityTransformUtil.copyValueProp(territory);
            t.setEmployeeTerritoryList(EntityTransformUtil.copyList(territory.getEmployeeTerritoryList()));
            return t;
        }).collect(Collectors.toList());
        return territoryListNew;


    }

    @Transactional
    public List<Territory> getTerritoryWithEmployeeTerritoryList() {

        final QTerritory qTerritory = QTerritory.territory;
        final QEmployeeTerritory qEmployeeTerritory = QEmployeeTerritory.employeeTerritory;
        final JPAQuery<Territory> query = new JPAQuery<>(entityManager);
        var territoryJPAQuery = query.from(qTerritory)
                .leftJoin(qTerritory.employeeTerritoryList, qEmployeeTerritory).fetchJoin()
                .orderBy(qTerritory.id.desc());
        //return t;

        var t2 =territoryJPAQuery.fetch();
        System.out.println(t2.size());
        List<Territory> territoryListNew = t2.parallelStream().map(territory -> {
            var t = EntityTransformUtil.copyValueProp(territory);
            t.setEmployeeTerritoryList(EntityTransformUtil.copyList(territory.getEmployeeTerritoryList()));
            return t;
        }).collect(Collectors.toList());
        return territoryListNew;

        //return (List<Territory>) stringToObject(objToString(t));
        //return (List<Territory>) Hibernate.unproxy(t);
    }
}
