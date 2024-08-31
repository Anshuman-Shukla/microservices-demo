package com.microservices.demo.product.service;

import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.kafka.avro.model.ProductAvroModel;
import com.microservices.demo.kafka.producer.config.service.KafkaProducer;
import com.microservices.demo.product.model.Product;
import com.microservices.demo.product.transformer.ProductDataToAvroTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private static final Logger LOG= LoggerFactory.getLogger(ProductService.class);

    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducer<Long, ProductAvroModel> kafkaProducer;
    private final ProductDataToAvroTransformer productDataToAvroTransformer;

    public ProductService(KafkaConfigData kafkaConfigData, KafkaProducer<Long, ProductAvroModel> kafkaProducer, ProductDataToAvroTransformer productDataToAvroTransformer) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaProducer = kafkaProducer;
        this.productDataToAvroTransformer = productDataToAvroTransformer;
    }

    public void sendToKafka(Product product){
        LOG.info("Received Product Data {} sending to kafka topic {}", product.toString(), kafkaConfigData.getTopicName());
        ProductAvroModel productDataToAvroModel = productDataToAvroTransformer.getProductDataToAvroModel(product);
        kafkaProducer.send(kafkaConfigData.getTopicName(), Long.valueOf(product.getId()),productDataToAvroModel);
    }
}
