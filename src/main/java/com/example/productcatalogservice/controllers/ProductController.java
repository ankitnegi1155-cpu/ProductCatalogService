package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

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
    public ProductDto getProductById(@PathVariable("id") Long productId)
    {
      ProductDto productDto = new ProductDto();
      productDto.setId(productId);
      productDto.setName("Macbook Pro");
      return productDto;
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


}
