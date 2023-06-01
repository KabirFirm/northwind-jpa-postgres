package com.egen.northwind.service;

import com.egen.northwind.dto.SupplierDto;
import com.egen.northwind.entity.Supplier;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierTransformService {

    public static SupplierDto toSupplierDto(Supplier supplier) {

        return new SupplierDto()
                .setId(supplier.getId())
                .setCompanyName(supplier.getCompanyName())
                .setContactName(supplier.getContactName())
                .setContactTitle(supplier.getContactTitle())
                .setAddress(supplier.getAddress())
                .setCity(supplier.getCity())
                .setRegion(supplier.getRegion())
                .setPostalCode(supplier.getPostalCode())
                .setCountry(supplier.getCountry())
                .setPhone(supplier.getPhone())
                .setFax(supplier.getFax())
                .setHomepage(supplier.getHomepage())
                ;

    }

    public static List<SupplierDto> toSupplierDtoList(List<Supplier> supplierList) {
        return supplierList.parallelStream().map(SupplierTransformService::toSupplierDto).collect(Collectors.toList());
    }
}
