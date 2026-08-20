package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import com.example.productcatalogservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
//    @Qualifier("fkps")                   // Injection using Qualifier
    private IProductService productService;    // Field Injection

//    public ProductService(IProductService productService)   Constructor Injection
//    {
//        this.productService = productService;
//    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        ProductDto productDto = new ProductDto();
        productDto.setId(2L);
        productDto.setName("Iphone");
        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(productDto);
        return productDtos;
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId)
    {
//      ProductDto productDto = new ProductDto();
//      productDto.setId(productId);
//      productDto.setName("Macbook Pro");
//      return productDto;

        if(productId <= 0)
        {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            throw new IllegalArgumentException("Pass positive product id");
        }

        Product product = productService.getProductById(productId);
        if(product == null)
        {
            throw new NullPointerException("Product not found");
        }

        ProductDto productDto = from(product);

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto)
    {
        return productDto;
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable("id") Long productId,@RequestBody ProductDto productDto)
    {
        if(productId <= 0)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product inputProduct = from(productDto);
        Product outputProduct = productService.replaceProduct(productId,inputProduct);
        if(outputProduct == null)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
       return new ResponseEntity<>(from(outputProduct), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public String deletedProductById(@PathVariable("id") Long productId)
    {
        return "Product with " + productId + " deleted successfully";
    }

    private ProductDto from(Product product)
    {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null)
        {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }

        return productDto;
    }

    private Product from(ProductDto productDto)
    {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        if(productDto.getCategory() != null)
        {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }

        return product;
    }


}
