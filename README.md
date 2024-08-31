# microservices-demo

First we have created the product endpoint to send data ,then created a

app-config-data to keep all the config data classes at one point.

Then created a docker-compose folder to run the kafka in docker

        docker-compose -f common.yml -f kafka_cluster.yml up

      check if cluster is ready kafkacat -L -b localhost:19092
2. Kafka-Model we are using Avro: To create the model using AVro 
we need to define a schema in resource >avro in kafka-model ,when be compile it automatically create the Model for us.

3. Kafka-admin it will create the topic ,check for topic and schema.
    
4. common-config to add common config that can use across the module.
5. RetryConfigData class in app-config-data
6. Create a kafka adminconfig and webclientconfig and kafkaAdmin client.
7. Commented in KafkaProducerConfig as snappy compression is not compatable with mac.
   //props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerConfigData.getCompressionType());