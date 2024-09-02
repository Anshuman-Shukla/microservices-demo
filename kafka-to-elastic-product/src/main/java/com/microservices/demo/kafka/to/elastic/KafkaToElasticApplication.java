package com.microservices.demo.kafka.to.elastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
public class KafkaToElasticApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaToElasticApplication.class,args);
    }
}
