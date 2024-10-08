package com.microservices.demo.kafka.producer.config.service.impl;


import com.microservices.demo.kafka.avro.model.ProductAvroModel;
import com.microservices.demo.kafka.producer.config.service.KafkaProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;

@Service
public class ProductToKafkaProducer implements KafkaProducer<Long, ProductAvroModel> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductToKafkaProducer.class);

    private KafkaTemplate<Long, ProductAvroModel> kafkaTemplate;

    public ProductToKafkaProducer(KafkaTemplate<Long, ProductAvroModel> template) {
        this.kafkaTemplate = template;
    }

    @Override
    public void send(String topicName, Long key, ProductAvroModel message) {
        LOG.info("Sending message='{}' to topic='{}'", message, topicName);
        ListenableFuture<SendResult<Long, ProductAvroModel>> kafkaResultFuture =
                kafkaTemplate.send(topicName, key, message);
        addCallback(topicName, message, kafkaResultFuture);
        LOG.info("Message Sent ='{}' to topic='{}'", message, topicName);
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            LOG.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }

    private void addCallback(String topicName, ProductAvroModel message,
                             ListenableFuture<SendResult<Long, ProductAvroModel>> kafkaResultFuture) {
        kafkaResultFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                LOG.error("Error while sending message {} to topic {}", message.toString(), topicName, throwable);
            }

            @Override
            public void onSuccess(SendResult<Long, ProductAvroModel> result) {
                    RecordMetadata metadata = result.getRecordMetadata();
                    LOG.info("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset(),
                            metadata.timestamp(),
                            System.nanoTime());
            }
        });
    }
}
