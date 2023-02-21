package com.texhnolyze.productservice.microservice.repository;

import com.texhnolyze.productservice.microservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface ProductRepository extends MongoRepository<Product, String> {
}
