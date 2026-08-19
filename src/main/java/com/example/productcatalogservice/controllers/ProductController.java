package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import com.example.productcatalogservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product product = productService.getProductById(productId);
        if(product == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProductDto productDto = from(product);

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto)
    {
        return productDto;
    }

    @PatchMapping("{id}")
    public ProductDto updatedProductPartially(@PathVariable("id") Long productId,@RequestBody ProductDto productDto)
    {
       return productDto;
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


}
