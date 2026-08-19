package com.example.productcatalogservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FakeStoreProductDto {
    private String title;
    private Long id;
    private String description;
    private String category;
    private Double price;
    private String image;
}
