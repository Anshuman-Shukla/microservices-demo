package com.microservices.demo.elastic.index.client.service.impl;

import com.microservices.demo.elastic.index.client.repository.ProductElasticsearchIndexRepository;
import com.microservices.demo.elastic.index.client.service.ElasticIndexClient;
import com.microservices.demo.elastic.model.index.impl.ProductIndexModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "true", matchIfMissing = true)
public class ProductElasticRepositoryIndexClient implements ElasticIndexClient<ProductIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductElasticRepositoryIndexClient.class);

    private final ProductElasticsearchIndexRepository productElasticsearchIndexRepository;

    public ProductElasticRepositoryIndexClient(ProductElasticsearchIndexRepository indexRepository) {
        this.productElasticsearchIndexRepository = indexRepository;
    }

    @Override
    public List<String> save(List<ProductIndexModel> documents) {
        List<ProductIndexModel> repositoryResponse =
                (List<ProductIndexModel>) productElasticsearchIndexRepository.saveAll(documents);
        List<String> ids = repositoryResponse.stream().map(ProductIndexModel::getId).collect(Collectors.toList());
        LOG.info("Documents indexed successfully with type: {} and ids: {}", ProductIndexModel.class.getName(), ids);
        return ids;
    }
}
