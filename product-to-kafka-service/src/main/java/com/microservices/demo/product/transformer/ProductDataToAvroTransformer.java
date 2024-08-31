package com.microservices.demo.product.transformer;

import com.microservices.demo.kafka.avro.model.ProductAvroModel;
import com.microservices.demo.product.model.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductDataToAvroTransformer {

    public ProductAvroModel getProductDataToAvroModel(Product product) {
       return ProductAvroModel.newBuilder()
               .setId(product.getId())
               .setCompany(product.getCompany())
               .setPCategory(product.getPCategory())
               .setPName(product.getPName())
               .setPOrigin(product.getPOrigin())
               .setQuality(product.getQuality())
               .setQuantity(product.getQuantity())
               .build();
    }
}
