package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
//    private final RestTemplateBuilder restTemplateBuilder;
//
//    public ProductService(RestTemplateBuilder restTemplateBuilder) {
//        this.restTemplateBuilder = restTemplateBuilder;
//    }

    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
//        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, id);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, id);
        if(fakeStoreProductDtoResponseEntity.hasBody() && fakeStoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful())
        {
            return from(fakeStoreProductDtoResponseEntity.getBody());
        }
        else
        {
            System.out.println(fakeStoreProductDtoResponseEntity.getStatusCode());
        }

        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());

        return productDto;
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);

        return product;
    }

}
