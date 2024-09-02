package com.microservices.demo.kafka.to.elastic.transformer;

import com.microservices.demo.elastic.model.index.impl.ProductIndexModel;
import com.microservices.demo.kafka.avro.model.ProductAvroModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvroToElasticModelTransformer {
    private static final Logger LOG= LoggerFactory.getLogger(AvroToElasticModelTransformer.class);

    public List<ProductIndexModel> getElasticModels(List<ProductAvroModel> avroModels) {
        LOG.info("Product Avro Data to be transformed to Elastic Document  :{}",avroModels.toString());
        return avroModels.stream()
                .map(avroModel -> ProductIndexModel
                        .builder()
                        .id(String.valueOf(avroModel.getId()))
                        .pName(avroModel.getPName())
                        .quality(avroModel.getQuality())
                        .company(avroModel.getCompany())
                        .pOrigin(avroModel.getPOrigin())
                        .pCategory(avroModel.getPCategory())
                        .quantity(avroModel.getQuantity())
                        .createdAt(LocalDateTime.now())
                        .build()
                ).collect(Collectors.toList());
    }
}
