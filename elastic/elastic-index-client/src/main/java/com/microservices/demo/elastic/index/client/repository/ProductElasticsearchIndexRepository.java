package com.microservices.demo.elastic.index.client.repository;

import com.microservices.demo.elastic.model.index.impl.ProductIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductElasticsearchIndexRepository extends ElasticsearchRepository<ProductIndexModel, String> {
}
