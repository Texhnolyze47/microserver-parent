package com.texhnolyze.productservice.microservice.service;

import com.texhnolyze.productservice.microservice.dto.ProductResponse;
import com.texhnolyze.productservice.microservice.dto.ProductResquest;
import com.texhnolyze.productservice.microservice.model.Product;
import com.texhnolyze.productservice.microservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//En tiempo de compilacion se va crear el contructor
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;


    public void createProduct(ProductResquest productResquest){
        Product product = Product.builder()
                .name(productResquest.getName())
                .description(productResquest.getDescription())
                .price(productResquest.getPrice())
                .build();

        productRepository.save(product);
        log.info("El producto {} se guardo correctamente " + product.getId());
    }

    public List <ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
