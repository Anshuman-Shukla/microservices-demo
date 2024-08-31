package com.microservices.demo.product.controller;

import com.microservices.demo.product.model.Product;
import com.microservices.demo.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public String postProductData(@RequestBody Product product) {
        LOG.info("Request Received {}", product);
        productService.sendToKafka(product);
        return "successfully sent";
    }
}
