package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service("sps")
@Primary
public class StorageProductService implements IProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepo.findById(id);
        if(productOptional.isEmpty()) {
            return null;
        }

        return productOptional.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        Assert.notNull(product.getId(), "Please pass id in request");
        Optional<Product> productOptional = productRepo.findById(product.getId());
        if(productOptional.isPresent()) {
            throw new RuntimeException("Product already exists with id: " + product.getId());
        }
        return productRepo.save(product);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        Optional<Product> productOptional = productRepo.findById(id);
        if(productOptional.isEmpty()) {
            throw new RuntimeException("Product with id: " + id + " does not exist");
        }
        product.setId(id);
        return productRepo.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepo.findById(id);
        if(product.isEmpty())
        {
            throw new NullPointerException("Product with id: " + id + " does not exist");
        }
        productRepo.deleteById(id);
    }
}
