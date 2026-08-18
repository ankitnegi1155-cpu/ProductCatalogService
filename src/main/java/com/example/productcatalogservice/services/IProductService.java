package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById();
    List<Product> getAllProducts();
    Product createProduct(Product product);

}
