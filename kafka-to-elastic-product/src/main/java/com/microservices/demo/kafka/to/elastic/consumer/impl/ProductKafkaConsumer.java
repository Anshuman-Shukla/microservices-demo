package com.microservices.demo.kafka.to.elastic.consumer.impl;

import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.elastic.index.client.service.ElasticIndexClient;
import com.microservices.demo.elastic.model.index.impl.ProductIndexModel;
import com.microservices.demo.kafka.admin.config.client.KafkaAdminClient;
import com.microservices.demo.kafka.avro.model.ProductAvroModel;
import com.microservices.demo.kafka.to.elastic.consumer.KafkaConsumer;
import com.microservices.demo.kafka.to.elastic.transformer.AvroToElasticModelTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductKafkaConsumer implements KafkaConsumer<Long, ProductAvroModel> {
    private static final Logger LOG = LoggerFactory.getLogger(ProductKafkaConsumer.class);

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    private final KafkaAdminClient kafkaAdminClient;

    private final KafkaConfigData kafkaConfigData;
    private final AvroToElasticModelTransformer avroToElasticModelTransformer;

    private final ElasticIndexClient<ProductIndexModel> elasticIndexClient;

    public ProductKafkaConsumer(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry, KafkaAdminClient kafkaAdminClient, KafkaConfigData kafkaConfigData, AvroToElasticModelTransformer avroToElasticModelTransformer, ElasticIndexClient<ProductIndexModel> elasticIndexClient) {
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.kafkaAdminClient = kafkaAdminClient;
        this.kafkaConfigData = kafkaConfigData;
        this.avroToElasticModelTransformer = avroToElasticModelTransformer;
        this.elasticIndexClient = elasticIndexClient;
    }
    @EventListener
    public void onAppStarted(ApplicationStartedEvent event) {
        kafkaAdminClient.checkTopicsCreated();
        LOG.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
        kafkaListenerEndpointRegistry.getListenerContainer("twitterTopicListener").start();
    }
    @Override
    @KafkaListener(id = "twitterTopicListener", topics = "${kafka-config.topic-name}")
    public void receive(@Payload List<ProductAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<Long> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        LOG.info("{} number of message received with keys {}, partitions {} and offsets {}, " +
                        "sending it to elastic: Thread id {}",
                messages.toString(),
                keys.toString(),
                partitions.toString(),
                offsets.toString(),
                Thread.currentThread().getId());
        try{
            saveKafkaProductDataToElastic(messages);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void saveKafkaProductDataToElastic(List<ProductAvroModel> messages){
        List<ProductIndexModel> productIndexModels = avroToElasticModelTransformer.getElasticModels(messages);
        List<String> savedDocumentIds = elasticIndexClient.save(productIndexModels);
        LOG.info("Documents saved to elasticsearch with ids {}", savedDocumentIds.toArray());
    }
}
