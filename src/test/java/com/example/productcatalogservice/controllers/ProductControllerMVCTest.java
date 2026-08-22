package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void TestGetProductByIdAPI_WithPositiveId_ReturnsEmptyResponseWith404() throws Exception {
        mockMvc.perform(get("/products/2"))              // Act
                        .andExpect(status().isNotFound());          // Assert
    }

    @Test
    public void TestGetProductByIdAPI_WithPositiveId_ReturnsResponseSuccessfully() throws Exception {
        // Arrange
        Long id = 2L;
        Product product = new Product();
        product.setId(id);
        product.setName("Iphone");
        when(productService.getProductById(id)).thenReturn(product);
        ProductDto productDto = new ProductDto();
        productDto.setId(2L);
        productDto.setName("Iphone");
        String expectedResponse = objectMapper.writeValueAsString(productDto);

        //Act and Assert
        mockMvc.perform(get("/products/2")) // Act
                .andExpect(status().isOk())           // Assert
                .andExpect(content().string(expectedResponse));   // assertEquals(expectedResponse, received response converted into string
    }

    @Test
    public void TestCreateProductAPI_WithNewProduct_ReturnsResponseSuccessfully() throws Exception {
        //Arrange
        Product product = new Product();
        product.setId(2L);
        product.setName("Iphone");

        ProductDto productDto = new ProductDto();
        productDto.setId(2L);
        productDto.setName("Iphone");

        when(productService.createProduct(any(Product.class))).thenReturn(product);
        String expectedResponse = objectMapper.writeValueAsString(productDto);

        //Act and Assert
        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(expectedResponse))
                .andExpect(content().string(expectedResponse));
    }

}
