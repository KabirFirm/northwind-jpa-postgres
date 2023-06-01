package com.egen.northwind.service;

import com.egen.northwind.advice.DuplicateException;
import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.dto.CategorySearchDto;
import com.egen.northwind.dto.ProductDto;
import com.egen.northwind.dto.ProductSearchDto;
import com.egen.northwind.entity.Category;
import com.egen.northwind.entity.Product;
import com.egen.northwind.entity.QCategory;
import com.egen.northwind.entity.QProduct;
import com.egen.northwind.repository.CategoryRepository;
import com.egen.northwind.repository.ProductRepository;
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
import static com.egen.northwind.service.ProductPredicateService.productSearch;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final EntityManager entityManager;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public ProductDto createNewProduct(ProductDto productDto)  {
        var product = new Product();
        BeanUtils.copyProperties(productDto, product);
//        category.setCategoryName(categoryDto.getCategoryName());
//        category.setPicture(categoryDto.getPicture());
//        category.setDescription(categoryDto.getDescription());
        if (productRepository.findProductsByProductName(productDto.getProductName()).isEmpty()) {
            BeanUtils.copyProperties(productRepository.save(product), productDto);
            return productDto;
        } else throw new DuplicateException();
    }

    public ProductDto getProductById(Integer id) {
        return productRepository.findById(id)
                .map(itm -> {
                    var productDto = new ProductDto();
                    BeanUtils.copyProperties(itm, productDto);
                    return productDto;
                }).orElseThrow(() -> new RuntimeException(String
                        .format("Category not found with id [%s]", id)));
    }

    public ProductDto updateProduct(Integer id, ProductDto productDto) {

        productRepository.findById(id)
                .ifPresent(itm -> {
                    BeanUtils.copyProperties(productDto, itm);
                    //supplierRepository.findById(productDto.getSupplierId()).ifPresent();

                    productRepository.save(itm);

                });
        return productDto;

        //return categoryRepository.findById(id).get();

    }

    public void delete(Integer id) {
        productRepository.findById(id).ifPresent(itm -> {
            productRepository.deleteById(itm.getId());
        });
    }

    public List<ProductDto> getProductList() {
        return productRepository.findAll()
                .stream()
                .map(itm -> {
                    var productDto = new ProductDto();
                    BeanUtils.copyProperties(itm, productDto);
                    return productDto;
                }).sorted(Comparator.comparing(ProductDto::getProductName))
                .collect(Collectors.toList());
    }

    public Page<ProductDto> searchProduct(ProductSearchDto productSearchDto) {

        final QProduct product = QProduct.product;
        final JPAQuery<Product> query = new JPAQuery<>(entityManager);

        Predicate predicate = productSearch(productSearchDto);
        Pageable pageable = PageRequest.of(productSearchDto.getPage(), productSearchDto.getSize(),
                Sort.by("createdDate").descending());

        var productList = query.from(product)
                .where(predicate)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(product.id.desc())
                .fetch();
        return new PageImpl<>(ProductTransformService.toProductDtoList(productList),
                pageable, query.fetchCount());
    }

    public Page<ProductDto> getAllProductPage(ProductSearchDto productSearchDto) {
        Predicate predicate = productSearch(productSearchDto);

        final QProduct product = QProduct.product;
        final JPAQuery<Product> query = new JPAQuery<>(entityManager);
        var productList = query.from(product)
                .where(predicate)
                .orderBy(product.id.desc())
                .fetch();

        return new PageImpl<>(ProductTransformService.toProductDtoList(productList));
    }
}
