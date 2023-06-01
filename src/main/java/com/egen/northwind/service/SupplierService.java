package com.egen.northwind.service;

import com.egen.northwind.advice.DuplicateException;
import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.dto.CategorySearchDto;
import com.egen.northwind.dto.SupplierDto;
import com.egen.northwind.dto.SupplierSearchDto;
import com.egen.northwind.entity.Category;
import com.egen.northwind.entity.QCategory;
import com.egen.northwind.entity.QSupplier;
import com.egen.northwind.entity.Supplier;
import com.egen.northwind.repository.CategoryRepository;
import com.egen.northwind.repository.SupplierRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.egen.northwind.service.CategoryPredicateService.categorySearch;
import static com.egen.northwind.service.SupplierPredicateService.supplierSearch;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final EntityManager entityManager;

    public SupplierDto createNewSupplier(SupplierDto supplierDto)  {
        var supplier = new Supplier();
        BeanUtils.copyProperties(supplierDto, supplier);
        if (supplierRepository.findSuppliersByCompanyName(supplierDto.getCompanyName()).isEmpty()) {
            BeanUtils.copyProperties(supplierRepository.save(supplier), supplierDto);
            return supplierDto;
        } else throw new DuplicateException();
    }

    public SupplierDto updateSupplier(Integer id, SupplierDto supplierDto) {

        supplierRepository.findById(id)
                .ifPresent(itm -> {
                    BeanUtils.copyProperties(supplierDto, itm);
                    supplierRepository.save(itm);

                });
        return supplierDto;

    }

    public List<SupplierDto> getSupplierList() {
        return supplierRepository.findAll()
                .stream()
                .map(itm -> {
                    var supplierDto = new SupplierDto();
                    BeanUtils.copyProperties(itm, supplierDto);
                    return supplierDto;
                }).sorted(Comparator.comparing(SupplierDto::getCompanyName))
                .collect(Collectors.toList());
    }

    public Page<SupplierDto> searchSupplier(SupplierSearchDto supplierSearchDto) {

        final QSupplier supplier = QSupplier.supplier;
        final JPAQuery<Supplier> query = new JPAQuery<>(entityManager);

        Predicate predicate = supplierSearch(supplierSearchDto);
        Pageable pageable = PageRequest.of(supplierSearchDto.getPage(), supplierSearchDto.getSize(),
                Sort.by("createdDate").descending());

        var supplierList = query.from(supplier)
                .where(predicate)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(supplier.id.desc())
                .fetch();
        return new PageImpl<>(SupplierTransformService.toSupplierDtoList(supplierList),
                pageable, query.fetchCount());
    }

    public Page<SupplierDto> getAllSupplierPage(SupplierSearchDto supplierSearchDto) {
        Predicate predicate = supplierSearch(supplierSearchDto);

        final QSupplier supplier = QSupplier.supplier;
        final JPAQuery<Supplier> query = new JPAQuery<>(entityManager);
        var supplierList = query.from(supplier)
                .where(predicate)
                .orderBy(supplier.id.desc())
                .fetch();

        return new PageImpl<>(SupplierTransformService.toSupplierDtoList(supplierList));
    }


}
