package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockitoBean                            // It is empty ProductService i.e., All methods are empty or null, so it reach repo and original productService with logic
    private IProductService productService;

    @Test
    public void TestGetProductById_withPositiveProductId_ReturnsProductSuccessfully()
    {
        // Arrange
        Long id = 2L;
        Product product = new Product();
        product.setId(id);
        product.setName("Iphone");
        when(productService.getProductById(id)).thenReturn(product);

        //Act
        ResponseEntity<ProductDto> productDtoResponseEntity = productController.getProductById(id);

        //Assert
        assertNotNull(productDtoResponseEntity);
        assertNotNull(productDtoResponseEntity.getBody());
        assertEquals(id, productDtoResponseEntity.getBody().getId());
        assertNull(productDtoResponseEntity.getBody().getDescription());
        assertEquals("Iphone", productDtoResponseEntity.getBody().getName());
    }

    @Test
    public void TestGetProductById_withNegativeProductId_ThrowsIllegalArguementException()
    {
        //Arrange
        Long productId = -2L;

        //Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> productController.getProductById(productId));
        assertEquals("Pass positive product id", exception.getMessage());
    }

    @Test
    public void TestGetProductById_WhereProductServiceReturnNullProduct_ThenThrowsNullPointerException()
    {
        //Arrange
        Long productId = 27L;
        when(productService.getProductById(productId)).thenReturn(null);

        //Act and Assert
        assertThrows(NullPointerException.class, () -> productController.getProductById(productId));
        Exception exception = assertThrows(NullPointerException.class, () -> productController.getProductById(productId));
        assertEquals("Product not found", exception.getMessage());
    }

}