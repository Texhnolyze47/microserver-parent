package com.texhnolyze.productservice.microservice.repository;

import com.texhnolyze.productservice.microservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
