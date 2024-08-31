package com.microservices.demo;

import com.microservices.demo.product.init.StreamInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
public class ProductToKafkaServiceApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(ProductToKafkaServiceApplication.class);
    private final StreamInitializer streamInitializer;

    public ProductToKafkaServiceApplication(StreamInitializer streamInitializer) {
        this.streamInitializer = streamInitializer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductToKafkaServiceApplication.class, args);
        LOG.info(" Product to Kafka Service Started !!");
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("App starts...");
        streamInitializer.init();
    }
}
